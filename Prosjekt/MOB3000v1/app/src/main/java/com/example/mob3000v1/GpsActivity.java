package com.example.mob3000v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class GpsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public static final int PLAY_SERVICES_RESOLUTION_REQUEST = 2;
    private static int saveProgress;

    static Circle circle;
    static CircleOptions circleOptions;
    static boolean mapClicked;
    static AudioManager aManager;
    static SeekBar seekBar;
    static MarkerOptions markerOptions;
    static GoogleMap gMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);

        seekBar = findViewById(R.id.bar);

        // If google play services is out of date
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);

        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                // Prompt the dialog to update google play
                googleAPI.getErrorDialog(this,result,PLAY_SERVICES_RESOLUTION_REQUEST).show();

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        aManager = (AudioManager) getSystemService(getApplicationContext().AUDIO_SERVICE);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {
            Intent intent = new Intent(
                    Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
            startActivity(intent);
        }

        findViewById(R.id.buttonStartLocationUpdates).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            GpsActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_LOCATION_PERMISSION
                    );
                } else {
                    startLocationService();
                }
            }
        });

        findViewById(R.id.buttonStopLocationUpdates).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationService();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveProgress = seekBar.getProgress();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(saveProgress == 0) saveProgress = ((seekBar.getMax()) / 2);
        setupMapIfNeeded();
    }

    private void setupMapIfNeeded() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        mapFragment.getMapAsync(this);

        seekBar.setProgress(saveProgress);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            } else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isLocationServiceRunning() {
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null) {
            for(ActivityManager.RunningServiceInfo service :
                    activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if(LocationService.class.getName().equals(service.service.getClassName())) {
                    if(service.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void startLocationService() {
        if(!isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service started", Toast.LENGTH_SHORT).show();

            SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
            supportMapFragment.getMapAsync(GpsActivity.this);
        }
    }

    private void stopLocationService() {
        if(isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service stopped", Toast.LENGTH_SHORT).show();

            if (ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        GpsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_LOCATION_PERMISSION
                );
            } else {
                gMap.setMyLocationEnabled(false);
                gMap.clear();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        Log.d("Map", String.valueOf(gMap));
        // If location service has started, able to use map
        if(isLocationServiceRunning()) {

            if(mapClicked) {
                gMap.addMarker(markerOptions);
                circleOptions.radius(saveProgress);
                circle = gMap.addCircle(circleOptions);
            }


            // Permission granted in onCreate in MainActivity
            if (ContextCompat.checkSelfPermission(
                    getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        GpsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_LOCATION_PERMISSION
                );
            } else {
                gMap.setMyLocationEnabled(true);
            }

            LatLng latLng = new LatLng(LocationService.latitude, LocationService.longitude);
            Log.d("LOCATION_UPDATE", LocationService.latitude + ", " + LocationService.longitude);
            //LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            //gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            if(!latLng.equals(new LatLng(0.0, 0.0))) {
                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            }

            gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    //Get current zoom level
                    float zoom = gMap.getCameraPosition().zoom;

                    //Radius variable for circle
                    int radius;

                    // Seekbar
                    //seekBar = findViewById(R.id.bar);
                    int lValue = seekBar.getProgress();

                    //Create marker
                    markerOptions = new MarkerOptions();

                    //Set marker position
                    markerOptions.position(latLng);

                    //Set latitude and longitude on marker
                    markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                    //Clear the priveusyslei click position
                    gMap.clear();

                    //Zoom the marker
                    if (zoom < 15) {
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                        seekBar.setProgress(200);
                        radius = 200;
                    } else {
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
                        seekBar.setProgress(lValue);
                        radius = lValue;
                    }

                    //Add marker on map
                    gMap.addMarker(markerOptions);

                    //Create circle
                    circleOptions = new CircleOptions();
                    circleOptions.center(new LatLng(latLng.latitude, latLng.longitude));
                    circleOptions.radius(radius);
                    circleOptions.strokeWidth(2);
                    circleOptions.strokeColor(Color.BLACK);
                    circleOptions.fillColor(0x30ff0000);
                    //gMap.addCircle(circleOptions);

                    circle = gMap.addCircle(new CircleOptions()
                            .center(new LatLng(latLng.latitude, latLng.longitude))
                            .radius(radius)
                            .strokeWidth(2)
                            .strokeColor(Color.BLACK)
                            .fillColor(0x30ff0000));

                    // Set mapClicked to true
                    mapClicked = true;
                }
            });

            // Seekbar change listener
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int min = 10;
                    if(progress < min) {
                        seekBar.setProgress(min);
                    }
                    circle.setRadius(progress);
                }

                @Override
                public void onStartTrackingTouch(final SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(final SeekBar seekBar) {
                }
            });
        }
    }
}
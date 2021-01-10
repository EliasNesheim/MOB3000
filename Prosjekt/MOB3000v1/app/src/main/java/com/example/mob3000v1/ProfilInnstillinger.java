package com.example.mob3000v1;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class ProfilInnstillinger extends AppCompatActivity {

    EditText epst, gampass, nypass;
    String encodeImageString;
    ImageView valgtBilde, kameraKnapp;
    Bitmap bitmap;
    Button lagreInfo;
    Spinner instspinner;
    Spinner fagspinner;

    private String Brukernavn = "";


    @Override
    protected void onCreate(Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_innstillinger);

        //Henter brukernavn
        Intent intent_profil = getIntent();
        Brukernavn = intent_profil.getStringExtra("brukernavn");

        //Finner views fra layout
        valgtBilde = findViewById(R.id.profilBilde);
        kameraKnapp = findViewById(R.id.kameraBildeKnapp);
        lagreInfo = findViewById(R.id.knapp_lagre);
        epst = (EditText) findViewById(R.id.epost);
        gampass = (EditText) findViewById(R.id.passold);
        nypass = (EditText) findViewById(R.id.passny);
        instspinner = (Spinner) findViewById(R.id.spinnerInstitusjon);
        fagspinner = (Spinner) findViewById(R.id.spinnerFag);

        //Knapp øverst i hjørnet som går tilbake til profilsiden
        final Intent intent_tilbake = new Intent(this, ProfilActivity.class);
        intent_tilbake.putExtra("brukernavn",Brukernavn);
        intent_tilbake.putExtra("innlogget",1);
        final ImageButton btn_tilbake = findViewById(R.id.btnTilbake);
        btn_tilbake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent_tilbake);
            }
        });

        //Liste for brukerinfo
        final ArrayList<String> brukerInfoListe = new ArrayList<>();

        RequestQueue queueBilde = Volley.newRequestQueue(this);
        String urlBilde = "http://13.49.175.230/profilinfo.php?";
        urlBilde += "user=" + Brukernavn;

        //Setter riktig profilbilde
        StringRequest stringRequestBilde = new StringRequest(Request.Method.GET, urlBilde,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        String[] values = response.split(",");
                        brukerInfoListe.addAll(Arrays.asList(values));

                        //Bilde:
                        if(brukerInfoListe.size() > 5) {
                            String lokasjon = brukerInfoListe.get(5);

                            Picasso.get()
                                    .load("http://13.49.175.230/" + lokasjon)
                                    .into(valgtBilde);//Your imageView
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queueBilde.add(stringRequestBilde);


        //Velger bilder i mediafolder når man trykker på knapp
        kameraKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(ProfilInnstillinger.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                                startActivityForResult(Intent.createChooser(intent, "Browse Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }

        });


    } //onCreate ferdig

    @Override
    protected void onStart() {
        super.onStart();

        final ArrayList<String> instListe = new ArrayList<>();
        final ArrayList<String> fagListe = new ArrayList<>();

        final ArrayAdapter<String> instdataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list, instListe);
        final ArrayAdapter<String> fagdataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_list, fagListe);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://13.49.175.230/profilspinner.php";

        //Viser innholdet i spinnerne
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] kombo = response.split("/");
                        String[] instlist = kombo[0].split(",");
                        String[] faglist = kombo[1].split(",");
                        if(instlist[0].contains("Velg institusjon")) instlist[0] = "Velg institusjon";
                        instListe.addAll(Arrays.asList(instlist));
                        fagListe.addAll(Arrays.asList(faglist));

                        instspinner.setAdapter(instdataAdapter);
                        fagspinner.setAdapter(fagdataAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: sjekk internett-tilkobling",Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

        //Funksjon for lagreknapp
        lagreInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lagreEndringer();

                //Hvis bilde er lagt til
                if(encodeImageString != null) {
                    bildeLastOpp();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode==RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                bitmap=BitmapFactory.decodeStream(inputStream);
                valgtBilde.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            } catch (Exception ex) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();
        encodeImageString = Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    //Lagre info knapp metode
    public void lagreEndringer() {

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://13.49.175.230/profilinnstillinger.php?";
        url += "user=" + Brukernavn;

        String email = epst.getText().toString();
        String gpassord = gampass.getText().toString();
        String npassord = nypass.getText().toString();

        int valgtInst = instspinner.getSelectedItemPosition();
        int valgtFag = fagspinner.getSelectedItemPosition();

        url += "&inst=" + valgtInst + "&fag=" + valgtFag + "&gpassord=" + gpassord + "&npassord=" + npassord + "&email=" + email;


        if(valgtInst == 0 && valgtFag == 0 && gpassord.isEmpty() && npassord.isEmpty() && email.isEmpty() && encodeImageString == null) {
            Toast.makeText(getApplicationContext(),"Ingen endringer gjort", Toast.LENGTH_LONG).show();
        } else if (valgtInst != 0 || valgtFag != 0 || !gpassord.isEmpty() || !npassord.isEmpty() || !email.isEmpty()) {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(!response.isEmpty()) {
                        Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Error: sjekk internett-tilkobling",Toast.LENGTH_LONG).show();
                }
            });

            queue.add(stringRequest);
        }
    }

    //Laste opp bilde metode
    private void bildeLastOpp() {
        String url = "http://13.49.175.230/fileupload.php?";
        url += "user=" + Brukernavn;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()) {
                    Toast toast = Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Bilde error: sjekk internett-tilkobling",Toast.LENGTH_LONG).show();
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> map = new HashMap<String, String>();
                map.put("upload", encodeImageString);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);
    }

}
package com.example.mob3000v1;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfilActivity extends AppCompatActivity {

    //Navigation drawer
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Button button;
    private ImageView navBilde;
    private TextView textView;

    //Bruker variabler
    private int innlogget;
    private TextView brukernavn;
    private TextView fag;
    private TextView instu;
    private TextView epost;
    private Button innlegg;
    private Button kommentarer;
    private ImageView profilbilde;

    //Brukernavn fra logg inn
    private String Brukernavn = "";

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        //Henter brukernavn
        Intent intent_profil = getIntent();
        Brukernavn = intent_profil.getStringExtra("brukernavn");

        //Finner views/id'er for brukerinfo
        brukernavn = findViewById(R.id.txtBruker);
        fag = findViewById(R.id.txtStudie);
        instu = findViewById(R.id.txtInstitusjon);
        epost = findViewById(R.id.txtEpost);
        innlegg = findViewById(R.id.btnInnlegg);
        kommentarer = findViewById(R.id.btnKommentarer);
        profilbilde = findViewById(R.id.profilBilde);

        //Finner views/id'er for navmeny
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nvView);
        View header = navigationView.getHeaderView(0);
        button = (Button) header.findViewById(R.id.loginButton);
        navBilde = (ImageView) header.findViewById(R.id.navBilde);
        textView = (TextView) header.findViewById(R.id.navBruker);

        //Knapp som går til brukerens innlegg
        final Intent intent_innlegg = new Intent(this, BrukerInnlegg.class);
        intent_innlegg.putExtra("brukernavn",Brukernavn);
        innlegg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent_innlegg);
            }
        });

        //Knapp som går til brukerens kommentarer
        final Intent intent_kommentar = new Intent(this, BrukerKommentarer.class);
        intent_kommentar.putExtra("brukernavn",Brukernavn);
        kommentarer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent_kommentar);
            }
        });

        //Knapp som går til profilinnstillinger
        final Intent intent_profinnstillinger = new Intent(this, ProfilInnstillinger.class);
        intent_profinnstillinger.putExtra("brukernavn",Brukernavn);
        final Button btn_profins = findViewById(R.id.btnProfIns);
        btn_profins.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent_profinnstillinger);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Nav
        //Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle drawerToggle = setupDrawerToggle();
        drawerToggle.syncState();
        mDrawer.addDrawerListener(drawerToggle);
        setupDrawerContent(navigationView);

        //Henter info om brukeren er innlogget fra logginn klassen
        Intent intent = getIntent();
        innlogget = intent.getIntExtra("innlogget", 1);

        if(innlogget == 1) {
            final ArrayList<String> brukerInfoListe = new ArrayList<>();
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://13.49.175.230/profilinfo.php?";
            url += "user=" + Brukernavn;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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
                                        .into(navBilde);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    //Feilmelding hvis internett ikke er koblet til/server er nede.
                    String error_melding = getResources().getString(R.string.error_melding);
                }
            });
            queue.add(stringRequest);

            navBilde.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(Brukernavn);
            button.setVisibility(View.GONE);

            navigationView.getMenu().findItem(R.id.profilside).setVisible(true);
            navigationView.getMenu().findItem(R.id.innleggFragment).setVisible(true);
            navigationView.getMenu().findItem(R.id.loggut).setVisible(true);
            navigationView.getMenu().findItem(R.id.logginn).setVisible(false);
        } else {
        sp = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }


        //Viser info om brukeren fra databasen:
        final ArrayList<String> brukerInfoListe = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://13.49.175.230/profilinfo.php?";
        url += "user=" + Brukernavn;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String[] values = response.split(",");
                        //Trimer det første elementet i listen.
                        if(values[0].contains("IT")) values[0] = "IT";
                        if(values[0].contains("Okonomi")) values[0] = "Økonomi";
                        if(values[0].contains("Juss")) values[0] = "Juss";
                        if(values[0].contains("Markedsforing")) values[0] = "Markedsføring";
                        if(values[0].contains("Pedagogikk")) values[0] = "Pedagogikk";
                        if(values[0].contains("Naturvitenskap")) values[0] = "Naturvitenskap";
                        brukerInfoListe.addAll(Arrays.asList(values));
                        System.out.println(brukerInfoListe);
                        brukernavn.setText(Brukernavn);
                        fag.setText(brukerInfoListe.get(0));
                        instu.setText(brukerInfoListe.get(1));
                        epost.setText(brukerInfoListe.get(2));
                        innlegg.setText(String.format("Innlegg: %s", brukerInfoListe.get(3)));
                        kommentarer.setText(String.format("Kommentarer: %s", brukerInfoListe.get(4)));

                        //Bilde:
                        if(brukerInfoListe.size() > 5) {
                            String lokasjon = brukerInfoListe.get(5);

                            Picasso.get()
                                    .load("http://13.49.175.230/" + lokasjon)
                                    .into(profilbilde);//Bilde
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Feilmelding hvis internettt ikke er koblet til/server er nede.
                String error_melding = getResources().getString(R.string.error_melding);
                brukernavn.setText(error_melding);
            }
        });

        queue.add(stringRequest);

    }

    //Navdrawer
    private ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawer.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        Intent intent_main = new Intent(this, MainActivity.class);
        Intent intent_profil = new Intent(this, ProfilActivity.class);
        intent_profil.putExtra("brukernavn",Brukernavn);
        intent_profil.putExtra("innlogget",1);
        Intent intent_gps = new Intent(this, GpsActivity.class);
        switch(menuItem.getItemId()) {
            case R.id.hjem:
                if(innlogget == 1) {
                    intent_main.putExtra("brukernavn",Brukernavn);
                    intent_main.putExtra("innlogget", 1);
                } else {
                    intent_main.putExtra("innlogget", 0);
                }
                startActivity(intent_main);
                break;
            case R.id.profilside:
                startActivity(intent_profil);
                break;
            case R.id.innleggFragment:
                Intent intent_innlegg = new Intent(this, PostActivity.class);
                intent_innlegg.putExtra("brukernavn",Brukernavn);
                startActivity(intent_innlegg);
                break;
            case R.id.GPS:
                startActivity(intent_gps);
                break;
            case R.id.loggut:
                loggUt();
                break;
        }
    }

    public void loggInn(View v) {
        startActivity(new Intent(ProfilActivity.this, LoggInn.class));
    }

    public void loggUt() {
        sp = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        sp.edit().clear().commit();

        startActivity(new Intent(ProfilActivity.this, MainActivity.class));
    }

    public void Post (View v) {
        Intent i = new Intent(ProfilActivity.this, PostActivity.class);
        i.putExtra("brukernavn",Brukernavn);
        startActivity(i);
    }
}
package com.example.mob3000v1;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;


public class Registrer extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrer);
		
		//Knapp øverst i hjørnet som går tilbake til profilsiden
        final Intent intent_tilbake = new Intent(this, LoggInn.class);
        final ImageButton btn_tilbake = findViewById(R.id.btnTilbake);
        btn_tilbake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent_tilbake);
            }
        });
		
        final Spinner fagSpinner = (Spinner) findViewById(R.id.spinnerFag);
        final Spinner instSpinner = (Spinner) findViewById(R.id.spinnerInst);

        final ArrayList <String> fagList = new ArrayList<>();
        final ArrayList <String> instList = new ArrayList<>();

        final ArrayAdapter<String> fagAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fagList);
        final ArrayAdapter<String> instAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, instList);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://13.49.175.230/spinner.php?";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        String[] kombo = response.split("/");
                        String[] fag = kombo[0].split(",");
                        String[] inst = kombo[1].split(",");

                        fagList.addAll(Arrays.asList(fag));
                        instList.addAll(Arrays.asList(inst));

                        // Drop down layout style - list view with radio button
                        fagAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        instAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        // attaching data adapter to spinner
                        fagSpinner.setAdapter(fagAdapter);
                        instSpinner.setAdapter(instAdapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void registrer(View view) {
        EditText bn = findViewById(R.id.registerBrukernavn);
        EditText ep = findViewById(R.id.emailAddress);
        EditText pw = findViewById(R.id.registerPassord);
        EditText pwg = findViewById(R.id.registerGjenta);

        Spinner fagSpinner = (Spinner) findViewById(R.id.spinnerFag);
        Spinner instSpinner = (Spinner) findViewById(R.id.spinnerInst);

        String brukernavn = bn.getText().toString();
        String passord = pw.getText().toString();
        String gpassord = pwg.getText().toString();
        String institusjon = instSpinner.getSelectedItem().toString();
        String fag = fagSpinner.getSelectedItem().toString();
        String epost = ep.getText().toString();

        if(brukernavn.equals("") || epost.equals("")) {
            Toast.makeText(this, "Fyll felt", Toast.LENGTH_LONG).show();
        } else if(!passord.equals(gpassord)) {
            Toast.makeText(this, "Passord må være likt", Toast.LENGTH_LONG).show();

        } else {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://13.49.175.230/registrerbruker.php?";

            if (fag.contains("IT")) fag="IT";

            url += "bNavn=" + brukernavn + "&PW=" + passord + "&inst=" + institusjon + "&fag=" + fag + "&email=" + epost;


            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("bruker eksisterer")) {
                                Toast.makeText(getApplicationContext(), "Brukernavn eksisterer", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                                startActivity(new Intent(Registrer.this, LoggInn.class));
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
    }
}

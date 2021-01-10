package com.example.mob3000v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LoggInn extends AppCompatActivity {

    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logg_inn);

		//Knapp øverst i hjørnet som går tilbake til profilsiden
        final Intent intent_tilbake = new Intent(this, MainActivity.class);
        final ImageButton btn_tilbake = findViewById(R.id.btnTilbake);
        btn_tilbake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent_tilbake);
            }
        });

        //For å komme seg inn på registrersiden
        TextView registrer = findViewById(R.id.registerHer);
        registrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegister();
            }
        });

    }

	

    //Metode for å logge inn
    public void loginBtn(View view) {
        //Henter input
        EditText bn = findViewById(R.id.loginBrukernavn);
        EditText pw = findViewById(R.id.loginPassord);

        final String brukernavn = bn.getText().toString();
        String passord = pw.getText().toString();

        if(brukernavn.equals("") || passord.equals("")) {
            Toast.makeText(this, "Fyll felt", Toast.LENGTH_LONG).show();
        } else {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);

            String url = "http://13.49.175.230/login.php?";
            //String url = "http://13.49.175.230/login.php?";

            url += "user=" + brukernavn + "&pass=" + passord;

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {
                            if(response.contains("Innlogget")) {
                                String[] kombo = response.split(",");

                                int bruker_fag = Integer.parseInt(kombo[2]);
                                int bruker_inst = Integer.parseInt(kombo[3]);
                                Toast.makeText(LoggInn.this, "innlogget", Toast.LENGTH_LONG).show();

                                Intent i = new Intent(LoggInn.this, MainActivity.class);
                                i.putExtra("innlogget",1);
                                i.putExtra("brukernavn",brukernavn);
                                sp = getSharedPreferences("prefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("username", brukernavn);
                                editor.putInt("bruker_fag", bruker_fag);
                                editor.putInt("bruker_inst", bruker_inst);
                                editor.commit();

                                startActivity(i);
                        }
                            else {
                                Toast.makeText(LoggInn.this, "feil", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoggInn.this, "Ingen tilkobling", Toast.LENGTH_LONG).show();
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);
        }
    }
    public void setBrukernavn(String brukernavn) {
    }

    //Metode for å gå inn på registrersiden
    public void openRegister() {
        Intent intent = new Intent(this, Registrer.class);
        startActivity(intent);
    }
}

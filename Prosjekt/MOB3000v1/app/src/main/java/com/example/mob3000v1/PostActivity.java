package com.example.mob3000v1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

public class PostActivity extends AppCompatActivity {

    private String Brukernavn;
    private int brukerID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent intent = getIntent();
        Brukernavn = intent.getStringExtra("brukernavn");
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://13.49.175.230/getbrukerid.php?";
        String url = "http://13.49.175.230/getBrukerID.php?";
        url += "user=" + Brukernavn;


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        response = response.trim();
                        brukerID = Integer.parseInt(response);

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

    public void Post(View view) {
        EditText tittel = findViewById(R.id.input_tittel);
        EditText tekst = findViewById(R.id.input_tekst);

        String Tittel = tittel.getText().toString();
        String Tekst = tekst.getText().toString();

        Tekst = Tekst.replaceAll(",", "¸");
        SharedPreferences sp = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        int fag_id = sp.getInt("bruker_fag", 10);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://13.49.175.230/post.php?";
        url += "user=" + brukerID + "&tittel=" + Tittel + "&tekst=" + Tekst + "&fag_id=" + fag_id;
        url = url.replaceAll(" ","%");

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override

                    public void onResponse(String response) {
                        response = response.trim();
                        if(response.equals("success")) {
                            Toast.makeText(getApplicationContext(), "Ny Post er postet", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "feil", Toast.LENGTH_LONG).show();
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

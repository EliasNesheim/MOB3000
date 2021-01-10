package com.example.mob3000v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class BrukerInnlegg extends AppCompatActivity {

    private String Brukernavn = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bruker_innlegg);

        final ViewGroup.LayoutParams[] layoutparams = new ViewGroup.LayoutParams[1];

        final ScrollView scrollview = (ScrollView) findViewById(R.id.scrollView);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        Intent intent_innlegg = getIntent();
        Brukernavn = intent_innlegg.getStringExtra("brukernavn");

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

        String url = "http://13.49.175.230/getPostsBruker.php?";
        url += "user=" + Brukernavn;


        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(String response) {
                        layoutparams[0] = new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        );

                        String[] kombo = response.split("/");

                        LinearLayout linearLayout = new LinearLayout(getApplicationContext());
                        linearLayout.setOrientation(LinearLayout.VERTICAL);

                        if(!response.contains("Ingen innlegg")) {
                            for (int i = 0; i < kombo.length; i++) {

                                String[] post = kombo[i].split(",");

                                final int innlegg_id = Integer.parseInt(post[4]);

                                final CardView cardView = new CardView(getApplicationContext());
                                cardView.setId(i);

                                cardView.setLayoutParams(layoutparams[0]);
                                cardView.setCardBackgroundColor(13355888);
                                cardView.setRadius(20);
                                cardView.setPadding(25, 25, 25, 25);

                                LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
                                linearLayout1.setOrientation(LinearLayout.VERTICAL);

                                final TextView textView0 = new TextView(getApplicationContext());
                                textView0.setText("\n " + post[1]);
                                textView0.setPadding(25, 25, 25, 25);
                                textView0.setGravity(Gravity.CENTER);
                                textView0.setTextSize(25);

                                final TextView textView = new TextView(getApplicationContext());
                                textView.setId(i);
                                textView.setText("\n" + post[2]);

                                final TextView textView2 = new TextView(getApplicationContext());
                                textView2.setId(i + 1);
                                textView2.setText("\n" + post[0] + "    " + post[3]);
                                textView2.setPadding(25, 25, 25, 25);
                                textView2.setGravity(Gravity.RIGHT);

                                textView.setPadding(25, 25, 25, 25);

                                textView.setGravity(Gravity.CENTER);

                                linearLayout1.addView(textView0);
                                linearLayout1.addView(textView);
                                linearLayout1.addView(textView2);

                                cardView.addView(linearLayout1);
                                linearLayout.addView(cardView);
                                cardView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        openInnlegg(innlegg_id);
                                    }
                                });
                            }
                        } else {
                            final CardView cardView = new CardView(getApplicationContext());

                            cardView.setLayoutParams(layoutparams[0]);
                            cardView.setCardBackgroundColor(13355888);
                            cardView.setRadius(20);
                            cardView.setPadding(25, 25, 25, 25);

                            LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
                            linearLayout1.setOrientation(LinearLayout.VERTICAL);

                            final TextView textView = new TextView(getApplicationContext());

                            textView.setText(R.string.ingen_innlegg_bruker);
                            textView.setPadding(25, 100, 25, 100);
                            textView.setGravity(Gravity.CENTER);

                            linearLayout1.addView(textView);

                            cardView.addView(linearLayout1);
                            linearLayout.addView(cardView);
                        }

                        linearLayout.setId(1);
                        scrollview.addView(linearLayout);

                    }




                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: sjekk internett-tilkobling",Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

    }

    public void openInnlegg(int i) {
        Intent intent_brukerinnlegg = new Intent(this, BrukerInnleggEnkel.class);
        intent_brukerinnlegg.putExtra("brukernavn",Brukernavn);
        intent_brukerinnlegg.putExtra("i",i);
        startActivity(intent_brukerinnlegg);
    }
}


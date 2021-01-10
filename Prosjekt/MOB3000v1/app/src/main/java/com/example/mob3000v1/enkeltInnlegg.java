package com.example.mob3000v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class enkeltInnlegg extends MainActivity{

SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enkelt_innlegg);
        SharedPreferences sp = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String Brukernavn = sp.getString("username", "");
        //Knapp øverst i hjørnet som går tilbake til profilsiden
        final Intent intent_tilbake = new Intent(this, MainActivity.class);
        if(Brukernavn != "") {
            intent_tilbake.putExtra("brukernavn",Brukernavn);
            intent_tilbake.putExtra("innlogget",1);
        }

        final ImageButton btn_tilbake = findViewById(R.id.btnTilbake);
        btn_tilbake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent_tilbake);
            }
        });

        final LinearLayout linearLayout = findViewById(R.id.afafafafa);

        RequestQueue queue = Volley.newRequestQueue(this);

        final Intent intent = getIntent();

        int i = intent.getIntExtra("i", 0);
        TextView textView0 = new TextView(this);
        textView0.setText(String.valueOf(i));
        String url = "http://13.49.175.230/getPost.php?";
        url += "inn_id=" + i;
        sp = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("i", i);
        editor.commit();

        final ViewGroup.LayoutParams[] layoutparams = new ViewGroup.LayoutParams[1];

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(String response) {
                        layoutparams[0] = new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        String[] kombo = response.split(",");
                        final CardView cardView = new CardView(getApplicationContext());
                        cardView.setId(1);

                        cardView.setLayoutParams(layoutparams[0]);
                        cardView.setCardBackgroundColor(13355888);
                        cardView.setRadius(20);
                        cardView.setPadding(25, 25, 25, 25);

                        LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
                        linearLayout1.setOrientation(LinearLayout.VERTICAL);

                        final TextView textView0 = new TextView(getApplicationContext());

                        textView0.setText("\n " + kombo[1]);
                        textView0.setPadding(25, 25, 25, 25);
                        textView0.setGravity(Gravity.CENTER);
                        textView0.setTextSize(25);

                        final TextView textView = new TextView(getApplicationContext());
                        textView.setId(1);

                        textView.setText("\n" + kombo[2]);

                        final TextView textView2 = new TextView(getApplicationContext());
                        textView2.setId(1);
                        textView2.setText("\n" + kombo[0]);
                        textView2.setPadding(25, 25, 25, 25);
                        textView2.setGravity(Gravity.RIGHT);

                        textView.setPadding(25, 25, 25, 25);

                        textView.setGravity(Gravity.CENTER);

                        linearLayout1.addView(textView0);
                        linearLayout1.addView(textView);
                        linearLayout1.addView(textView2);

                        cardView.addView(linearLayout1);
                        linearLayout.addView(cardView);
                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });


        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        final ScrollView scrollview = findViewById(R.id.scrollView);
        LinearLayout pap = findViewById(R.id.pap);

        String url2 = "http://13.49.175.230/getKommentar.php?";
        url2 += "inn_id=" + i;

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2,
                new Response.Listener<String>() {

                    @SuppressLint("ResourceType")
                    @Override
                    public void onResponse(String response) {
                        System.out.println("test1234");
                        LinearLayout pap = findViewById(R.id.pap);
                        layoutparams[0] = new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                        );
                        String[] kombo2 = response.split("/");

                        LinearLayout linearLayout = new LinearLayout(enkeltInnlegg.this);
                        linearLayout.setOrientation(LinearLayout.VERTICAL);
                        System.out.println(response);
                        //Sjekker om det finnes innlegg i databasen:
                        if(!response.equals("Det oppstod et problem")) {
                            if(response.contains(",")) {

                                for (int i = 0; i < kombo2.length; i++) {
                                    System.out.println("FOR LOOP");
                                    String[] post2 = kombo2[i].split(",");
                                    final CardView cardView = new CardView(enkeltInnlegg.this);
                                    cardView.setId(i);

                                    cardView.setLayoutParams(layoutparams[0]);
                                    cardView.setCardBackgroundColor(13355888);
                                    cardView.setRadius(20);
                                    cardView.setPadding(25, 25, 25, 25);

                                    LinearLayout linearLayout1 = new LinearLayout(enkeltInnlegg.this);
                                    linearLayout1.setOrientation(LinearLayout.VERTICAL);
                                    final TextView textView0 = new TextView(enkeltInnlegg.this);

                                    textView0.setText("\n " + post2[1]);
                                    textView0.setPadding(25, 25, 25, 5);
                                    textView0.setGravity(Gravity.CENTER);


                                    final TextView textView = new TextView(enkeltInnlegg.this);
                                    textView.setId(i);

                                    textView.setText("\n" + post2[2]);

                                    final TextView textView2 = new TextView(enkeltInnlegg.this);
                                    textView2.setId(i + 1);
                                    textView2.setPadding(25, 5, 25, 25);
                                    textView2.setGravity(Gravity.RIGHT);
                                    textView.setTextSize(35);

                                    textView.setPadding(25, 5, 25, 5);

                                    textView.setGravity(Gravity.CENTER);

                                    linearLayout1.addView(textView0);
                                    linearLayout1.addView(textView);
                                    linearLayout1.addView(textView2);
                                    // linearLayout1.addView(textView2);
                                    cardView.addView(linearLayout1);
                                    linearLayout.addView(cardView);
                                }
                            }
                        } else { //Hvis ingen innlegg finnes i databasen:
                            final CardView cardView = new CardView(enkeltInnlegg.this);
                            System.out.println("ELSEN");
                            cardView.setLayoutParams(layoutparams[0]);
                            cardView.setCardBackgroundColor(13355888);
                            cardView.setRadius(20);
                            cardView.setPadding(25, 25, 25, 25);

                            LinearLayout linearLayout1 = new LinearLayout(enkeltInnlegg.this);
                            linearLayout1.setOrientation(LinearLayout.VERTICAL);

                            final TextView textView = new TextView(enkeltInnlegg.this);

                            textView.setText(R.string.ingen_innlegg);
                            textView.setPadding(25, 5, 25, 5);
                            textView.setGravity(Gravity.CENTER);

                            linearLayout1.addView(textView);

                            cardView.addView(linearLayout1);
                            linearLayout.addView(cardView);
                        }

                        linearLayout.setId(1);

                        pap.addView(linearLayout);

                    }




                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Ingen tilkobling:
                Toast.makeText(enkeltInnlegg.this, "Ingen tilkobling", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest2);

    }

    public void kommenter(View view) {
        System.out.println("test");

        EditText kommentar = findViewById(R.id.textView5);
        String kom = kommentar.getText().toString();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String Brukernavn = sp.getString("username", "");
        int i = sp.getInt("i", 13);


        if (kom.equals("")) {
            Toast.makeText(enkeltInnlegg.this, "Fyll felt", Toast.LENGTH_LONG).show();
        }else {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(enkeltInnlegg.this);
            String url = "http://13.49.175.230/SetKommentar.php?";
            //String url = "http://13.49.175.230/registrerbruker.php?";
//
            url += "kom=" + kom + "&i=" + i + "&Bruknavn=" + Brukernavn;
            System.out.println(url);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.contains("kommentaren er registrert")) {
                                SharedPreferences sp = getApplicationContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                                int i = sp.getInt("i", 10);
                                Toast.makeText(getApplicationContext(), "kommentaren er registrert", Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(enkeltInnlegg.this, enkeltInnlegg.class);
                                intent.putExtra("i",i);
                                startActivity(intent);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });

            // Add the request to the RequestQueue.
            queue.add(stringRequest);

        }
    }

}

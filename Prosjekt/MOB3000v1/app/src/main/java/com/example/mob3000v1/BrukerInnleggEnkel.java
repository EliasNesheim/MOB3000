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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
public class BrukerInnleggEnkel extends AppCompatActivity {

    private String Brukernavn = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bruker_innlegg_enkel);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.enkeltInnleggId);
        RequestQueue queue = Volley.newRequestQueue(this);

        Intent intent_brukerinnlegg = getIntent();
        Brukernavn = intent_brukerinnlegg.getStringExtra("brukernavn");

        Intent intent = getIntent();
        int i = intent.getIntExtra("i", 0);

        TextView textView0 = new TextView(this);
        textView0.setText(String.valueOf(i));

        //Knapp øverst i hjørnet som går tilbake
        final Intent intent_tilbake = new Intent(this, BrukerInnlegg.class);
        intent_tilbake.putExtra("brukernavn",Brukernavn);
        intent_tilbake.putExtra("innlogget",1);
        final ImageButton btn_tilbake = findViewById(R.id.btnTilbake);
        btn_tilbake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent_tilbake);
            }
        });

        String url = "http://13.49.175.230/getPostBruker.php?";
        url += "inn_id=" + i;

        final ViewGroup.LayoutParams[] layoutparams = new ViewGroup.LayoutParams[1];

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
                        textView2.setText("\n" + kombo[0] + "    " + kombo[3]);
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
                Toast.makeText(getApplicationContext(),"Error: sjekk internett-tilkobling",Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);

    }
}

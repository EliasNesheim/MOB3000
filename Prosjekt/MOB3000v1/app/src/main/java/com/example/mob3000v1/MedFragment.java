package com.example.mob3000v1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class MedFragment extends Fragment {

    int linje = 6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup.LayoutParams[] layoutparams = new ViewGroup.LayoutParams[1];
        View view = inflater.inflate(R.layout.fragment_med, container, false);
        final ScrollView scrollview = (ScrollView) view.findViewById(R.id.scrollView);
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());

        String url = "http://13.49.175.230/getPosts.php?";
        url += "linje=" + linje;

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

                        LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
                        linearLayout.setOrientation(LinearLayout.VERTICAL);

                        //Sjekker om det finnes innlegg i databasen:
                        if(!response.equals("Ingen innlegg")) {
                            for (int i = 0; i < kombo.length; i++) {

                                String[] post = kombo[i].split(",");

                                // only print IT posts
                                //should be dont in sql at scale
                                //is now done in sql

                                final CardView cardView = new CardView(getActivity().getApplicationContext());
                                cardView.setId(Integer.parseInt(post[5]));

                                cardView.setLayoutParams(layoutparams[0]);
                                cardView.setCardBackgroundColor(13355888);
                                cardView.setRadius(20);
                                cardView.setPadding(25, 25, 25, 25);

                                LinearLayout linearLayout1 = new LinearLayout(getActivity().getApplicationContext());
                                linearLayout1.setOrientation(LinearLayout.VERTICAL);
                                final TextView textView0 = new TextView(getActivity().getApplicationContext());

                                textView0.setText("\n " + post[1]);
                                textView0.setPadding(25, 25, 25, 25);
                                textView0.setGravity(Gravity.CENTER);
                                textView0.setTextSize(25);

                                final TextView textView = new TextView(getActivity().getApplicationContext());
                                textView.setId(i);

                                textView.setText("\n" + post[2]);

                                final TextView textView2 = new TextView(getActivity().getApplicationContext());
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
                                        //        System.out.println("test1234");
                                        openInnlegg(cardView.getId());
                                    }
                                });
                            }
                        } else { //Hvis ingen innlegg finnes i databasen:
                            final CardView cardView = new CardView(getActivity().getApplicationContext());

                            cardView.setLayoutParams(layoutparams[0]);
                            cardView.setCardBackgroundColor(13355888);
                            cardView.setRadius(20);
                            cardView.setPadding(25, 25, 25, 25);

                            LinearLayout linearLayout1 = new LinearLayout(getActivity().getApplicationContext());
                            linearLayout1.setOrientation(LinearLayout.VERTICAL);

                            final TextView textView = new TextView(getActivity().getApplicationContext());

                            textView.setText(R.string.ingen_innlegg);
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
                //Ingen tilkobling:
                Toast.makeText(getActivity(), "Ingen tilkobling", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        return view;
    }

    public void openInnlegg(int i) {
        Intent intent = new Intent(this.getActivity(), enkeltInnlegg.class);
        intent.putExtra("i",i);
        startActivity(intent);
    }
}
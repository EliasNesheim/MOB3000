package com.example.mob3000v1;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    public int innlogget;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem tabIt, tabMark, tabJus, tabBio, tabMed, tabPed;
    public PagerAdapter pagerAdapter;
    private String Brukernavn = "";
    private Button btnLogginn, btnPost;
    ImageView imageView;
    private TextView textView;

    private ActionBarDrawerToggle drawerToggle;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        setupDrawerContent(nvDrawer);
        View header = nvDrawer.getHeaderView(0);

        //Header items
        btnLogginn = header.findViewById(R.id.loginButton);
        btnPost = header.findViewById(R.id.postButton);
        imageView = header.findViewById(R.id.navBilde);
        textView = header.findViewById(R.id.navBruker);

        // TABLAYOUT
        tabLayout = (TabLayout) findViewById(R.id.tabBar);
        tabIt = (TabItem) findViewById(R.id.tabIt);
        tabMark = (TabItem) findViewById(R.id.tabMark);
        tabJus = (TabItem) findViewById(R.id.tabJus);
        tabBio = (TabItem) findViewById(R.id.tabBio);
        tabMed = (TabItem) findViewById(R.id.tabMed);
        tabPed = (TabItem) findViewById(R.id.tabPed);
        viewPager = findViewById(R.id.viewPager);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Henter info om brukeren er innlogget fra logginn klassen
        Intent intent = getIntent();
        innlogget = intent.getIntExtra("innlogget", 0);

        Brukernavn = intent.getStringExtra("brukernavn");
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
                                        .into(imageView);
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println(error);

                    //Feilmelding hvis internettt ikke er koblet til/server er nede.
                    String error_melding = getResources().getString(R.string.error_melding);
                }
            });
            queue.add(stringRequest);

            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView.setText(Brukernavn);
            btnLogginn.setVisibility(View.GONE);

            nvDrawer.getMenu().findItem(R.id.profilside).setVisible(true);
            nvDrawer.getMenu().findItem(R.id.innleggFragment).setVisible(true);
            nvDrawer.getMenu().findItem(R.id.loggut).setVisible(true);
            nvDrawer.getMenu().findItem(R.id.logginn).setVisible(false);
        } else {

        }

        // Change tabs view when the tab is selected or clicked
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 3) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 4) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 5) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    ActionBarDrawerToggle setupDrawerToggle() {

        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open,  R.string.drawer_close);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void setupDrawerContent(NavigationView navigationView) {
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
        intent_profil.putExtra("brukernavn", Brukernavn);
        Intent intent_gps = new Intent(this, GpsActivity.class);
        switch(menuItem.getItemId()) {
            case R.id.hjem:
                if(innlogget == 1) {
                    intent_main.putExtra("brukernavn", Brukernavn);
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
                intent_innlegg.putExtra("brukernavn", Brukernavn);
                startActivity(intent_innlegg);
                break;
            case R.id.GPS:
                startActivity(intent_gps);
                break;
            case R.id.loggut:
                loggUt();
                break;
            case R.id.logginn:
                loggInn(null);
                break;
        }
    }

    public void loggInn(View view) {
        startActivity(new Intent(MainActivity.this, LoggInn.class));
    }

    public void loggUt() {
        sp = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        sp.edit().clear().commit();

        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

    public void Post (View v) {

        Intent i = new Intent(MainActivity.this, PostActivity.class);
        i.putExtra("brukernavn",Brukernavn);
        startActivity(i);

    }
}

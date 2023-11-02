package com.giapvantai.moviestreaming;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.giapvantai.moviestreaming.Fragmentsnetflix.DownloadsFragment;
import com.giapvantai.moviestreaming.Fragmentsnetflix.HomeFragment;
import com.giapvantai.moviestreaming.Fragmentsnetflix.NewsFragment;
import com.giapvantai.moviestreaming.Fragmentsnetflix.Playvideofragment;
import com.giapvantai.moviestreaming.models.Moviemodel;
import com.giapvantai.moviestreaming.viewmodels.Movieviewmodel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Parenntscreen extends AppCompatActivity {
    Toolbar parenttb,downloadstb;

RecyclerView moviesrc,trendingnowrc,onlyonnetflixrc,tvdramasrc,horrorrc;
Movieviewmodel movieviewmodel;
BottomNavigationView bottom_navigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parentscreen);
        parenttb=findViewById(R.id.parentTB);

        bottom_navigation=findViewById(R.id.bottom_navigation);

        downloadstb=findViewById(R.id.downloadsTB);

        downloadstb.setVisibility(View.GONE);
        parenttb.setVisibility(View.VISIBLE);

        setSupportActionBar(parenttb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



       getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment())
                       .commit();

        Intent intent=getIntent();
        if(intent!=null)
        {
            boolean tvshow = intent.getBooleanExtra("tvshow",false);
            boolean movies=intent.getBooleanExtra("movies",false);
            if(tvshow) {
                {
                    Log.e("#", "tv show ");
                    Moviemodel datatv=intent.getParcelableExtra("tvshowdata");
                    Log.e("#", datatv.getGenre_ids().get(0)+" --<");

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.frameContainer, new Playvideofragment(datatv));
                    fragmentTransaction.addToBackStack(null); // Add the transaction to the back stack if you want to navigate back
                    fragmentTransaction.commit();

                }
            }
            else if(movies)
            {
                Moviemodel datatv=intent.getParcelableExtra("moviesdata");
                Log.e("#", datatv.getGenre_ids().get(0)+" --<");

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer, new Playvideofragment(datatv));
                fragmentTransaction.addToBackStack(null); // Add the transaction to the back stack if you want to navigate back
                fragmentTransaction.commit();

            }
            else
                Log.e("#","Normal  ");



        }

        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.HomeMenu:
                        downloadstb.setVisibility(View.GONE);
                        parenttb.setVisibility(View.VISIBLE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new HomeFragment())
                                .commit();


                        break;
                    case R.id.NewsMenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new NewsFragment())
                                .commit();

                        break;

                    case R.id.DownloadsMenu:
                        downloadstb.setVisibility(View.VISIBLE);
                        parenttb.setVisibility(View.GONE);
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,new DownloadsFragment())
                                .commit();

                        break;



                }
                return true;
            }
        });






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.parentmenus,menu);

        return true;
    }

     public Context getContext(){

        return  this;

    }
}
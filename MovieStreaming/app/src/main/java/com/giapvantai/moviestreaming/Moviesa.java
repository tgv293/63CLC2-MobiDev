package com.giapvantai.moviestreaming;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.giapvantai.moviestreaming.Adapters.Moviesadapter;
import com.giapvantai.moviestreaming.models.Moviemodel;
import com.giapvantai.moviestreaming.viewmodels.Movieviewmodel;

import java.util.List;

public class Moviesa extends AppCompatActivity {
    RecyclerView tvshowsrc;
    Movieviewmodel movieviewmodel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviesa);


        tvshowsrc=findViewById(R.id.tvShowsRc);
        movieviewmodel=new Movieviewmodel();
        movieviewmodel= new ViewModelProvider(this).get(Movieviewmodel.class);



        movieviewmodel.getMymovies().observe(this, new Observer<List<Moviemodel>>() {
            @Override
            public void onChanged(List<Moviemodel> moviemodels) {

                Moviesadapter moviesadapter=new Moviesadapter(getApplicationContext(),moviemodels,true,Moviesa.this);
                GridLayoutManager gridLayoutManager= new GridLayoutManager(getApplicationContext(),3);
                tvshowsrc.setLayoutManager(gridLayoutManager);
                tvshowsrc.setAdapter(moviesadapter);

            }
        });
        movieviewmodel.fetchPopularmovies();


    }
    public  void goBackToparentActivtity( Moviemodel data ){

        Log.e("#","its foreign ");

        Intent intent=new Intent(Moviesa.this,Parenntscreen.class);
        intent.putExtra("movies",true);
        intent.putExtra("moviesdata",data);

        startActivity(intent);



    }

}
package com.giapvantai.moviestreaming.Fragmentsnetflix;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.giapvantai.moviestreaming.Adapters.Traileradapter;
import com.giapvantai.moviestreaming.R;
import com.giapvantai.moviestreaming.models.Trailersmodel;
import com.giapvantai.moviestreaming.viewmodels.Movieviewmodel;

import java.util.List;


public class Trailersfragment extends Fragment {
Movieviewmodel movieviewmodel;
RecyclerView trailerrc;
int id;
    public Trailersfragment(int id) {
        // Required empty public constructor
        this.id=id;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_trailersfragment, container, false);
        trailerrc=view.findViewById(R.id.trailerRc);


        movieviewmodel=new Movieviewmodel();
        movieviewmodel  = new ViewModelProvider(this).get(Movieviewmodel.class);



        movieviewmodel.getTrailer().observe(getViewLifecycleOwner(), new Observer<List<Trailersmodel>>() {
            @Override
            public void onChanged(List<Trailersmodel> trailersmodels) {


                LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
                 trailerrc.setLayoutManager(linearLayoutManager);

                Traileradapter traileradapter=new Traileradapter(getContext(),trailersmodels);
                trailerrc.setAdapter(traileradapter);



            }
        });
        movieviewmodel.fetchTrailer(id);
        return  view;
    }
}
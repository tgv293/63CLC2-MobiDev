package com.giapvantai.moviestreaming.Fragmentsnetflix;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.giapvantai.moviestreaming.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link firstslid#newInstance} factory method to
 * create an instance of this fragment.
 */
public class firstslid extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_firstslid, container, false);
    }
}
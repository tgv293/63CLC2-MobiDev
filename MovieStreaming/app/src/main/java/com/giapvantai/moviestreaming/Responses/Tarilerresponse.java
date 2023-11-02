package com.giapvantai.moviestreaming.Responses;

import com.giapvantai.moviestreaming.models.Trailersmodel;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Tarilerresponse {

    @SerializedName("results")
    List<Trailersmodel> trailersmodels;

    public List<Trailersmodel> getTrailersmodels() {
        return trailersmodels;
    }
}

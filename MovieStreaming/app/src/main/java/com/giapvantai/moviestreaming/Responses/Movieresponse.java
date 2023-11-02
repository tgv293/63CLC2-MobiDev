package com.giapvantai.moviestreaming.Responses;

import com.giapvantai.moviestreaming.models.Moviemodel;

import java.util.List;

public class Movieresponse {

    private List<Moviemodel> results;

    public List<Moviemodel> getResults() {
        return results;
    }

    public void setResults(List<Moviemodel> results) {
        this.results = results;
    }
}

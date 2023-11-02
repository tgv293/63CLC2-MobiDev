package com.giapvantai.moviestreaming.Apiservices;

import com.giapvantai.moviestreaming.Apikey;
import com.giapvantai.moviestreaming.Responses.Episodesresponse;
import com.giapvantai.moviestreaming.Responses.Movieresponse;
import com.giapvantai.moviestreaming.Responses.Tarilerresponse;
import com.giapvantai.moviestreaming.models.Durationmodel;
import com.giapvantai.moviestreaming.models.Noofseasonsmodel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Movieapiservice {

    @Headers({"Authorization: Bearer "+Apikey.skey, "accept: application/json"})
    @GET("movie/now_playing")
    Call<Movieresponse> getMovieList();


    @Headers({"Authorization: Bearer "+Apikey.skey, "accept: application/json"})
    @GET("trending/all/day")
    Call<Movieresponse> getTrendingnow();

    @Headers({"Authorization: Bearer "+Apikey.skey, "accept: application/json"})
    @GET("discover/tv?api_key="+ Apikey.apikey +"&with_networks=213")
    Call<Movieresponse> getOnlyonnetflix();


    @Headers({"Authorization: Bearer "+Apikey.skey, "accept: application/json"})
    @GET(" trending/tv/day")
    Call<Movieresponse> getTvdramas();

    @Headers({"Authorization: Bearer "+Apikey.skey, "accept: application/json"})
    @GET("discover/movie?api_key="+ Apikey.apikey +"&with_genres=27")
    Call<Movieresponse> getHorrormovies();

    @Headers({"Authorization: Bearer "+Apikey.skey, "accept: application/json"})
    @GET("movie/{id}?api_key=b606cf25bdecfd350cb4d11ee5ddaec4&language=fr")
    Call<Durationmodel> getDuration(@Path("id") int id);



    @Headers({"Authorization: Bearer "+Apikey.skey, "accept: application/json"})
    @GET("tv/{id}/season/{sno}")
    Call<Episodesresponse> getEpisodes(@Path("id") int id,@Path("sno") int sno);


    @Headers({"Authorization: Bearer "+Apikey.skey, "accept: application/json"})
    @GET("tv/{id}?append_to_response=season/1")
    Call<Noofseasonsmodel> getNofseasons(@Path("id") int id,@Query("api_key") String api_key);



    @Headers({"Authorization: Bearer "+Apikey.skey, "accept: application/json"})
    @GET("https://api.themoviedb.org/3/discover/movie?api_key=b606cf25bdecfd350cb4d11ee5ddaec4")
    Call<Movieresponse> getMorelikethis(  @Query("api_key") String api_key, @Query("with_genres") int genreid);

    @Headers({"Authorization: Bearer "+Apikey.skey, "accept: application/json"})
    @GET("https://api.themoviedb.org/3/movie/{id}/videos")
    Call<Tarilerresponse> getTrailer(@Path("id") int id,  @Query("api_key") String api_key);
}




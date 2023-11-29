package vn.giapvantai.moviesapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import vn.giapvantai.moviesapp.R;
import vn.giapvantai.moviesapp.adapter.ActorsListAdapter;
import vn.giapvantai.moviesapp.adapter.CategoryEachFilmListAdapter;
import vn.giapvantai.moviesapp.domain.FilmItem;

public class DetailsActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://moviesapi.ir/api/v1/movies/";

    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private ProgressBar progressBar;
    private TextView titleTxt, movieRateTxt, movieTimeTxt, movieSummary, movieActors;
    private int idFilm;
    private ImageView moviePic, backBtn;
    private RecyclerView.Adapter adapterActorsList, adapterCategory;
    private RecyclerView recyclerViewActors, recyclerViewCategory;
    private NestedScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        idFilm = getIntent().getIntExtra("id", 0);
        initView();
        sendRequest();
    }

    private void initView() {
        titleTxt = findViewById(R.id.tvMovieName);
        progressBar = findViewById(R.id.progressBarDetails);
        scrollView = findViewById(R.id.nestedScrollView);
        moviePic = findViewById(R.id.moviePic);
        movieRateTxt = findViewById(R.id.tvMovieStar);
        movieTimeTxt = findViewById(R.id.tvMovieTime);
        movieSummary = findViewById(R.id.movieSummary);
        movieActors = findViewById(R.id.actorsInfo);
        backBtn = findViewById(R.id.imageViewBack);
        recyclerViewActors = findViewById(R.id.imageRecycler);
        recyclerViewCategory = findViewById(R.id.genreView);
        recyclerViewActors.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        backBtn.setOnClickListener(v -> finish());
    }

    private void sendRequest() {
        mRequestQueue = Volley.newRequestQueue(this);
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        mStringRequest = new StringRequest(Request.Method.GET, BASE_URL + idFilm,
                this::handleResponse,
                error -> handleError()
        );

        mRequestQueue.add(mStringRequest);
    }

    private void handleResponse(String response) {
        Gson gson = new Gson();
        progressBar.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);

        FilmItem item = gson.fromJson(response, FilmItem.class);

        Glide.with(DetailsActivity.this)
                .load(item.getPoster())
                .into(moviePic);

        titleTxt.setText(item.getTitle());
        movieRateTxt.setText(item.getImdbRating());
        movieTimeTxt.setText(item.getRuntime());
        movieSummary.setText(item.getPlot());
        movieActors.setText(item.getActors());

        if (item.getImages() != null) {
            adapterActorsList = new ActorsListAdapter(item.getImages());
            recyclerViewActors.setAdapter(adapterActorsList);
        }
        if (item.getGenres() != null) {
            adapterCategory = new CategoryEachFilmListAdapter(item.getGenres());
            recyclerViewCategory.setAdapter(adapterCategory);
        }
    }

    private void handleError() {
        progressBar.setVisibility(View.GONE);
        // Handle error (e.g., show a message)
    }
}

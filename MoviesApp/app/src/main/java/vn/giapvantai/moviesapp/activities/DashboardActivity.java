package vn.giapvantai.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import vn.giapvantai.moviesapp.R;
import vn.giapvantai.moviesapp.adapter.CategoryListAdapter;
import vn.giapvantai.moviesapp.adapter.FilmListAdapter;
import vn.giapvantai.moviesapp.adapter.SliderAdapters;
import vn.giapvantai.moviesapp.domain.GenresItem;
import vn.giapvantai.moviesapp.domain.ListFilm;
import vn.giapvantai.moviesapp.domain.SliderItems;

public class DashboardActivity extends AppCompatActivity {

    private static final String BEST_MOVIES_URL = "https://moviesapi.ir/api/v1/movies?page=1";
    private static final String UPCOMING_MOVIES_URL = "https://moviesapi.ir/api/v1/movies?page=2";
    private static final String CATEGORY_URL = "https://moviesapi.ir/api/v1/genres";

    private final Handler sliderHandler = new Handler();
    private RecyclerView.Adapter adapterBestMovies, adapterUpComming, adapterCategory;
    private RecyclerView recyclerViewBestMovies, recyclerViewUpComming, recyclerViewCategory;
    private RequestQueue mRequestQueue;
    private ProgressBar loading1, loading2, loading3;
    private ViewPager2 viewPager2;
    private TextView profileBtn;
    private final Runnable sliderRunnable = () -> viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initView();
        banners();
        sendRequest(BEST_MOVIES_URL, loading1, this::handleBestMoviesResponse);
        sendRequest(UPCOMING_MOVIES_URL, loading3, this::handleUpcomingMoviesResponse);
        sendRequest(CATEGORY_URL, loading2, this::handleCategoryResponse);

        profileBtn.setOnClickListener(v -> goToProfile());
    }

    private void goToProfile() {
        startActivity(new Intent(DashboardActivity.this, ProifileActivity.class));
    }

    private void sendRequest(String url, ProgressBar loading, Response.Listener<String> onResponse) {
        mRequestQueue = Volley.newRequestQueue(this);
        loading.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, onResponse,
                error -> {
                    loading.setVisibility(View.GONE);
                    Log.i("MoviesApp", "onErrorResponse: " + error.toString());
                }
        );

        mRequestQueue.add(stringRequest);
    }

    private void handleBestMoviesResponse(String response) {
        Gson gson = new Gson();
        loading1.setVisibility(View.GONE);
        ListFilm items = gson.fromJson(response, ListFilm.class);
        adapterBestMovies = new FilmListAdapter(items);
        recyclerViewBestMovies.setAdapter(adapterBestMovies);
    }

    private void handleUpcomingMoviesResponse(String response) {
        Gson gson = new Gson();
        loading3.setVisibility(View.GONE);
        ListFilm items = gson.fromJson(response, ListFilm.class);
        adapterUpComming = new FilmListAdapter(items);
        recyclerViewUpComming.setAdapter(adapterUpComming);
    }

    private void handleCategoryResponse(String response) {
        Gson gson = new Gson();
        loading2.setVisibility(View.GONE);
        ArrayList<GenresItem> cateList = gson.fromJson(response, new TypeToken<ArrayList<GenresItem>>() {}.getType());
        adapterCategory = new CategoryListAdapter(cateList);
        recyclerViewCategory.setAdapter(adapterCategory);
    }

    private void banners() {
        List<SliderItems> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItems(R.drawable.wide1));
        sliderItems.add(new SliderItems(R.drawable.wide2));
        sliderItems.add(new SliderItems(R.drawable.wide3));

        viewPager2.setAdapter(new SliderAdapters(sliderItems, viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer((page, position) -> {
            float r = 1 - Math.abs(position);
            page.setScaleY(0.85f + r * 0.15f);
        });

        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.setCurrentItem(1);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 2000);
    }

    private void initView() {
        viewPager2 = findViewById(R.id.viewpagerslidebanner);
        profileBtn = findViewById(R.id.profile);

        recyclerViewBestMovies = findViewById(R.id.recyclerViewBM);
        recyclerViewBestMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory = findViewById(R.id.recyclerViewCT);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewUpComming = findViewById(R.id.recyclerViewUC);
        recyclerViewUpComming.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        loading1 = findViewById(R.id.progressBarBM);
        loading2 = findViewById(R.id.progressBarCT);
        loading3 = findViewById(R.id.progressBarUC);
    }
}

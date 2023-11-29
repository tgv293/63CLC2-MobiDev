package vn.giapvantai.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

import vn.giapvantai.moviesapp.R;
import vn.giapvantai.moviesapp.adapter.IntroBannerPagerAdapter;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private IntroBannerPagerAdapter adapter;
    private TabLayout tabLayout;
    private Timer sliderTimer;
    private SparseArray<View> menuItems;

    private static final long AUTO_SLIDER_DELAY = 3000;
    private static final long AUTO_SLIDER_PERIOD = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isLoggedIn()) {
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            finish();
        } else {
            setContentView(R.layout.activity_main);
            setupViews();
            menuItems = new SparseArray<>();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoSlider();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoSlider();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopAutoSlider();
    }

    private void setupViews() {
        setupToolbar();
        setupViewPager();
        setupGetStartedButton();
        setupAutoSlider();
    }

    private boolean isLoggedIn() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.getstatrtedtb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.submenusfandhelp, menu);

        setupMenuItem(menu, R.id.privacymenu);
        setupMenuItem(menu, R.id.signinmenu);

        return true;
    }

    private void setupMenuItem(Menu menu, int itemId) {
        MenuItem item = menu.findItem(itemId);
        View view = menuItems.get(itemId);
        if (view == null) {
            view = View.inflate(this, R.layout.menu_item, null);
            menuItems.put(itemId, view);
        }
        TextView title = view.findViewById(R.id.item_title);
        title.setText(item.getTitle());
        view.setOnClickListener(v -> onOptionsItemSelected(item));
        item.setActionView(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.signinmenu) {
            navigateToLoginActivity();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void setupViewPager() {
        viewPager2 = findViewById(R.id.getstartedviewpager);
        adapter = new IntroBannerPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        tabLayout = findViewById(R.id.tab_indicator);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
        }).attach();
    }

    private void setupAutoSlider() {
        sliderTimer = new Timer();
    }

    private void startAutoSlider() {
        sliderTimer.scheduleAtFixedRate(new AutoSlider(), AUTO_SLIDER_DELAY, AUTO_SLIDER_PERIOD);
    }

    private void stopAutoSlider() {
        if (sliderTimer != null) {
            sliderTimer.cancel();
            sliderTimer = null;
        }
    }

    private class AutoSlider extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread(() -> {
                if (viewPager2.getCurrentItem() < adapter.getItemCount() - 1) {
                    viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
                } else {
                    viewPager2.setCurrentItem(0);
                }
            });
        }
    }

    private void setupGetStartedButton() {
        MaterialButton getStartedButton = findViewById(R.id.getstartedbtn);
        getStartedButton.setOnClickListener(v -> navigateToLoginActivity());
    }
}

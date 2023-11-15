package vn.giapvantai.videostreaming;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import vn.giapvantai.videostreaming.fragments.ExploreFragment;
import vn.giapvantai.videostreaming.fragments.HomeFragment;
import vn.giapvantai.videostreaming.fragments.LibraryFragment;
import vn.giapvantai.videostreaming.fragments.SubscriptionsFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    SparseArray<Fragment> fragments = new SparseArray<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        frameLayout = findViewById(R.id.frame_layout);

        fragments.put(R.id.home, new HomeFragment());
        fragments.put(R.id.explore, new ExploreFragment());
        fragments.put(R.id.subscriptions, new SubscriptionsFragment());
        fragments.put(R.id.library, new LibraryFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment selectedFragment = fragments.get(id);
            if (selectedFragment != null) {
                selectedFragment(selectedFragment);
                return true;
            } else if (id == R.id.publish) {
                Toast.makeText(MainActivity.this, "Add A Video", Toast.LENGTH_SHORT).show();
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    private void selectedFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notification:
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show();
                break;

            case R.id.search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;

            case R.id.account:
                Toast.makeText(this, "Account", Toast.LENGTH_SHORT).show();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }
}
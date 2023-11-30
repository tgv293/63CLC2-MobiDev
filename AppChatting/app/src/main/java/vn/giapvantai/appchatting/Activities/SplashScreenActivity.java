package vn.giapvantai.appchatting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import vn.giapvantai.appchatting.R;
import vn.giapvantai.appchatting.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    private Animation topAnim, bottomAnim;
    private FirebaseAuth auth;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadAnimations();
        setAnimations();

        auth = FirebaseAuth.getInstance();

        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus++;
                android.os.SystemClock.sleep(20);
                handler.post(() -> binding.loadingProgressBar.setProgress(progressStatus));
            }
        }).start();

        // Check if the user is already logged in
        FirebaseUser user = auth.getCurrentUser();
        long delayMillis = (user != null) ? 900 : 1900;

        new Handler().postDelayed(() -> {
            if (user != null) {
                checkUserProfileAndNavigate();
            } else {
                navigateToLogin();
            }
        }, delayMillis);
    }

    private void checkUserProfileAndNavigate() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            DatabaseReference reference = getUserReference(firebaseUser);
            checkUserProfile(reference);
        }
    }

    private DatabaseReference getUserReference(FirebaseUser firebaseUser) {
        return FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
    }

    private void checkUserProfile(DatabaseReference reference) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                handleDataChange(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showToast("An error occurred: " + error.getMessage());
            }
        });
    }

    private void handleDataChange(DataSnapshot snapshot) {
        if (isProfileIncomplete(snapshot)) {
            navigateToSetupProfile();
        } else {
            navigateToMain();
        }
    }

    private boolean isProfileIncomplete(DataSnapshot snapshot) {
        String name = snapshot.child("name").getValue(String.class);
        String imageUrl = snapshot.child("profileImage").getValue(String.class);
        return name == null || name.isEmpty() || imageUrl == null || imageUrl.isEmpty();
    }

    private void navigateToLogin() {
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        finish();
    }

    private void navigateToSetupProfile() {
        startActivity(new Intent(SplashScreenActivity.this, SetupProfileActivity.class));
        finish();
    }

    private void navigateToMain() {
        startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
        finish();
    }

    private void loadAnimations() {
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
    }

    private void setAnimations() {
        binding.topLayout.setAnimation(topAnim);
        binding.loadingProgressBar.setAnimation(bottomAnim);
    }

    private void showToast(String message) {
        Toast.makeText(SplashScreenActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

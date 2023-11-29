package vn.giapvantai.chattingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {
    private ImageView logo;
    private TextView name, own1, own2;
    private Animation topAnim, bottomAnim;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        initializeViews();
        loadAnimations();
        setAnimations();

        auth = FirebaseAuth.getInstance();
        new Handler().postDelayed(() -> {
            if (auth.getCurrentUser() != null) {
                // User is already signed in, go to MainActivity
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            } else {
                // User is not signed in, go to LoginActivity
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            }
            finish();
        }, 4000);
    }

    private void initializeViews() {
        logo = findViewById(R.id.logoimg);
        name = findViewById(R.id.logonameimg);
        own1 = findViewById(R.id.ownone);
        own2 = findViewById(R.id.owntwo);
    }

    private void loadAnimations() {
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
    }

    private void setAnimations() {
        logo.setAnimation(topAnim);
        name.setAnimation(topAnim);
        own1.setAnimation(bottomAnim);
        own2.setAnimation(bottomAnim);
    }
}

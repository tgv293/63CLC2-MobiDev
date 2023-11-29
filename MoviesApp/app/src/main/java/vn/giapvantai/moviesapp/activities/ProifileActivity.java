package vn.giapvantai.moviesapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import com.google.android.material.button.MaterialButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import vn.giapvantai.moviesapp.R;

public class ProifileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private ImageView backMain;
    private MaterialButton signoutbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proifile);

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        backMain = findViewById(R.id.backDash);
        signoutbtn = findViewById(R.id.signoutbtn);
    }

    private void setupListeners() {
        backMain.setOnClickListener(v -> navigateToDashboard());
        signoutbtn.setOnClickListener(v -> signOutAndNavigateToLogin());
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(ProifileActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void signOutAndNavigateToLogin() {
        // Sign out from Firebase
        mAuth.signOut();

        // Also sign out from Google
        mGoogleSignInClient.signOut();

        // Go back to LoginActivity
        Intent intent = new Intent(ProifileActivity.this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}

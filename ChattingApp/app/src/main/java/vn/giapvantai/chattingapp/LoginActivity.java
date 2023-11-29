package vn.giapvantai.chattingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private TextView logsignup, logforgot;
    private Button button;
    private EditText email, password;
    private FirebaseAuth auth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            checkUserDataAndNavigate();
        } else {
            setContentView(R.layout.activity_login);
            initializeViews();
            setOnClickListeners();
        }
    }

    private void checkUserDataAndNavigate() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(Objects.requireNonNull(auth.getCurrentUser()).getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    signOutAndNavigateToLogin();
                } else {
                    navigateToMainActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });
    }

    private void signOutAndNavigateToLogin() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
        finish();
    }

    private void navigateToMainActivity() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void initializeViews() {
        progressBar = findViewById(R.id.progressBar);
        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.logbutton);
        email = findViewById(R.id.editTexLogEmail);
        password = findViewById(R.id.editTextLogPassword);
        logsignup = findViewById(R.id.logsignup);
        logforgot = findViewById(R.id.logforgot);
    }

    private void setOnClickListeners() {
        logsignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        logforgot.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPassActivity.class));
            finish();
        });

        button.setOnClickListener(v -> {
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            if (TextUtils.isEmpty(userEmail)) {
                showToast("Enter The Email");
            } else if (TextUtils.isEmpty(userPassword)) {
                showToast("Enter The Password");
            } else if (!userEmail.matches(emailPattern)) {
                email.setError("Give Proper Email Address");
            } else if (userPassword.length() < 6) {
                password.setError("More Than Six Characters");
                showToast("Password Needs To Be Longer Than Six Characters");
            } else {
                signIn(userEmail, userPassword);
            }
        });
    }

    private void signIn(String userEmail, String userPassword) {
        auth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.VISIBLE);
            if (task.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                navigateToMainActivity();
            } else {
                progressBar.setVisibility(View.GONE);
                showToast(Objects.requireNonNull(task.getException()).getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

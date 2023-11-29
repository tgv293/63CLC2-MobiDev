package vn.giapvantai.appchatting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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

import java.util.Objects;

import vn.giapvantai.appchatting.Models.User;
import vn.giapvantai.appchatting.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private final String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
    private FirebaseAuth auth;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        auth = FirebaseAuth.getInstance();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setOnClickListeners();
    }

    private void checkUserProfileAndNavigate() {
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            DatabaseReference reference = getUserReference(firebaseUser);
            checkUserProfile(reference, firebaseUser);
        }
    }

    private DatabaseReference getUserReference(FirebaseUser firebaseUser) {
        return FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
    }

    private void checkUserProfile(DatabaseReference reference, FirebaseUser firebaseUser) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                handleDataChange(snapshot, reference, firebaseUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showToast("An error occurred: " + error.getMessage());
            }
        });
    }

    private void handleDataChange(DataSnapshot snapshot, DatabaseReference reference, FirebaseUser firebaseUser) {
        if (!snapshot.exists()) {
            createUserProfile(reference, firebaseUser);
            navigateToSetupProfile();
        } else if (isProfileIncomplete(snapshot)) {
            navigateToSetupProfile();
        } else {
            navigateToMain();
        }
    }

    private void createUserProfile(DatabaseReference reference, FirebaseUser firebaseUser) {
        User model = new User(firebaseUser.getUid(), "", firebaseUser.getEmail(), "");
        reference.setValue(model, (error, ref) -> {
            if (error != null) {
                showToast("An error occurred: " + error.getMessage());
            } else {
                navigateToSetupProfile();
            }
        });
    }

    private boolean isProfileIncomplete(DataSnapshot snapshot) {
        String name = snapshot.child("name").getValue(String.class);
        String imageUrl = snapshot.child("profileImage").getValue(String.class);
        return name == null || name.isEmpty() || imageUrl == null || imageUrl.isEmpty();
    }

    private void navigateToSetupProfile() {
        startActivity(new Intent(LoginActivity.this, SetupProfileActivity.class));
        finish();
    }

    private void navigateToMain() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void setOnClickListeners() {
        binding.tvSignup.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        binding.tvForgotPass.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            finish();
        });

        binding.loginBtn.setOnClickListener(v -> {
            String userEmail = Objects.requireNonNull(binding.emailBox.getText()).toString();
            String userPassword = Objects.requireNonNull(binding.passwordBox.getText()).toString();

            if (TextUtils.isEmpty(userEmail)) {
                showToast("Enter The Email");
            } else if (TextUtils.isEmpty(userPassword)) {
                showToast("Enter The Password");
            } else if (!userEmail.matches(emailPattern)) {
                binding.emailBox.setError("Give Proper Email Address");
            } else {
                signIn(userEmail, userPassword);
            }
        });
    }

    private void signIn(String userEmail, String userPassword) {
        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            if (user.isEmailVerified()) {
                                // User is signed in and email is verified, you can allow them to proceed
                                showToast("Login Successful.");
                                checkUserProfileAndNavigate();
                            } else {
                                // User's email is not verified, you can prompt them to verify their email
                                showToast("Please verify your email before logging in.");
                                FirebaseAuth.getInstance().signOut();
                            }
                        }
                    } else {
                        // If sign in fails, display a message to the user.
                        showToast("Authentication failed.");
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
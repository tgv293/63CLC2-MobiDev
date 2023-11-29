package vn.giapvantai.appchatting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import vn.giapvantai.appchatting.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    private final String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        binding.tvMoveToLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.continueBtn.setOnClickListener(v -> signUp());
    }

    private void signUp() {
        String email = Objects.requireNonNull(binding.regEmailBox.getText()).toString();
        String password = Objects.requireNonNull(binding.regPasswordBox.getText()).toString();
        String repassword = Objects.requireNonNull(binding.regRepasswordBox.getText()).toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
            Toast.makeText(RegisterActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            binding.regEmailBox.setError("Please enter a valid email");
        } else if (!password.equals(repassword)) {
            binding.regPasswordBox.setError("Passwords do not match");
            binding.regRepasswordBox.setError("Passwords do not match");
        } else if (password.length() < 6) {
            binding.regPasswordBox.setError("Password must be at least 6 characters long");
        } else if (!password.matches("(.*[A-Z].*)")) {
            binding.regPasswordBox.setError("Password must contain at least one uppercase letter");
        } else if (!password.matches("(.*[a-z].*)")) {
            binding.regPasswordBox.setError("Password must contain at least one lowercase letter");
        } else if (!password.matches("(.*\\d.*)")) {
            binding.regPasswordBox.setError("Password must contain at least one digit");
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                user.sendEmailVerification()
                                        .addOnCompleteListener(verificationTask -> {
                                            if (verificationTask.isSuccessful()) {
                                                showToast("Registered successfully. Please check your email for verification");
                                                auth.signOut();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                showToast("Failed to send verification email");
                                            }
                                        });
                            }
                        } else {
                            showToast("Failed to register");
                        }
                    });
        }
    }

    private void showToast(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

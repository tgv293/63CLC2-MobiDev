package vn.giapvantai.appchatting.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import vn.giapvantai.appchatting.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();

        binding.tvBackToLogin.setOnClickListener(v -> startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class)));

        binding.fpConfirmBtn.setOnClickListener(v -> {
            String userEmail = Objects.requireNonNull(binding.fpEmailBox.getText()).toString();

            if (TextUtils.isEmpty(userEmail)) {
                showToast("Enter your email");
            } else {
                auth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                showToast("Email sent. Please check your email for instructions");
                                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                showToast("Failed to send email. Please try again");
                            }
                        });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

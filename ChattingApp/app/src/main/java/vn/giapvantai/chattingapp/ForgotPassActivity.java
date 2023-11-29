package vn.giapvantai.chattingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassActivity extends AppCompatActivity {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
    private EditText emailEditText;
    private Button resetPasswordButton;
    private FirebaseAuth firebaseAuth;
    private TextView loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        firebaseAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.rgemail);
        resetPasswordButton = findViewById(R.id.forgotbutton);
        loginBtn = findViewById(R.id.fg_loginbut);

        loginBtn.setOnClickListener(v -> {
            startActivity(new Intent(ForgotPassActivity.this, LoginActivity.class));
            finish();
        });

        resetPasswordButton.setOnClickListener(v -> {
            String userEmail = emailEditText.getText().toString().trim();

            if(TextUtils.isEmpty(userEmail)){
                showToast(getString(R.string.enter_registered_email));
            } else if (!userEmail.matches(EMAIL_PATTERN)) {
                emailEditText.setError(getString(R.string.invalid_email_error));
            } else {
                sendResetPasswordEmail(userEmail);
            }
        });
    }

    private void sendResetPasswordEmail(String userEmail) {
        firebaseAuth.sendPasswordResetEmail(userEmail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        showToast(getString(R.string.password_reset_email_sent));
                    } else {
                        showToast(getString(R.string.error_sending_password_reset_email));
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(ForgotPassActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

package vn.giapvantai.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import vn.giapvantai.moviesapp.R;

public class ForgotActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextInputEditText emailEditText;
    private TextInputLayout emailInputLayout;
    private MaterialButton sendEmailButton;
    private ImageView backLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        initializeUI();
        setOnClickListeners();
    }

    private void initializeUI() {
        auth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.fp_et_email);
        emailInputLayout = findViewById(R.id.fp_emailsec);
        sendEmailButton = findViewById(R.id.sendemailbtn);
        backLogin = findViewById(R.id.backLogin);
    }

    private void setOnClickListeners() {
        sendEmailButton.setOnClickListener(v -> sendPasswordResetEmail());
        backLogin.setOnClickListener(v -> goBackToLogin());
    }

    private void sendPasswordResetEmail() {
        String emailAddress = Objects.requireNonNull(emailEditText.getText()).toString();

        if (emailAddress.isEmpty()) {
            emailInputLayout.setError("Vui lòng nhập email");
        } else {
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            goBackToLogin();
                        } else {
                            Toast.makeText(ForgotActivity.this, "Lỗi trong việc gửi email đặt lại mật khẩu.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void goBackToLogin() {
        Intent intent = new Intent(ForgotActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

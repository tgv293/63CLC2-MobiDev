package vn.giapvantai.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

import vn.giapvantai.moviesapp.R;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;

    private TextView registerTextView, forgotTextView;
    private TextInputEditText emailEditText, passwordEditText;
    private MaterialButton signInButton;
    private ImageView backBtn;
    private MaterialButton googleSignInButton;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setOnClickListeners();
        addTextWatchers();

        signInButton.setOnClickListener(v -> signInWithEmailPassword());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInButton.setOnClickListener(v -> signInWithGoogle(mGoogleSignInClient));
    }

    private void signInWithGoogle(GoogleSignInClient googleSignInClient) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            handleGoogleSignInResult(data);
        }
    }

    private void handleGoogleSignInResult(Intent data) {
        try {
            GoogleSignInAccount account = GoogleSignIn.getSignedInAccountFromIntent(data).getResult(ApiException.class);
            if (account != null) {
                firebaseAuthWithGoogle(account.getIdToken());
            }
        } catch (ApiException e) {
            Log.w("Login", "Google sign-in failed", e);
            showToast("Google sign-in failed");
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener(this, this::handleGoogleSignInComplete);
    }

    private void handleGoogleSignInComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Log.d("Login", "signInWithCredential:success");
            showToast(getString(R.string.login_success));
            navigateToDashboard();
        } else {
            showToast(getString(R.string.login_failed));
        }
    }

    private void signInWithEmailPassword() {
        String email = Objects.requireNonNull(emailEditText.getText()).toString();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, this::handleEmailSignInComplete);
    }

    private void handleEmailSignInComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            showToast(getString(R.string.login_success));
            navigateToDashboard();
        } else {
            showToast(getString(R.string.login_failed));
        }
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initializeViews() {
        registerTextView = findViewById(R.id.register);
        forgotTextView = findViewById(R.id.forgotpass);
        emailEditText = findViewById(R.id.et_email);
        passwordEditText = findViewById(R.id.signinpass);
        signInButton = findViewById(R.id.signinbtn);
        backBtn = findViewById(R.id.backMain);
        googleSignInButton = findViewById(R.id.google_signin_btn);
    }

    private void setOnClickListeners() {
        registerTextView.setOnClickListener(v -> openActivity(RegisterActivity.class));
        forgotTextView.setOnClickListener(v -> openActivity(ForgotActivity.class));
        backBtn.setOnClickListener(v -> openActivity(MainActivity.class));
    }

    private void openActivity(Class<?> cls) {
        Intent intent = new Intent(LoginActivity.this, cls);
        startActivity(intent);
        finish();
    }

    private void addTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSignInButtonState();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        emailEditText.addTextChangedListener(textWatcher);
        passwordEditText.addTextChangedListener(textWatcher);
    }

    private void updateSignInButtonState() {
        String email = Objects.requireNonNull(emailEditText.getText()).toString();
        String password = Objects.requireNonNull(passwordEditText.getText()).toString();

        if (isValidInput(email) && isValidInput(password)) {
            signInButton.setBackgroundColor(getResources().getColor(R.color.red, getTheme()));
            signInButton.setStrokeWidth(0);
        } else {
            signInButton.setBackgroundColor(getResources().getColor(R.color.light_dark, getTheme()));
            int strokeWidth = (int) (2 * getResources().getDisplayMetrics().density + 0.5f);
            signInButton.setStrokeWidth(strokeWidth);
            signInButton.setStrokeColorResource(R.color.gray_border);
        }
    }

    private boolean isValidInput(String input) {
        return !input.isEmpty() && input.length() > 4;
    }
}

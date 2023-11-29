package vn.giapvantai.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vn.giapvantai.moviesapp.R;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText emailField, passwordField;
    private TextInputLayout emailLayout, passwordLayout;
    private SparseArray<View> menuItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setupToolbar();
        menuItems = new SparseArray<>();
        initializeViews();
        setOnClickListeners();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.signuptb);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.signupmenus, menu);

        setupMenuItem(menu, R.id.signuphelp);
        setupMenuItem(menu, R.id.signupsignin);

        return true;
    }

    private void setupMenuItem(Menu menu, int itemId) {
        MenuItem item = menu.findItem(itemId);
        View view = menuItems.get(itemId);
        if (view == null) {
            view = View.inflate(this, R.layout.menu_item, null);
            menuItems.put(itemId, view);
        }
        TextView title = view.findViewById(R.id.item_title);
        title.setText(item.getTitle());
        view.setOnClickListener(v -> onOptionsItemSelected(item));
        item.setActionView(view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.signupsignin) {
            navigateToLoginActivity();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void initializeViews() {
        mAuth = FirebaseAuth.getInstance();
        emailField = findViewById(R.id.reg_et_email);
        passwordField = findViewById(R.id.reg_signinpass);
        emailLayout = findViewById(R.id.reg_emailsec);
        passwordLayout = findViewById(R.id.reg_passwordsec);

        setupTextWatchers();
    }

    private void setupTextWatchers() {
        emailField.addTextChangedListener(createTextWatcher(emailLayout, this::validateEmail));
        passwordField.addTextChangedListener(createTextWatcher(passwordLayout, this::validatePassword));
    }

    private TextWatcher createTextWatcher(final TextInputLayout layout, final ValidationCallback validationCallback) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                validationCallback.validate(charSequence.toString(), layout);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }

    private void setOnClickListeners() {
        findViewById(R.id.registerbtn).setOnClickListener(view -> registerUser());
    }


    private void registerUser() {
        String email = Objects.requireNonNull(emailField.getText()).toString();
        String password = Objects.requireNonNull(passwordField.getText()).toString();

        if (validateForm(email, password)) {
            register(email, password);
        }
    }

    private boolean validateForm(String email, String password) {
        return validateEmail(email, emailLayout) && validatePassword(password, passwordLayout);
    }

    private boolean validateEmail(String email, TextInputLayout layout) {
        if (TextUtils.isEmpty(email)) {
            showValidationError(layout, getString(R.string.empty_email_error));
            return false;
        } else if (!isEmailValid(email)) {
            showValidationError(layout, getString(R.string.invalid_email_error));
            return false;
        } else {
            clearValidationError(layout);
            return true;
        }
    }

    private boolean validatePassword(String password, TextInputLayout layout) {
        if (TextUtils.isEmpty(password)) {
            showValidationError(layout, "Vui lòng nhập mật khẩu");
            return false;
        } else if (!isPasswordValid(password)) {
            return false;
        } else {
            clearValidationError(layout);
            return true;
        }
    }

    private void showValidationError(TextInputLayout layout, String errorMessage) {
        layout.setError(errorMessage);
    }

    private void clearValidationError(TextInputLayout layout) {
        layout.setError(null);
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isPasswordValid(String password) {
        if (password.length() < 6) {
            showValidationError(passwordLayout, "Mật khẩu phải chứa ít nhất 6 ký tự");
            return false;
        }

        String[] regexArray = {
                "(.*[a-z].*)",
                "(.*[A-Z].*)",
                "(.*\\d.*)",
                "(.*[-+_!@#$%^&*., ?].*)"
        };

        String[] errorMessages = {
                "Mật khẩu phải chứa ít nhất một chữ cái viết thường",
                "Mật khẩu phải chứa ít nhất một chữ cái viết hoa",
                "Mật khẩu phải chứa ít nhất một số",
                "Mật khẩu phải chứa ít nhất một ký tự đặc biệt"
        };

        for (int i = 0; i < regexArray.length; i++) {
            if (!password.matches(regexArray[i])) {
                showValidationError(passwordLayout, errorMessages[i]);
                return false;
            }
        }

        return true;
    }

    private void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showToast(getString(R.string.registration_successful));
                        startLoginActivity();
                    } else {
                        showToast(getString(R.string.registration_failed));
                    }
                });
    }

    private void showToast(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private void startLoginActivity() {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    private interface ValidationCallback {
        void validate(String input, TextInputLayout layout);
    }
}

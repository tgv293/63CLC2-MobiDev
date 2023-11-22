package vn.giapvantai.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    private EditText input_username, input_password;
    private Button btn_login;
    private TextView registration;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        input_username = findViewById(R.id.input_username);
        input_password = findViewById(R.id.input_password);
        btn_login = findViewById(R.id.btn_login);
        registration = findViewById(R.id.registration);
    }

    private void setupClickListeners() {
        btn_login.setOnClickListener(v -> onLoginButtonClick());

        registration.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));
    }

    private void onLoginButtonClick() {
        String usernameOrEmail = input_username.getText().toString().trim();
        String password = input_password.getText().toString().trim();

        if (TextUtils.isEmpty(usernameOrEmail) || TextUtils.isEmpty(password)) {
            showToast(getString(R.string.empty_fields_message));
            return;
        }

        if (usernameOrEmail.contains("@")) {
            loginWithFirebaseEmail(usernameOrEmail, password);
        } else {
            searchUsernameInDatabase(usernameOrEmail, password);
        }
    }

    private void loginWithFirebaseEmail(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        showToast(getString(R.string.login_success));
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String username = user.getDisplayName();
                            // Hiển thị Toast
                            String welcomeMessage = getString(R.string.welcomeback) + " " + username;
                            showToast(welcomeMessage);

                            // Chuyển sang HomeActivity
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        }
                    } else {
                        showToast(getString(R.string.login_failure));
                    }
                });
    }

    private void searchUsernameInDatabase(String username, String password) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("USERS");
        usersRef.orderByChild("username").equalTo(username)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        runOnUiThread(() -> handleDatabaseQueryResult(dataSnapshot, password));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        runOnUiThread(() -> showToast(getString(R.string.username_check_error_message)));
                    }
                });
    }

    private void handleDatabaseQueryResult(DataSnapshot dataSnapshot, String password) {
        if (dataSnapshot.exists()) {
            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                User user = userSnapshot.getValue(User.class);
                if (user != null && user.getEmail() != null) {
                    loginWithFirebaseEmail(user.getEmail(), password);
                    return; // Giả sử chỉ có một người dùng với tên người dùng đã cho
                }
            }
        } else {
            showToast(getString(R.string.username_not_exist));
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}

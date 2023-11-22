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

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText input_username, input_email, input_password, input_repassword;
    private Button btn_register;
    private TextView login;

    private FirebaseAuth mAuth;
    private DatabaseReference usersRef;

    public static boolean isValid(String password) {
        // Mật khẩu phải có ít nhất 6 ký tự, chứa ít nhất một chữ cái, một chữ số và một ký tự đặc biệt
        String regex = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";
        return password.matches(regex);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeFirebase();
        initializeViews();

        btn_register.setOnClickListener(v -> registerUser());

        login.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));
    }

    private void initializeFirebase() {
        mAuth = FirebaseAuth.getInstance();
        usersRef = FirebaseDatabase.getInstance().getReference("USERS");
    }

    private void initializeViews() {
        input_username = findViewById(R.id.input_reg_username);
        input_email = findViewById(R.id.input_reg_email);
        input_password = findViewById(R.id.input_reg_password);
        input_repassword = findViewById(R.id.input_reg_repassword);
        btn_register = findViewById(R.id.btn_register);
        login = findViewById(R.id.login);
    }

    private void registerUser() {
        String username = input_username.getText().toString().trim();
        String email = input_email.getText().toString().trim();
        String password = input_password.getText().toString().trim();
        String re_password = input_repassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(re_password)) {
            showToast(getString(R.string.empty_fields_message));
            return;
        }

        if (!password.equals(re_password)) {
            showToast(getString(R.string.password_mismatch_message));
            return;
        }

        if (!isValid(password)) {
            showToast(getString(R.string.invalid_password_message));
            return;
        }

        checkIfUsernameExists(username, usersRef);
    }

    private void checkIfUsernameExists(String username, DatabaseReference usersRef) {
        usersRef.orderByValue().equalTo(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    showToast(getString(R.string.username_already_used_message));
                } else {
                    registerUserWithFirebase();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                showToast(getString(R.string.username_check_error_message));
            }
        });
    }

    private void registerUserWithFirebase() {
        String email = input_email.getText().toString().trim();
        String password = input_password.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                handleSuccessfulRegistration(Objects.requireNonNull(mAuth.getCurrentUser()));
            } else {
                handleRegistrationError(task);
            }
        });
    }

    private void handleSuccessfulRegistration(FirebaseUser user) {
        String username = input_username.getText().toString().trim();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserToDatabase(user);
                        showToast(getString(R.string.registration_success_message));
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    }
                });
    }

    private void saveUserToDatabase(FirebaseUser user) {
        String username = input_username.getText().toString().trim();
        String email = input_email.getText().toString().trim();

        User userObj = new User(username, email);
        usersRef.child(user.getUid()).setValue(userObj);
    }

    private void handleRegistrationError(Task<AuthResult> task) {
        Exception exception = task.getException();

        if (exception instanceof FirebaseAuthUserCollisionException) {
            showToast(getString(R.string.email_already_used_message));
        } else if (exception instanceof FirebaseAuthWeakPasswordException) {
            showToast(getString(R.string.weak_password_message));
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            showToast(getString(R.string.invalid_email_message));
        } else {
            showToast(getString(R.string.registration_failure_message));
        }
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}

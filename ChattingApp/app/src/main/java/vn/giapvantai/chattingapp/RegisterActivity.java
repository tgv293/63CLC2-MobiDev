package vn.giapvantai.chattingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    private final String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";
    private TextView loginbut;
    private EditText rg_username, rg_email, rg_password, rg_repassword;
    private Button rg_signup;
    private CircleImageView rg_profileImg;
    private FirebaseAuth auth;
    private Uri imageURI;
    private String imageuri;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();

        initializeViews();

        loginbut.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        rg_signup.setOnClickListener(v -> signUp());

        rg_profileImg.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
        });
    }

    private void initializeViews() {
        progressBar = findViewById(R.id.progressBar);
        loginbut = findViewById(R.id.loginbut);
        rg_username = findViewById(R.id.rgusername);
        rg_email = findViewById(R.id.rgemail);
        rg_password = findViewById(R.id.rgpassword);
        rg_repassword = findViewById(R.id.rgrepassword);
        rg_profileImg = findViewById(R.id.profilerg0);
        rg_signup = findViewById(R.id.signupbutton);
    }

    private void signUp() {
        String namee = rg_username.getText().toString();
        String emaill = rg_email.getText().toString();
        String Password = rg_password.getText().toString();
        String cPassword = rg_repassword.getText().toString();
        String status = "Hey I'm Using This Application";

        if (TextUtils.isEmpty(namee) || TextUtils.isEmpty(emaill) ||
                TextUtils.isEmpty(Password) || TextUtils.isEmpty(cPassword)) {
            showToast("Please Enter Valid Information");
        } else if (!emaill.matches(emailPattern)) {
            rg_email.setError("Type A Valid Email Here");
        } else if (Password.length() < 6) {
            rg_password.setError("Password Must Be 6 Characters Or More");
        } else if (!Password.equals(cPassword)) {
            rg_password.setError("The Password Doesn't Match");
        } else {
            auth.createUserWithEmailAndPassword(emaill, Password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    String id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                    DatabaseReference reference = database.getReference().child("user").child(id);
                    StorageReference storageReference = storage.getReference().child("Upload").child(id);

                    if (imageURI != null) {
                        uploadImageAndSaveData(id, reference, storageReference);
                    } else {
                        saveUserDataWithoutImage(id, reference);
                    }
                } else {
                    showToast(Objects.requireNonNull(task.getException()).getMessage());
                }
            });
        }
    }

    private void uploadImageAndSaveData(String id, DatabaseReference reference, StorageReference storageReference) {
        storageReference.putFile(imageURI).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    imageuri = uri.toString();
                    Users users = new Users(id, rg_username.getText().toString(), rg_email.getText().toString(),
                            rg_password.getText().toString(), imageuri, "Hey I'm Using This Application");
                    saveUserData(id, reference, users);
                });
            }
        });
    }

    private void saveUserDataWithoutImage(String id, DatabaseReference reference) {
        String status = "Hey I'm Using This Application";
        imageuri = "https://firebasestorage.googleapis.com/v0/b/chattingapp-28380.appspot.com/o/man.png?alt=media&token=9e084dbc-8e43-44b8-bf71-3251889323bf";
        Users users = new Users(id, rg_username.getText().toString(), rg_email.getText().toString(),
                rg_password.getText().toString(), imageuri, status);
        saveUserData(id, reference, users);
    }

    private void saveUserData(String id, DatabaseReference reference, Users users) {
        reference.setValue(users).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.VISIBLE);
            if (task.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                showToast("Successful Registration");
                startActivity(intent);
                finish();
            } else {
                progressBar.setVisibility(View.GONE);
                showToast("Error in creating the user");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && data != null) {
            imageURI = data.getData();
            rg_profileImg.setImageURI(imageURI);
        }
    }
}

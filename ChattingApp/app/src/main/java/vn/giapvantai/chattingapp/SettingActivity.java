package vn.giapvantai.chattingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private CircleImageView setProfile;
    private EditText setName, setStatus;
    private Button doneButton;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private Uri setImageUri;
    private String email, password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        setProfile = findViewById(R.id.settingprofile);
        setName = findViewById(R.id.settingname);
        setStatus = findViewById(R.id.settingstatus);
        doneButton = findViewById(R.id.donebutt);

        progressBar = findViewById(R.id.progressBar);

        DatabaseReference reference = database.getReference().child("user").child(Objects.requireNonNull(auth.getUid()));
        StorageReference storageReference = storage.getReference().child("upload").child(auth.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = Objects.requireNonNull(snapshot.child("mail").getValue()).toString();
                password = Objects.requireNonNull(snapshot.child("password").getValue()).toString();
                String name = Objects.requireNonNull(snapshot.child("userName").getValue()).toString();
                String profile = Objects.requireNonNull(snapshot.child("profilepic").getValue()).toString();
                String status = Objects.requireNonNull(snapshot.child("status").getValue()).toString();
                setName.setText(name);
                setStatus.setText(status);
                Picasso.get().load(profile).into(setProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });

        setProfile.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10);
        });

        doneButton.setOnClickListener(view -> {

            String name = setName.getText().toString();
            String status = setStatus.getText().toString();

            if (setImageUri != null) {
                storageReference.putFile(setImageUri).addOnCompleteListener(task -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String finalImageUri = uri.toString();
                    Users users = new Users(auth.getUid(), name, email, password, finalImageUri, status);
                    saveUserData(reference, users);
                }));
            } else {
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    String finalImageUri = uri.toString();
                    Users users = new Users(auth.getUid(), name, email, password, finalImageUri, status);
                    saveUserData(reference, users);
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && data != null) {
            setImageUri = data.getData();
            setProfile.setImageURI(setImageUri);
        }
    }

    private void saveUserData(DatabaseReference reference, Users users) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("userName", users.getUserName());
        updates.put("profilepic", users.getProfilepic());
        updates.put("status", users.getStatus());

        reference.updateChildren(updates).addOnCompleteListener(task -> {
            progressBar.setVisibility(View.VISIBLE);
            if (task.isSuccessful()) {
                progressBar.setVisibility(View.GONE);
                showToast("Data is saved");
                Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                progressBar.setVisibility(View.GONE);
                showToast("Something went wrong");
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(SettingActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

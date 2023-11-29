package vn.giapvantai.appchatting.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import vn.giapvantai.appchatting.databinding.ActivitySetupProfileBinding;

public class SetupProfileActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private FirebaseStorage storage;
    private Uri selectedImage;
    private String name;
    private ActivitySetupProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        binding = ActivitySetupProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();

        binding.imageView.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE);
        });

        binding.setupProfileBtn.setOnClickListener(v -> {
            name = Objects.requireNonNull(binding.nameBox.getText()).toString();
            if (TextUtils.isEmpty(name)) {
                showToast("Name is required");
            } else {
                if (selectedImage != null) {
                    StorageReference reference = storage.getReference().child("Profiles").child(Objects.requireNonNull(auth.getUid()));
                    reference.putFile(selectedImage).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            reference.getDownloadUrl().addOnSuccessListener(this::setupProfile);
                        }
                    });
                } else {
                    setupProfile(null);
                }
            }
        });
    }

    private void setupProfile(Uri uri) {
        String imageUrl = "https://firebasestorage.googleapis.com/v0/b/chatsapp-66fe1.appspot.com/o/avatar.png?alt=media&token=1c3c8a88-405a-47e3-9a1a-8f677b8dba65";

        if (uri != null) {
            imageUrl = uri.toString();
        }

        FirebaseUser firebaseUser = auth.getCurrentUser();
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();

            Map<String, Object> updates = new HashMap<>();
            updates.put("name", name);
            updates.put("profileImage", imageUrl);

            DatabaseReference reference = database.getReference().child("users").child(uid);
            reference.updateChildren(updates).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    startActivity(new Intent(SetupProfileActivity.this, MainActivity.class));
                } else {
                    showToast("Error in updating profile");
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (data.getData() != null) {
                binding.imageView.setImageURI(data.getData());
                selectedImage = data.getData();
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(SetupProfileActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}

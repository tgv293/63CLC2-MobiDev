package vn.giapvantai.chattingapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private RecyclerView mainUserRecyclerView;
    private UserAdapter adapter;
    private FirebaseDatabase database;
    private ArrayList<Users> usersArrayList;
    private ImageView imgLogout, camBut, setBut;
    private EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        camBut = findViewById(R.id.camBut);
        setBut = findViewById(R.id.settingBut);
        searchBar = findViewById(R.id.search_bar);

        usersArrayList = new ArrayList<>();
        mainUserRecyclerView = findViewById(R.id.mainUserRecyclerView);
        mainUserRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserAdapter(MainActivity.this, usersArrayList);
        mainUserRecyclerView.setAdapter(adapter);

        DatabaseReference reference = database.getReference().child("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersArrayList.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);

                    assert users != null;
                    if (!users.getUserId().equals(Objects.requireNonNull(auth.getCurrentUser()).getUid())) {
                        usersArrayList.add(users);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle onCancelled
            }
        });

        imgLogout = findViewById(R.id.logoutimg);

        imgLogout.setOnClickListener(v -> showLogoutDialog());

        setBut.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(intent);
        });

        camBut.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 10);
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing before text changes
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Filter user list based on text entered
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing after text changes
            }
        });

        if (auth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        }
    }

    private void showLogoutDialog() {
        Dialog dialog = new Dialog(this, R.style.dialoge);
        dialog.setContentView(R.layout.dialog_layout);

        Button yesBtn = dialog.findViewById(R.id.yesbnt);
        Button noBtn = dialog.findViewById(R.id.nobnt);

        yesBtn.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finishAffinity();
        });

        noBtn.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    void filter(String text){
        ArrayList<Users> filteredList = new ArrayList<>();
        for(Users user : usersArrayList){
            if(user.getUserName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(user);
            }
        }

        adapter.updateList(filteredList);
    }
}

package com.giapvantai.multicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Contact extends AppCompatActivity {

    RelativeLayout home, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        // Ánh xạ các phần tử giao diện
        home = findViewById(R.id.home);
        contact = findViewById(R.id.contact);

        // Thiết lập sự kiện cho nút "Home" để chuyển đến MainActivity
        home.setOnClickListener(v -> goToMainActivity());

        // Thiết lập sự kiện cho nút "Contact" để hiển thị thông báo "Contact"
        contact.setOnClickListener(v -> displayContactMessage());
    }

    // Phương thức này chuyển đến màn hình MainActivity
    private void goToMainActivity() {
        Intent intent = new Intent(Contact.this, MainActivity.class);
        startActivity(intent);
    }

    // Phương thức này hiển thị thông báo "Contact"
    private void displayContactMessage() {
        Toast.makeText(Contact.this, "Contact", Toast.LENGTH_SHORT).show();
    }
}

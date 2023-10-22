package com.giapvantai.intentexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        TextView tvUserName = (TextView) findViewById(R.id.tvUserName);
        String userName = getIntent().getStringExtra("userName");
        tvUserName.setText(userName);
    }
}
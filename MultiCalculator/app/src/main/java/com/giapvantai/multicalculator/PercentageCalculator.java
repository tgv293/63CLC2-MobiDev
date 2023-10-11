package com.giapvantai.multicalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PercentageCalculator extends AppCompatActivity {

    TextView totalTextView;
    EditText percentageTxt;
    EditText numberTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage_calculator);

        totalTextView = findViewById(R.id.textview);
        percentageTxt = findViewById(R.id.percentageTxt);
        numberTxt = findViewById(R.id.numberTxt);

        Button calcBtn = findViewById(R.id.calcBtn);
        calcBtn.setOnClickListener(view -> calculatePercentage());
    }

    // Phương thức này tính phần trăm và hiển thị kết quả
    @SuppressLint("SetTextI18n")
    private void calculatePercentage() {
        float percentage = Float.parseFloat(percentageTxt.getText().toString());
        float dec = percentage / 100;
        float total = dec * Float.parseFloat(numberTxt.getText().toString());
        totalTextView.setText(Float.toString(total));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

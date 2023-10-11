package com.giapvantai.multicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DiscountCalculator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_calculator);
    }

    @SuppressLint("SetTextI18n")
    public void applyDiscount(View view) {
        EditText priceText = findViewById(R.id.price);
        EditText percentageText = findViewById(R.id.percentage);
        TextView calcResult = findViewById(R.id.calcResult);

        // Kiểm tra nếu trường giá trị rỗng
        if (priceText.getText().toString().equals("")) {
            Toast.makeText(DiscountCalculator.this, "Price field is empty", Toast.LENGTH_SHORT).show();
        } else if (percentageText.getText().toString().equals("")) {
            Toast.makeText(DiscountCalculator.this, "Discount field is empty", Toast.LENGTH_SHORT).show();
        } else if (Double.parseDouble(percentageText.getText().toString()) >= 101) {
            // Kiểm tra nếu giảm giá vượt quá 100%
            Toast.makeText(DiscountCalculator.this, "You cannot discount beyond 101%", Toast.LENGTH_SHORT).show();
        } else {
            // Ẩn bàn phím ảo sau khi tính toán
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            double price = Double.parseDouble(priceText.getText().toString());
            double percentage = Double.parseDouble(percentageText.getText().toString());
            double onePerc = price / 100;
            double PercOff = 100 - percentage;
            double result = onePerc * PercOff;
            double finalResult = Math.round(result * 100.0) / 100.0;

            // Hiển thị kết quả tính toán giảm giá
            calcResult.setText("Discounted price:" + '\n' + finalResult);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}



package com.giapvantai.multicalculator;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BMIResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmiresult);

        // Ánh xạ các phần tử giao diện
        TextView yourBMI = findViewById(R.id.your_bmi);
        TextView age = findViewById(R.id.age);
        TextView category = findViewById(R.id.category);
        TextView condition = findViewById(R.id.condition);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        float BMI = Math.round((intent.getFloatExtra("BMI", 0) * 100) / 100);
        String ageValue = intent.getStringExtra("age");

        // Hiển thị dữ liệu lên giao diện
        yourBMI.setText(String.valueOf(BMI));
        age.setText(ageValue);

        // Lấy danh mục và điều kiện từ các lớp Category và Condition
        Category category1 = new Category();
        Condition condition1 = new Condition();
        category.setText(category1.getCategory(BMI));
        condition.setText(condition1.getCategory(BMI));

        // Thiết lập sự kiện cho nút "Recalculate"
        Button recalculate = findViewById(R.id.recalculate);
        recalculate.setOnClickListener(v -> updateUI());
    }

    // Phương thức này chuyển đến màn hình tính BMI khi nút "Recalculate" được bấm
    private void updateUI() {
        Intent intent = new Intent(BMIResult.this, BMICalculator.class);
        startActivity(intent);
        finish();
    }

    // Xử lý sự kiện khi nút "Back" được bấm
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateUI();
    }
}

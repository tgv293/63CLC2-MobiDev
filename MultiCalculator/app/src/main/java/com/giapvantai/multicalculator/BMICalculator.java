package com.giapvantai.multicalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class BMICalculator extends AppCompatActivity {

    boolean maleSelected = false;
    boolean femaleSelected = false;

    float height, weight;
    TextView height_txt, age;
    int count_weight = 50, count_age = 19;
    RelativeLayout weight_plus, weight_minus, age_plus, age_minus;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalculator);

        // Ánh xạ các thành phần trong layout
        height_txt = findViewById(R.id.height_txt);
        age = findViewById(R.id.age);
        weight_plus = findViewById(R.id.weight_plus);
        weight_minus = findViewById(R.id.weight_minus);
        age_plus = findViewById(R.id.age_plus);
        age_minus = findViewById(R.id.age_minus);

        CardView card_female = findViewById(R.id.cardView_female);
        CardView card_male = findViewById(R.id.cardView_male);

        // Bắt sự kiện khi chọn giới tính nam
        card_male.setOnClickListener(v -> {
            maleSelected = !maleSelected;
            if (maleSelected) {
                femaleSelected = false;
            }
            updateGenderViews(maleSelected, femaleSelected);
        });

        // Bắt sự kiện khi chọn giới tính nữ
        card_female.setOnClickListener(v -> {
            femaleSelected = !femaleSelected;
            if (femaleSelected) {
                maleSelected = false;
            }
            updateGenderViews(maleSelected, femaleSelected);
        });

        // Cài đặt và bắt sự kiện SeekBar cho chiều cao
        CheckSeekbarStatus();

        // Cài đặt và bắt sự kiện cho nút tăng/giảm cân nặng
        CheckWeight();

        // Cài đặt và bắt sự kiện cho nút tăng/giảm tuổi
        CheckAge();

        // Bắt sự kiện khi nhấn nút tính BMI
        Button calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(v -> CalculateBMI());
    }

    // Cập nhật giao diện khi thay đổi giới tính
    private void updateGenderViews(boolean maleSelected, boolean femaleSelected) {
        final TextView female_text = findViewById(R.id.female);
        final TextView male_text = findViewById(R.id.male);

        if (maleSelected) {
            male_text.setTextColor(Color.parseColor("#FFFFFF"));
            male_text.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.male_white, 0, 0);
        } else {
            male_text.setTextColor(Color.parseColor("#8D8E99"));
            male_text.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.male, 0, 0);
        }

        if (femaleSelected) {
            female_text.setTextColor(Color.parseColor("#FFFFFF"));
            female_text.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.female_white, 0, 0);
        } else {
            female_text.setTextColor(Color.parseColor("#8D8E99"));
            female_text.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.female, 0, 0);
        }
    }

    // Cài đặt và bắt sự kiện cho nút tăng/giảm tuổi
    private void CheckAge() {
        age_plus.setOnClickListener(v -> {
            count_age++;
            if (count_age > 120) {
                count_age = 120; // Giới hạn tuổi tối đa
                Toast.makeText(this, "Tuổi tối đa là 120.", Toast.LENGTH_SHORT).show();
            }
            age.setText(String.valueOf(count_age));
        });

        age_minus.setOnClickListener(v -> {
            count_age--;
            if (count_age < 0) {
                count_age = 0; // Giới hạn tuổi tối thiểu
                Toast.makeText(this, "Tuổi tối thiểu là 0.", Toast.LENGTH_SHORT).show();
            }
            age.setText(String.valueOf(count_age));
        });
    }

    // Cài đặt và bắt sự kiện cho nút tăng/giảm cân nặng
    @SuppressLint("SetTextI18n")
    private void CheckWeight() {
        final TextView weight_txt = findViewById(R.id.weight);

        weight_plus.setOnClickListener(v -> {
            count_weight++;
            if (count_weight > 200) {
                count_weight = 200; // Giới hạn cân nặng tối đa
                Toast.makeText(this, "Cân nặng tối đa là 200kg.", Toast.LENGTH_SHORT).show();
            }
            weight_txt.setText(count_weight + "kg");
            updateWeight(); // Cập nhật trọng lượng
        });

        weight_minus.setOnClickListener(v -> {
            count_weight--;
            if (count_weight < 0) {
                count_weight = 0; // Giới hạn cân nặng tối thiểu
                Toast.makeText(this, "Cân nặng tối thiểu là 0kg.", Toast.LENGTH_SHORT).show();
            }
            weight_txt.setText(count_weight + "kg");
            updateWeight(); // Cập nhật trọng lượng
        });

        updateWeight(); // Cập nhật trọng lượng ban đầu
    }

    // Cập nhật trọng lượng sau khi cân nặng thay đổi
    private void updateWeight() {
        final TextView weight_txt = findViewById(R.id.weight);
        String weightString = weight_txt.getText().toString().replaceAll("[^0-9]", ""); // Loại bỏ ký tự không phải số
        weight = Float.parseFloat(weightString); // Chuyển đổi chuỗi thành số
    }


    // Cài đặt và bắt sự kiện SeekBar cho chiều cao
    private void CheckSeekbarStatus() {
        SeekBar Seekbar = findViewById(R.id.Seekbar);
        Seekbar.setMax(250);
        Seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String ht = progress + getResources().getString(R.string.cm);
                height_txt.setText(ht);
                height = (float) (progress) / 100;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    // Tính toán BMI và chuyển sang màn hình kết quả
    private void CalculateBMI() {
        float BMI = weight / (height * height);
        Intent intent = new Intent(BMICalculator.this, BMIResult.class);
        intent.putExtra("BMI", BMI);
        intent.putExtra("age", age.getText().toString());
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

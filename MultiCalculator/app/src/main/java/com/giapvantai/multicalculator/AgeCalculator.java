package com.giapvantai.multicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AgeCalculator extends AppCompatActivity {
    // Khai báo biến cho các thành phần giao diện
    private Button birthButton;
    private Button todayButton;
    private TextView resultTextView;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    // Định dạng ngày tháng năm
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_calculator);

        // Ánh xạ các thành phần giao diện từ XML layout
        birthButton = findViewById(R.id.bt_birth);
        todayButton = findViewById(R.id.bt_today);
        Button calculateButton = findViewById(R.id.btn_calculate);
        resultTextView = findViewById(R.id.tv_result);

        // Lấy ngày tháng năm hiện tại
        Calendar calendar = Calendar.getInstance();

        // Năm
        int year = calendar.get(Calendar.YEAR);

        // Tháng
        int month = calendar.get(Calendar.MONTH);

        // Ngày
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Định dạng ngày tháng năm
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        // Hiển thị ngày hiện tại mặc định trên nút "Ngày hiện tại"
        String currentDate = dateFormat.format(Calendar.getInstance().getTime());
        todayButton.setText(currentDate);

        // Xử lý sự kiện khi người dùng chọn ngày trên nút "Ngày sinh"
        dateSetListener = (view, year1, month1, day1) -> {
            month1 = month1 + 1;
            String date = day1 + "/" + month1 + "/" + year1;
            birthButton.setText(date);
        };

        // Xử lý sự kiện khi người dùng click vào nút "Ngày sinh"
        birthButton.setOnClickListener(view -> showDatePickerDialog(year, month, day));

        // Xử lý sự kiện khi người dùng click vào nút "Ngày hiện tại"
        todayButton.setOnClickListener(view -> showDatePickerDialog(year, month, day));

        // Xử lý sự kiện khi người dùng click vào nút "Tính tuổi"
        calculateButton.setOnClickListener(view -> calculateAge());
    }

    // Hiển thị hộp thoại chọn ngày
    private void showDatePickerDialog(int year, int month, int day) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AgeCalculator.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        datePickerDialog.show();
    }

    // Tính tuổi dựa trên ngày sinh và ngày hiện tại
    @SuppressLint("SetTextI18n")
    private void calculateAge() {
        String birthDateStr = birthButton.getText().toString();
        String currentDateStr = todayButton.getText().toString();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            Date birthDate = dateFormat.parse(birthDateStr);
            Date currentDate = dateFormat.parse(currentDateStr);

            assert birthDate != null;
            if (birthDate.before(currentDate)) {
                // Sử dụng thư viện Joda-Time để tính tuổi
                assert currentDate != null;
                Period period = new Period(birthDate.getTime(), currentDate.getTime(), PeriodType.yearMonthDay());
                int years = period.getYears();
                int months = period.getMonths();
                int days = period.getDays();

                // Hiển thị kết quả tuổi
                resultTextView.setText(years + " Years | " + months + " Months | " + days + " Days");
            } else {
                // Hiển thị thông báo nếu ngày sinh lớn hơn ngày hiện tại
                Toast.makeText(AgeCalculator.this, "BirthDate should not be larger than today's date!", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}

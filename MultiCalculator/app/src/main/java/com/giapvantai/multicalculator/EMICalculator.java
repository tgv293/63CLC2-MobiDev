package com.giapvantai.multicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EMICalculator extends AppCompatActivity {

    double emi = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emi_layout);
    }

    public void calculate(View view) {
        // Ánh xạ các trường nhập liệu
        EditText loanAmount = findViewById(R.id.loan_amount);
        EditText loanInterest = findViewById(R.id.loan_interest);
        EditText loanTenure = findViewById(R.id.loan_tenure);

        // Kiểm tra và xử lý dữ liệu nhập liệu
        if (isEmpty(loanAmount) || isEmpty(loanInterest) || isEmpty(loanTenure)) {
            return;
        }

        double principal = Double.parseDouble(loanAmount.getText().toString());
        double interest = Double.parseDouble(loanInterest.getText().toString());
        int tenure = Integer.parseInt(loanTenure.getText().toString());

        double emiResult = calculateEMI(principal, interest, tenure);

        // Hiển thị kết quả EMI
        displayEMI(emiResult);
    }

    private boolean isEmpty(EditText editText) {
        if (editText.getText().toString().trim().isEmpty()) {
            editText.setError("This field is required!");
            return true;
        }
        return false;
    }

    private double calculateEMI(double principal, double interest, int tenure) {
        double intPerMonth = (interest / 12 / 100);
        double emi = (principal * intPerMonth * Math.pow((1 + intPerMonth), tenure))
                / (Math.pow((1 + intPerMonth), tenure) - 1);
        return Math.round(emi * 100.0) / 100.0;
    }

    @SuppressLint("SetTextI18n")
    private void displayEMI(double emiResult) {
        TextView emiTextView = findViewById(R.id.emi);
        emiTextView.setText("₹ " + emiResult);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

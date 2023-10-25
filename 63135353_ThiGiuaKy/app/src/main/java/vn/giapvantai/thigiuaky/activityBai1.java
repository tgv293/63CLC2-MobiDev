package vn.giapvantai.thigiuaky;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class activityBai1 extends AppCompatActivity {

    // Khai báo các thành phần giao diện
    EditText edtHeight, edtWeight;
    TextView tvResult, tvRStatus;
    Button btnCalculate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai1);

        // Tìm các điều khiển
        edtHeight = findViewById(R.id.edtHeight);
        edtWeight = findViewById(R.id.edtWeight);
        tvResult = findViewById(R.id.tvResult);
        tvRStatus = findViewById(R.id.tvRStatus);
        btnCalculate = findViewById(R.id.btnCalculate);

        // Thêm lắng nghe sự kiện cho nút btnCalculate
        btnCalculate.setOnClickListener(view -> calculateBMI());
    }

    @SuppressLint("DefaultLocale")
    private void calculateBMI() {
        float height = Float.parseFloat(edtHeight.getText().toString()) / 100;
        float weight = Float.parseFloat(edtWeight.getText().toString());
        float bmi = weight / (height * height);

        tvResult.setText(String.format("%.2f", bmi));
        tvRStatus.setText(getBMICategory(bmi));
    }

    private String getBMICategory(float bmi) {
        if (bmi < 18.5) {
            return "Cân nặng thấp (gầy)";
        } else if (bmi < 25) {
            return "Bình thường";
        } else if (bmi == 25) {
            return "Thừa cân";
        } else if (bmi < 30) {
            return "Tiền béo phì";
        } else if (bmi < 35) {
            return "Béo phì độ I";
        } else {
            return "Béo phì độ II";
        }
    }
}
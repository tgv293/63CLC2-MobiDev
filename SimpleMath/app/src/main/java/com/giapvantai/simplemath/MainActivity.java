package com.giapvantai.simplemath;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Khai báo các thành phần giao diện
    EditText dk_soA, dk_soB;
    TextView dk_KQ;
    Button btnAdd, btnSub, btnMul, btnDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tìm các điều khiển
        dk_soA = findViewById(R.id.edtA);
        dk_soB = findViewById(R.id.edtB);
        dk_KQ = findViewById(R.id.tvResult);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);

        // Thêm lắng nghe sự kiện cho các nút
        btnAdd.setOnClickListener(view -> calculate(Operation.ADD));
        btnSub.setOnClickListener(view -> calculate(Operation.SUBTRACT));
        btnMul.setOnClickListener(view -> calculate(Operation.MULTIPLY));
        btnDiv.setOnClickListener(view -> calculate(Operation.DIVIDE));
    }

    private void calculate(Operation operation) {
        int soA = Integer.parseInt(dk_soA.getText().toString());
        int soB = Integer.parseInt(dk_soB.getText().toString());
        float KetQua;

        switch (operation) {
            case ADD:
                KetQua = soA + soB;
                break;
            case SUBTRACT:
                KetQua = soA - soB;
                break;
            case MULTIPLY:
                KetQua = soA * soB;
                break;
            case DIVIDE:
                if (soB != 0) {
                    KetQua = (float) soA / soB;
                } else {
                    dk_KQ.setText("Không thể chia cho 0");
                    return;
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + operation);
        }

        dk_KQ.setText(String.valueOf(KetQua));
    }

    private enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }
}

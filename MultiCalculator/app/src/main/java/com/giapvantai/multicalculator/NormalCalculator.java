package com.giapvantai.multicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class NormalCalculator extends AppCompatActivity {

    private EditText inputtext;  // Trường nhập dữ liệu cho biểu thức
    private TextView displaytext; // TextView để hiển thị kết quả
    private String currentInput = ""; // Biến để lưu trữ biểu thức đầu vào hiện tại
    private String previousResult = ""; // Biến để lưu trữ kết quả trước đó

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_calculator);

        // Tìm và thiết lập sự kiện cho nút xóa
        ImageButton deleteButton = findViewById(R.id.butdelet);
        deleteButton.setOnClickListener(view -> deletenumber());

        // Khởi tạo nút "C" và thiết lập lắng nghe sự kiện khi nút "C" được nhấn
        Button clearButton = findViewById(R.id.butclear);
        clearButton.setOnClickListener(view -> xoaToanBo());

        // Khởi tạo các thành phần giao diện người dùng
        inputtext = findViewById(R.id.input_box);
        displaytext = findViewById(R.id.result_box);
    }

    // Được gọi khi một nút số hoặc toán tử được nhấn
    public void onClickButton(View v) {
        Button button = (Button) v;
        String buttonText = button.getText().toString();

        if (buttonText.equals("=")) {
            calculateResult(); // Gọi hàm tính toán kết quả
        } else {
            currentInput += buttonText; // Thêm biểu thức đầu vào hiện tại
            inputtext.append(buttonText); // Hiển thị biểu thức trên giao diện
        }
    }

    // Xóa ký tự cuối cùng từ trường nhập dữ liệu
    public void deletenumber() {
        Editable inputEditable = inputtext.getText();
        int length = inputEditable.length();

        if (length > 0) {
            currentInput = currentInput.substring(0, currentInput.length() - 1); // Loại bỏ ký tự cuối cùng
            inputEditable.delete(length - 1, length);
        }
    }

    // Phương thức xóa toàn bộ nội dung nhập
    public void xoaToanBo() {
        inputtext.setText("");
        displaytext.setText("");
        currentInput = ""; // Đặt lại biểu thức đầu vào hiện tại
    }

    // Tính toán kết quả của biểu thức
    @SuppressLint("SetTextI18n")
    public void calculateResult() {
        if (!currentInput.isEmpty()) {
            try {
                // Thay thế 'x' bằng '*' và '÷' bằng '/' để phân tích cú pháp đúng
                String input = currentInput.replace("x", "*").replace("÷", "/");

                // Sử dụng ExpressionBuilder để tính toán biểu thức
                Expression expression = new ExpressionBuilder(input).build();
                double result = expression.evaluate();

                // Lưu kết quả vào biến previousResult để sử dụng cho tính toán tiếp theo
                previousResult = String.valueOf(result);
                displaytext.setText(previousResult);

                // Cập nhật trường nhập với kết quả trước đó để tiếp tục tính toán
                currentInput = previousResult;
                inputtext.setText(previousResult);
            } catch (ArithmeticException e) {
                displaytext.setText("Không thể chia cho 0");
            } catch (Exception ex) {
                displaytext.setText("Biểu thức không hợp lệ");
            }
        } else if (!previousResult.isEmpty()) {
            // Nếu không có biểu thức mới, hiển thị kết quả trước đó
            inputtext.setText(previousResult);
        } else {
            displaytext.setText("");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

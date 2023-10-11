package com.giapvantai.multicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ScientificCalculator extends AppCompatActivity {

    private EditText e1, e2;
    private String expression = "";
    private DBHelper dbHelper;
    private Button mode, toggle, square, xpowy, log, sin, cos, tan, sqrt, fact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific_calculator);

        e1 = findViewById(R.id.editText);
        e2 = findViewById(R.id.editText2);
        mode = findViewById(R.id.mode);
        toggle = findViewById(R.id.toggle);
        square = findViewById(R.id.square);
        xpowy = findViewById(R.id.xpowy);
        log = findViewById(R.id.log);
        sin = findViewById(R.id.sin);
        cos = findViewById(R.id.cos);
        tan = findViewById(R.id.tan);
        sqrt = findViewById(R.id.sqrt);
        fact = findViewById(R.id.factorial);

        dbHelper = new DBHelper(this);

        e2.setText("0");

        mode.setTag(1);
        toggle.setTag(1);
    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void onClick(View v) {
        int toggleMode = (int) toggle.getTag();
        int angleMode = ((int) mode.getTag());
        switch (v.getId()) {
            case R.id.toggle:
                if (toggleMode == 1) {
                    toggle.setTag(2);
                    square.setText(R.string.cube);
                    xpowy.setText(R.string.tenpow);
                    log.setText(R.string.naturalLog);
                    sin.setText(R.string.sininv);
                    cos.setText(R.string.cosinv);
                    tan.setText(R.string.taninv);
                    sqrt.setText(R.string.cuberoot);
                    fact.setText(R.string.Mod);
                } else if (toggleMode == 2) {
                    toggle.setTag(3);
                    square.setText(R.string.square);
                    xpowy.setText(R.string.epown);
                    log.setText(R.string.log);
                    sin.setText(R.string.hyperbolicSine);
                    cos.setText(R.string.hyperbolicCosine);
                    tan.setText(R.string.hyperbolicTan);
                    sqrt.setText(R.string.inverse);
                    fact.setText(R.string.factorial);
                } else if (toggleMode == 3) {
                    toggle.setTag(1);
                    sin.setText(R.string.sin);
                    cos.setText(R.string.cos);
                    tan.setText(R.string.tan);
                    sqrt.setText(R.string.sqrt);
                    xpowy.setText(R.string.xpown);
                }
                break;

            case R.id.mode:
                if (angleMode == 1) {
                    mode.setTag(2);
                    mode.setText(R.string.mode2);
                } else {
                    mode.setTag(1);
                    mode.setText(R.string.mode1);
                }
                break;

            case R.id.num0:
                e2.setText(e2.getText() + "0");
                break;

            // Xóa các trường hợp khác để giảm bớt sự phức tạp
            // ...

            case R.id.equal:
                if (e2.length() != 0) {
                    String text = e2.getText().toString();
                    expression = e1.getText().toString() + text;
                }
                e1.setText("");
                if (expression.length() == 0)
                    expression = "0.0";
                try {
                    Double result = new ExtendedDoubleEvaluator().evaluate(expression);
                    if (String.valueOf(result).equals("6.123233995736766E-17")) {
                        result = 0.0;
                        e2.setText(result + "");
                    } else if (String.valueOf(result).equals("1.633123935319537E16"))
                        e2.setText("infinity");
                    else
                        e2.setText(result + "");
                    if (!expression.equals("0.0"))
                        dbHelper.insert("SCIENTIFIC", expression + " = " + result);
                } catch (Exception e) {
                    e2.setText("Invalid Expression");
                    e1.setText("");
                    expression = "";
                    e.printStackTrace();
                }
                break;

            // Xóa các trường hợp khác để giảm bớt sự phức tạp
            // ...
        }
    }

    @SuppressLint("SetTextI18n")
    private void operationClicked(String op) {
        if (e2.length() != 0) {
            String text = e2.getText().toString();
            e1.setText(e1.getText() + text + op);
            e2.setText("");
            int count = 0;
        } else {
            String text = e1.getText().toString();
            if (text.length() > 0) {
                String newText = text.substring(0, text.length() - 1) + op;
                e1.setText(newText);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

package com.giapvantai.ex_no_1;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView textView1;
    private ObjectAnimator colorAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView1 = findViewById(R.id.textView1);

        int[] colors = {
                0xFF000000, // Black
                0xFFFFFFFF, // White
                0xFFFF0000, // Red
                0xFF00FF00, // Lime
                0xFF0000FF, // Blue
                0xFFFFFF00, // Yellow
                0xFF00FFFF, // Cyan / Aqua
                0xFFFF00FF, // Magenta / Fuchsia
                0xFFC0C0C0, // Silver
                0xFF808080, // Gray
                0xFF800000, // Maroon
                0xFF808000, // Olive
                0xFF008000, // Green
                0xFF800080, // Purple
                0xFF008080, // Teal
                0xFF000080  // Navy
        };

        colorAnim = ObjectAnimator.ofInt(textView1, "textColor", colors);
        colorAnim.setDuration(10000); // duration in milliseconds, change as you want
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.setRepeatCount(ObjectAnimator.INFINITE);
        colorAnim.setRepeatMode(ObjectAnimator.RESTART);

        findViewById(R.id.btnChangeSize).setOnClickListener(v -> {
            final EditText input = new EditText(MainActivity.this);
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Change Font Size")
                    .setMessage("Enter the new font size:")
                    .setView(input)
                    .setPositiveButton("OK", (dialog, whichButton) -> {
                        int newSize = Integer.parseInt(input.getText().toString());
                        textView1.setTextSize(newSize);
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        });

        findViewById(R.id.btnChangeColor).setOnClickListener(v -> {
            if (colorAnim.isRunning()) {
                colorAnim.end();
            } else {
                colorAnim.start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (colorAnim.isRunning()) {
            colorAnim.end();
        }
    }
}

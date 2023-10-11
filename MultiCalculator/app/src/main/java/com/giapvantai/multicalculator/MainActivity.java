package com.giapvantai.multicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các thành phần giao diện
        ImageView bottomNav = findViewById(R.id.bottom_nav);
        RelativeLayout home = findViewById(R.id.home);
        RelativeLayout contact = findViewById(R.id.contact);

        // Thiết lập trình nghe sự kiện khi click vào bottom navigation
        bottomNav.setOnClickListener(v -> showBottomSheetDialog());

        // Khởi tạo và ánh xạ các CardView của máy tính
        CardView[] calculators = new CardView[]{
                findViewById(R.id.cal1),
                findViewById(R.id.cal2),
                findViewById(R.id.cal3),
                findViewById(R.id.cal4),
                findViewById(R.id.cal5),
                findViewById(R.id.cal6),
                findViewById(R.id.cal7)
        };

        // Thiết lập trình nghe sự kiện cho các CardView
        for (CardView calculator : calculators) {
            calculator.setOnClickListener(this);
        }

        // Thiết lập sự kiện khi click vào mục "Home" trên bottom navigation
        home.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_SHORT).show());

        // Thiết lập sự kiện khi click vào mục "Contact" trên bottom navigation
        contact.setOnClickListener(v -> {
            Intent g = new Intent(MainActivity.this, Contact.class);
            startActivity(g);
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        // Xử lý sự kiện click vào các CardView
        Intent intent = null;
        switch (v.getId()) {
            case R.id.cal1:
                intent = new Intent(MainActivity.this, NormalCalculator.class);
                break;
            case R.id.cal2:
                intent = new Intent(MainActivity.this, BMICalculator.class);
                break;
            case R.id.cal3:
                intent = new Intent(MainActivity.this, AgeCalculator.class);
                break;
            case R.id.cal4:
                intent = new Intent(MainActivity.this, DiscountCalculator.class);
                break;
            case R.id.cal5:
                intent = new Intent(MainActivity.this, ScientificCalculator.class);
                break;
            case R.id.cal6:
                intent = new Intent(MainActivity.this, EMICalculator.class);
                break;
            case R.id.cal7:
                intent = new Intent(MainActivity.this, PercentageCalculator.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    private void showBottomSheetDialog() {
        // Hiển thị hộp thoại bottom sheet
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        // Ánh xạ các mục trong bottom sheet
        LinearLayout share = bottomSheetDialog.findViewById(R.id.share);
        LinearLayout rating = bottomSheetDialog.findViewById(R.id.rating);
        LinearLayout contact = bottomSheetDialog.findViewById(R.id.contact);

        // Xử lý sự kiện khi click vào mục "Share"
        assert share != null;
        share.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Share is Clicked", Toast.LENGTH_SHORT).show());

        // Xử lý sự kiện khi click vào mục "Rating"
        assert rating != null;
        rating.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Rating is Clicked", Toast.LENGTH_SHORT).show());

        // Xử lý sự kiện khi click vào mục "Update"
        assert contact != null;
        contact.setOnClickListener(v -> Toast.makeText(MainActivity.this, "Update is Clicked", Toast.LENGTH_SHORT).show());

        // Hiển thị hộp thoại bottom sheet
        bottomSheetDialog.show();
    }
}

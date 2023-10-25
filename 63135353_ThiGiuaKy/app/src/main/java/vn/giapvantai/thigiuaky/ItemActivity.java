package vn.giapvantai.thigiuaky;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        TextView tenBaiTextView = findViewById(R.id.tenBaiTextView);
        TextView tacGiaTextView = findViewById(R.id.tacGiaTextView);

        Intent intent = getIntent();
        String tenBai = intent.getStringExtra("tenBai");
        String tacGia = intent.getStringExtra("tacGia");

        tenBaiTextView.setText("Tên bài: " + tenBai);
        tacGiaTextView.setText("Tác giả: " + tacGia);
    }


}
package com.giapvantai.vdonclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Viết hàm xử lý sự kiện ở đây
    public void XuLyNoiHello(View v) {
        //Bước 1: Tìm điều khiển
        EditText edtName = (EditText) findViewById(R.id.edtName);
        //Bước 2: Xuất theo yêu cầu
        String strThongBao = "Xin chào, tôi là " + edtName.getText().toString();
        Toast.makeText(this, strThongBao, Toast.LENGTH_LONG).show();
    }

    public void XuLyChaoBan(View v) {
        String strThongBao = "Chào Bạn Giáp Văn Tài";
        Toast.makeText(this, strThongBao, Toast.LENGTH_LONG).show();
    }

}
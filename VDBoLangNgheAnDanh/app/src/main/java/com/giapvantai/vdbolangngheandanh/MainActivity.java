package com.giapvantai.vdbolangngheandanh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edtName;
    Button buttonSayHi, buttonXinChao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findControls();
        //cài đặt bộ lắng nghe + xử lý sự kiện cho mỗi nút
        buttonSayHi.setOnClickListener(boLangNgheSayHi);
        buttonXinChao.setOnClickListener(boLangNgheXinChao);
    }

    View.OnClickListener boLangNgheSayHi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strThongBao = "Nice to see you \n" + edtName.getText().toString();
            Toast.makeText(getBaseContext(), strThongBao, Toast.LENGTH_LONG).show();
        }
    };

    View.OnClickListener boLangNgheXinChao = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strThongBao = "Chào Bạn \n" + edtName.getText().toString();
            Toast.makeText(getBaseContext(), strThongBao, Toast.LENGTH_LONG).show();
        }
    };

    void findControls() {
        edtName = findViewById(R.id.edtName);
        buttonSayHi = findViewById(R.id.btnHello);
        buttonXinChao = findViewById(R.id.btnXinChao);
    }

}
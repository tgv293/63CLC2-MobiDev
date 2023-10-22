package com.giapvantai.intentexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // Phương thức này được gọi khi nút btnOK được nhấn
    public void chuyenSangHome(View v){
        EditText edtUserName = (EditText) findViewById(R.id.edtUserName);
        EditText edtPassword = (EditText) findViewById(R.id.edtPassword);
        EditText edtEmail = (EditText) findViewById(R.id.edtEmail);

        String userName = edtUserName.getText().toString();
        String password = edtPassword.getText().toString();
        String email = edtEmail.getText().toString();

        if (kiemTra(userName, password, email)) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("userName", userName);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Thông tin đăng nhập không chính xác", Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức kiểm tra thông tin đăng nhập
    private boolean kiemTra(String userName, String password, String email) {
        return "giapvantai".equals(userName) && "123456".equals(password) && "tai.gv.63cntt@ntu.edu.vn".equals(email);
    }
}
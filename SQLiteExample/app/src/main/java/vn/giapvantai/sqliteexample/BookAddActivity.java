package vn.giapvantai.sqliteexample;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookAddActivity extends AppCompatActivity {

    private EditText edMaSach, edTenSach, edSoTrang, edGia, edMoTa;
    private Button buttonAdd, buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add);

        edMaSach = findViewById(R.id.edMaSach);
        edTenSach = findViewById(R.id.edTenSach);
        edSoTrang = findViewById(R.id.edSoTrang);
        edGia = findViewById(R.id.edGia);
        edMoTa = findViewById(R.id.edMoTa);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonCancel = findViewById(R.id.buttonCancel);

        buttonAdd.setOnClickListener(view -> {
            String tenSach = edTenSach.getText().toString();
            String soTrangStr = edSoTrang.getText().toString();
            String giaStr = edGia.getText().toString();
            String moTa = edMoTa.getText().toString();

            if (!tenSach.isEmpty() && !soTrangStr.isEmpty() && !giaStr.isEmpty()) {
                try {
                    int soTrang = Integer.parseInt(soTrangStr);
                    float gia = Float.parseFloat(giaStr);
                    addBookToDatabase(tenSach, soTrang, gia, moTa);
                    updateBookCode();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonCancel.setOnClickListener(view -> {
            startActivity(new Intent(BookAddActivity.this, MainActivity.class));
            finish();
        });
    }

    private void addBookToDatabase(String tenSach, int soTrang, float gia, String moTa) {
        try (SQLiteDatabase db = openOrCreateDatabase("QuanLySach.db", MODE_PRIVATE, null)) {
            String sqlInsert = "INSERT INTO BOOKS(BookName, Page, Price, Description) " +
                    "VALUES('" + tenSach + "', " + soTrang + ", " + gia + ", '" + moTa + "');";
            db.execSQL(sqlInsert);
            Toast.makeText(this,"Thêm thành công",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(BookAddActivity.this, MainActivity.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateBookCode() {
        String currentBookCodeStr = edMaSach.getText().toString();
        if (!currentBookCodeStr.isEmpty()) {
            try {
                int currentBookCode = Integer.parseInt(currentBookCodeStr);
                currentBookCode++;
                edMaSach.setText(String.valueOf(currentBookCode));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}

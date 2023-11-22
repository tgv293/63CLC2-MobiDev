package vn.giapvantai.sqliteexample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class BookNavigationActivity extends AppCompatActivity {

    SQLiteDatabase dbSACH;
    Cursor controBanGhi;
    View.OnClickListener xuLyNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!controBanGhi.isLast()) {
                controBanGhi.moveToNext();
                capNhatManHinhTheoConTro();  // Cập nhật thông tin trên màn hình
            }
        }
    };
    View.OnClickListener xuLyFirst = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            controBanGhi.moveToFirst();
            capNhatManHinhTheoConTro();  // Cập nhật thông tin trên màn hình
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_navigation);
        // Mở CSDL
        dbSACH = openOrCreateDatabase("QuanLySach.db", MODE_PRIVATE, null);
        // Đọc toàn bộ bảng BOOKS
        controBanGhi = dbSACH.rawQuery("Select * from BOOKS", null);
        // Chuyển con trỏ về bản ghi đầu tiên
        controBanGhi.moveToFirst();
        // Hiển bản ghi đầu tiên này lên màn hình
        // Lấy dữ liệu ở dòng hiện tại đang được trả bởi controBanGhi
        capNhatManHinhTheoConTro();

        Button btnNext = findViewById(R.id.buttonNext);
        Button btnFirst = findViewById(R.id.buttonFirst);

        btnFirst.setOnClickListener(xuLyFirst);
        btnNext.setOnClickListener(xuLyNext);
    }

    private void capNhatManHinhTheoConTro() {
        int maSach = controBanGhi.getInt(0);
        String tenSach = controBanGhi.getString(1);
        int soTrang = controBanGhi.getInt(2);
        float gia = controBanGhi.getFloat(3);
        String mota = controBanGhi.getString(4);

        // đặt vào các điều khiển
        EditText edMaSach = findViewById(R.id.edMaSach);
        EditText edTenSach = findViewById(R.id.edTenSach);
        EditText edSoTrang = findViewById(R.id.edSoTrang);
        EditText edGia = findViewById(R.id.edGia);
        EditText edMoTa = findViewById(R.id.edMoTa);

        edMaSach.setText(String.valueOf(maSach));
        edTenSach.setText(tenSach);
        edSoTrang.setText(String.valueOf(soTrang));
        edGia.setText(String.valueOf(gia));
        edMoTa.setText(mota);
    }
}

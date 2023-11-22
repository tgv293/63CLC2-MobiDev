package vn.giapvantai.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase dbSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        DatabaseDemo();


    }

    public void sangManHinhDuyet(View v) {
        Intent intent = new Intent(getBaseContext(), BookNavigationActivity.class);
        startActivity(intent);
    }

    public void sangManHinhThem(View v) {
        Intent intent = new Intent(getBaseContext(), BookAddActivity.class);
        startActivity(intent);
    }

    private void DatabaseDemo() {

        // Tạo cơ sở dữ liệu
        dbSach = openOrCreateDatabase("QuanLySach.db", MODE_PRIVATE, null);

        // Tạo bảng BOOKS
        //* chú ý xóa bảng nếu tồn tại
        dbSach.execSQL("DROP TABLE IF EXISTS BOOKS");
        String sqlTaoBangBooks = "CREATE TABLE BOOKS(BookID integer PRIMARY KEY, " +
                "BookName text, " +
                "Page integer, " +
                "Price Float, " +
                "Description text);";
        dbSach.execSQL(sqlTaoBangBooks);
        // Chèn 1 bản ghi
        String sqlThem1 = "INSERT INTO BOOKS VALUES(1, " +
                "'Lập trình Java'," + " 100, " + "9.99, " + "'Lập trình Java của NXB ABC');";
        dbSach.execSQL(sqlThem1);
        String sqlThem2 = "INSERT INTO BOOKS VALUES(2, " +
                "'Lập trình Java'," + " 108, " + "12.99, " + "'Giáo trình Android toàn tập');";
        dbSach.execSQL(sqlThem2);

        String sqlThem3 = "INSERT INTO BOOKS VALUES(3, " +
                "'Kiến trúc và thiết kế phần mềm'," + " 100, " + "9.99, " + "'Tài liệu học Mẫu thiết kế phần mềm');";
        dbSach.execSQL(sqlThem3);
        dbSach.close();
    }
}
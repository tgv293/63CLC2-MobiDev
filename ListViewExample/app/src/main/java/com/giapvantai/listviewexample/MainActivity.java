package com.giapvantai.listviewexample;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> dsBaiHat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //b0
        findControls();
        //b1. Chuẩn bị nguồn dữ liệu
        dsBaiHat = new ArrayList<>();
        dsBaiHat.add("Tiến quân ca");
        dsBaiHat.add("Dòng máu lạc hồng");
        dsBaiHat.add("Nam quốc sơn hà");
        dsBaiHat.add("Kiếp đỏ đen");
        dsBaiHat.add("Người Việt Nam");
        //b2. Tạo adapter
        ArrayAdapter<String> adapterBH;
        adapterBH = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dsBaiHat);
        //b3. gắn adapter
        listViewBaiHat.setAdapter(adapterBH);
        //b4. bắt sự kiện
        listViewBaiHat.setOnItemClickListener((parent, view, position, id) -> {
            //String itemChon = adapterBH.getItem(position);
            //hoặc ta có thể lấy từ nguồn dữ liệu
            String itemChon = dsBaiHat.get(position);
            //thông báo ra màn hình
            String thongBao = "Bạn chọn bài: " + itemChon;
            Toast.makeText(MainActivity.this,thongBao,Toast.LENGTH_SHORT).show();
        });
    }

    public void findControls() {
        listViewBaiHat = findViewById(R.id.lvBaiHat);
    }

    ListView listViewBaiHat;
}
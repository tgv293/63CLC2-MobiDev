package vn.giapvantai.thigiuaky;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class activityBai2 extends AppCompatActivity {

    ArrayList<BaiHat> dsBaiHat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai2);
        //b0
        findControls();
        //b1. Chuẩn bị nguồn dữ liệu
        dsBaiHat = new ArrayList<>();
        dsBaiHat.add(new BaiHat("Tiến quân ca", "Văn Cao"));
        dsBaiHat.add(new BaiHat("Dòng máu lạc hồng", "Hồng Dân"));
        dsBaiHat.add(new BaiHat("Mưa trên cuộc tình", "Đàm Vĩnh Hưng"));
        dsBaiHat.add(new BaiHat("Nơi này có anh", "Sơn Tùng M-TP"));
        dsBaiHat.add(new BaiHat("Chạy ngay đi", "Sơn Tùng M-TP"));
        dsBaiHat.add(new BaiHat("Lạc trôi", "Sơn Tùng M-TP"));
        dsBaiHat.add(new BaiHat("Em của ngày hôm qua", "Sơn Tùng M-TP"));
        dsBaiHat.add(new BaiHat("Cơn mưa ngang qua", "Sơn Tùng M-TP"));
        dsBaiHat.add(new BaiHat("Anh cứ đi đi", "Hari Won"));
        dsBaiHat.add(new BaiHat("Anh không đòi quà", "Vũ Duy Khánh"));
        //b2. Tạo adapter
        BaiHatAdapter adapterBH = new BaiHatAdapter(this, dsBaiHat);
        listViewBaiHat.setAdapter(adapterBH);


        // Bắt sự kiện khi người dùng chọn một bài hát
        listViewBaiHat.setOnItemClickListener((parent, view, position, id) -> {
            BaiHat baiHat = dsBaiHat.get(position);

            // Truyền thông tin chi tiết của bài hát sang ItemActivity
            Intent intent = new Intent(activityBai2.this, ItemActivity.class);
            intent.putExtra("tenBai", baiHat.getTenBai());
            intent.putExtra("tacGia", baiHat.getTacGia());
            startActivity(intent);
        });

    }

    public void findControls() {
        listViewBaiHat = findViewById(R.id.lvBaiHat);
    }

    ListView listViewBaiHat;

}
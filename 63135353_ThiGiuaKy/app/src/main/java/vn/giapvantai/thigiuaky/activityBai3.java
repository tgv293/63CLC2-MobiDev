package vn.giapvantai.thigiuaky;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class activityBai3 extends AppCompatActivity {

    ArrayList<Country> dsQuocGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bai3);
        //Chuan bi du lieu
        dsQuocGia = new ArrayList<>();
        Country nation1 = new Country("Nga", "ru", 144500000); // Population của Nga
        Country nation2 = new Country("Việt Nam", "vn", 97339000); // Population của Việt Nam
        Country nation3 = new Country("Hoa Kỳ", "us", 331449281); // Population của Hoa Kỳ
        dsQuocGia.add(nation1);
        dsQuocGia.add(nation2);
        dsQuocGia.add(nation3);
        //Tim dieu khien
        ListView listViewQG = findViewById(R.id.listViewNation);
        //Tao adapter
        CountryAdapter adapter = new CountryAdapter(dsQuocGia, this);
        //Gan adapter
        listViewQG.setAdapter(adapter);
        //Xu ly su kien: nhu thong thuong
    }
}

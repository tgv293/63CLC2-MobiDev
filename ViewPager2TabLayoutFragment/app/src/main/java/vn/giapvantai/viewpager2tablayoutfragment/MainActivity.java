package vn.giapvantai.viewpager2tablayoutfragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<QuocGia> dsQuocGia;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    QuocGiaPagerAdapter quocGiaPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dsQuocGia = new ArrayList<>();
        QuocGia qg1 = new QuocGia("Vietnam", "vn", 80);
        QuocGia qg2 = new QuocGia("United State", "us", 68);
        QuocGia qg3 = new QuocGia("Russia", "ru", 120);
        dsQuocGia.add(qg1);
        dsQuocGia.add(qg2);
        dsQuocGia.add(qg3);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.myrecycleView);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(llm);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);

        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));

        recyclerView.addItemDecoration(itemDecorator);
        QuocGiaRVAdapter quocGiaRVAdapter = new QuocGiaRVAdapter(dsQuocGia);
        recyclerView.setAdapter(quocGiaRVAdapter);
        quocGiaRVAdapter.notifyDataSetChanged();

        viewPager2 = findViewById(R.id.viewPagerQG);
        quocGiaPagerAdapter = new QuocGiaPagerAdapter(this, dsQuocGia);

        viewPager2.setAdapter(quocGiaPagerAdapter);

        tabLayout = findViewById(R.id.tabQuocGia);
        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> tab.setText("QG " + (position + 1))
        ).attach();
    }
}
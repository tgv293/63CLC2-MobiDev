package com.giapvantai.autocompletetextview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> dsQuocGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //b0
        findControls();
        //b1. Chuẩn bị nguồn dữ liệu
        dsQuocGia = new ArrayList<>();
        dsQuocGia.add("Vietnam");
        dsQuocGia.add("England");
        dsQuocGia.add("Spain");
        dsQuocGia.add("China");
        dsQuocGia.add("Chile");
        //b2. Tạo adapter
        ArrayAdapter<String> adapterQG;
        adapterQG = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, dsQuocGia);
        //b3. gắn adapter
        autoQuocGia.setAdapter(adapterQG);
    }

    public void findControls() {
        autoQuocGia = findViewById(R.id.autoCompleteQuocGia);
    }

    AutoCompleteTextView autoQuocGia;
}
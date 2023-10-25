package vn.giapvantai.thigiuaky;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomItemActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_item);

        // Get data from Intent
        Intent intent = getIntent();
        String countryName = intent.getStringExtra("countryName");
        String countryFlag = intent.getStringExtra("countryFlag");
        int population = intent.getIntExtra("population", 0);

        // Find TextViews and ImageView in the layout
        TextView textViewCountryNameDetail = findViewById(R.id.textViewCountryNameDetail);
        TextView textViewPopulationDetail = findViewById(R.id.textViewPopulationDetail);
        ImageView imageViewFlagDetail = findViewById(R.id.imageViewFlagDetail);

        // Set data to the views
        textViewCountryNameDetail.setText(countryName);
        textViewPopulationDetail.setText("Population: " + population);

        int resImageID = getResources().getIdentifier(countryFlag, "mipmap", getPackageName());
        imageViewFlagDetail.setImageResource(resImageID);
    }
}

package vn.giapvantai.healthcare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LabTestDetailsActivity extends AppCompatActivity {

    private TextView tvPackageName, tvTotalCost;
    private EditText edDetails;
    private Button btnAddToCart, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_test_details);

        initializeViews();
        populateViewsFromIntent();
        setupButtonListeners();
    }

    private void initializeViews() {
        tvPackageName = findViewById(R.id.tvLDPackageName);
        tvTotalCost = findViewById(R.id.tvLDTotalCost);
        edDetails = findViewById(R.id.editTextLDMultiLine);
        btnAddToCart = findViewById(R.id.btn_LDATCart);
        btnBack = findViewById(R.id.btn_LDBack);

        edDetails.setKeyListener(null);
    }

    private void populateViewsFromIntent() {
        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("name"));
        edDetails.setText(intent.getStringExtra("details"));
        tvTotalCost.setText(intent.getStringExtra("price"));
    }

    private void setupButtonListeners() {
        btnBack.setOnClickListener(v -> startActivity(new Intent(LabTestDetailsActivity.this, LabTestActivity.class)));

        btnAddToCart.setOnClickListener(v -> {
            // Handle add to cart action
        });
    }
}

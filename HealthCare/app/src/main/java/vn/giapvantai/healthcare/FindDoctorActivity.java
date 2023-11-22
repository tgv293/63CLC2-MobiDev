package vn.giapvantai.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;

public class FindDoctorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_doctor);

        setOnClickListenerForCardView(R.id.cardFDBack, HomeActivity.class);
        setOnClickListenerForCardViewWithExtra(R.id.cardFDFamilyPhysician, DoctorDetailsActivity.class, "Family Physicians");
        setOnClickListenerForCardViewWithExtra(R.id.cardFDDietician, DoctorDetailsActivity.class, "Dietician");
        setOnClickListenerForCardViewWithExtra(R.id.cardFDDentist, DoctorDetailsActivity.class, "Dentist");
        setOnClickListenerForCardViewWithExtra(R.id.cardFDSurgeon, DoctorDetailsActivity.class, "Surgeon");
        setOnClickListenerForCardViewWithExtra(R.id.cardFDCardiologists, DoctorDetailsActivity.class, "Cardiologists");
    }

    private void setOnClickListenerForCardView(int cardViewId, Class<?> activityClass) {
        CardView cardView = findViewById(cardViewId);
        cardView.setOnClickListener(v -> startActivity(new Intent(FindDoctorActivity.this, activityClass)));
    }

    private void setOnClickListenerForCardViewWithExtra(int cardViewId, Class<?> activityClass, String title) {
        CardView cardView = findViewById(cardViewId);
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(FindDoctorActivity.this, activityClass);
            intent.putExtra("title", title);
            startActivity(intent);
        });
    }
}

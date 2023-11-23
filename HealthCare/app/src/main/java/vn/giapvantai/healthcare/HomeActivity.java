package vn.giapvantai.healthcare;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        setOnClickListenerForCardView(R.id.cardExit, LoginActivity.class);
        setOnClickListenerForCardView(R.id.cardFindDoctor, FindDoctorActivity.class);
        setOnClickListenerForCardView(R.id.cardLabTest, LabTestActivity.class);
    }

    private void setOnClickListenerForCardView(int cardViewId, Class<?> activityClass) {
        CardView cardView = findViewById(cardViewId);
        cardView.setOnClickListener(v -> {
            if (activityClass.equals(LoginActivity.class)) {
                mAuth.signOut();
            }
            startActivity(new Intent(HomeActivity.this, activityClass));
        });
    }
}

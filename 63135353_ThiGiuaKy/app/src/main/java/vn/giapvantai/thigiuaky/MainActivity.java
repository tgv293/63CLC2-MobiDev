package vn.giapvantai.thigiuaky;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    public void goToBai1Activity(View view) {
        goToActivity(activityBai1.class);
    }

    public void goToBai2Activity(View view) {
        goToActivity(activityBai2.class);
    }

    public void goToBai3Activity(View view) {
        goToActivity(activityBai3.class);
    }

    public void goToBai4Activity(View view) {
        goToActivity(activityBai4.class);
    }
}

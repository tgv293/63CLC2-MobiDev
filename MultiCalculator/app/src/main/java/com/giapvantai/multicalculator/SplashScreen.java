package com.giapvantai.multicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    ProgressBar pb;
    int counter = 0;

    // Thời gian hiển thị màn hình Splash (milliseconds), giảm xuống 3 giây
    private static final int SPLASH_SCREEN_TIME_OUT = 3000;

    // Số lần cập nhật ProgressBar (tính theo milliseconds)
    private static final int PROGRESS_BAR_UPDATE_INTERVAL = 30;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Loại bỏ thanh trạng thái và thanh điều hướng để màn hình Splash có thể che phủ toàn bộ màn hình.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        // Tăng giá trị tối đa của ProgressBar để phản ánh thời gian hiển thị Splash (2 giây).
        pb = findViewById(R.id.pb);
        pb.setMax(SPLASH_SCREEN_TIME_OUT);

        new Handler().postDelayed(() -> {
            // Tạo Intent để chuyển từ màn hình Splash sang MainActivity.
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            // Khởi chạy MainActivity.
            startActivity(i);
            // Kết thúc màn hình Splash.
            finish();
        }, SPLASH_SCREEN_TIME_OUT);

        // Thiết lập thanh tiến trình (ProgressBar).
        setupProgressBar();
    }

    // Thiết lập ProgressBar.
    public void setupProgressBar() {
        pb.setProgress(0);

        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter += PROGRESS_BAR_UPDATE_INTERVAL;
                pb.setProgress(counter);

                if (counter >= SPLASH_SCREEN_TIME_OUT) {
                    t.cancel();
                }
            }
        };

        // Lên lịch cập nhật ProgressBar mỗi 50ms.
        t.schedule(tt, 0, PROGRESS_BAR_UPDATE_INTERVAL);
    }

}

package vn.giapvantai.musicplayer;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

public class App extends Application {

    // Context toàn cục của ứng dụng
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    /**
     * Phương thức trả về Context toàn cục của ứng dụng.
     *
     * @return Context toàn cục
     */
    public static Context getContext() {
        return App.context;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Khởi tạo Context toàn cục bằng cách sử dụng getApplicationContext()
        App.context = getApplicationContext();
    }
}


package vn.giapvantai.musicplayer.database.converter;

import androidx.room.TypeConverter;

import vn.giapvantai.musicplayer.model.Music;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;

public class CustomConverter {
    // Sử dụng Gson để chuyển đổi giữa đối tượng Music và chuỗi JSON
    public static final Gson gson = new Gson();

    // Converter cho việc chuyển đổi danh sách Music thành chuỗi JSON
    public static class MusicListConverter {
        @TypeConverter
        public static String musicToStr(List<Music> music) {
            // Chuyển đối danh sách Music thành chuỗi JSON
            return gson.toJson(music);
        }

        // Converter cho việc chuyển đổi chuỗi JSON thành danh sách Music
        @TypeConverter
        public static List<Music> strToMusic(String data) {
            // Nếu chuỗi JSON là null, trả về danh sách rỗng
            if (data == null) return Collections.emptyList();

            // Chuyển đối chuỗi JSON thành danh sách Music
            return gson.fromJson(data, new TypeToken<List<Music>>() {
            }.getType());
        }
    }
}

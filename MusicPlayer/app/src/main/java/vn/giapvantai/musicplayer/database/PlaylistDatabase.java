package vn.giapvantai.musicplayer.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import vn.giapvantai.musicplayer.MPConstants;
import vn.giapvantai.musicplayer.database.converter.CustomConverter;
import vn.giapvantai.musicplayer.model.PlayList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters({CustomConverter.MusicListConverter.class})
@Database(entities = {PlayList.class}, version = MPConstants.DATABASE_VERSION, exportSchema = false)
public abstract class PlayListDatabase extends RoomDatabase {

    // Sử dụng ExecutorService để thực hiện các tác vụ cơ sở dữ liệu trên một luồng duy nhất
    public static final ExecutorService databaseExecutor = Executors.newSingleThreadExecutor();

    // Volatile giúp đảm bảo rằng INSTANCE được thấy bởi tất cả các luồng
    private static volatile PlayListDatabase INSTANCE;

    // Phương thức để lấy đối tượng PlayListDatabase
    public static PlayListDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlayListDatabase.class) {
                if (INSTANCE == null) {
                    // Tạo mới INSTANCE của cơ sở dữ liệu nếu nó chưa được tạo
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    PlayListDatabase.class, MPConstants.DATABASE_NAME)
                            .fallbackToDestructiveMigration() // Cho phép xóa toàn bộ cơ sở dữ liệu khi thực hiện migration
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Phương thức abstract để trả về đối tượng PlayListDao
    public abstract PlayListDao dao();
}

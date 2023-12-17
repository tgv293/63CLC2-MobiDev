package vn.giapvantai.musicplayer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import vn.giapvantai.musicplayer.MPConstants;
import vn.giapvantai.musicplayer.model.PlayList;

import java.util.List;

@Dao
public interface PlayListDao {
    // Insert: Thêm một đối tượng PlayList vào cơ sở dữ liệu
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(PlayList playList);

    // Delete: Xóa một đối tượng PlayList khỏi cơ sở dữ liệu
    @Delete
    void delete(PlayList playList);

    // Query: Truy vấn tất cả các PlayList từ cơ sở dữ liệu và trả về LiveData để theo dõi sự thay đổi tự động
    @Query("SELECT * FROM " + MPConstants.MUSIC_TABLE)
    LiveData<List<PlayList>> all();

    // Update: Cập nhật thông tin của một đối tượng PlayList trong cơ sở dữ liệu
    @Update
    void update(PlayList playList);
}

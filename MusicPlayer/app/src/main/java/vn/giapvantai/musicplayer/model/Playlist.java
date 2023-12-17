package vn.giapvantai.musicplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import vn.giapvantai.musicplayer.MPConstants;

import java.util.List;

@Entity(tableName = MPConstants.MUSIC_TABLE)
public class PlayList implements Parcelable {

    // Parcelable.Creator để tạo đối tượng PlayList từ Parcel
    public static final Creator<PlayList> CREATOR = new Creator<PlayList>() {
        @Override
        public PlayList createFromParcel(Parcel in) {
            return new PlayList(in);
        }

        @Override
        public PlayList[] newArray(int size) {
            return new PlayList[size];
        }
    };

    // Khóa chính, sử dụng title làm khóa
    @NonNull
    @PrimaryKey
    public String title = "";

    // Danh sách bài hát trong danh sách phát
    public List<Music> musics;

    // Constructor mặc định, được sử dụng khi tạo mới đối tượng PlayList
    public PlayList() {
    }

    // Constructor sử dụng để tạo đối tượng PlayList từ Parcel
    protected PlayList(Parcel in) {
        title = in.readString();
        musics = in.createTypedArrayList(Music.CREATOR);
    }

    // Phương thức mô tả nội dung của đối tượng PlayList
    @Override
    public int describeContents() {
        return 0;
    }

    // Phương thức ghi thông tin của đối tượng PlayList vào Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeTypedList(musics);
    }
}

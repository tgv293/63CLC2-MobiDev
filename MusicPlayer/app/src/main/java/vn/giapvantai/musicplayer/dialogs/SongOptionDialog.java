package vn.giapvantai.musicplayer.dialogs;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import vn.giapvantai.musicplayer.R;
import vn.giapvantai.musicplayer.model.Music;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class SongOptionDialog extends BottomSheetDialog {

    public SongOptionDialog(@NonNull Context context, Music music) {
        super(context);
        setContentView(R.layout.dialog_option);

        // Ánh xạ TextView từ layout dialog_option
        TextView option = findViewById(R.id.add_to_playlist);
        assert option != null;

        // Xử lý sự kiện click trên TextView
        option.setOnClickListener(v ->
                showPlaylistDialog(music));
    }

    // Hiển thị dialog danh sách phát khi sự kiện click xảy ra
    private void showPlaylistDialog(Music music) {
        // Tạo mới dialog danh sách phát và hiển thị
        PlaylistHandlerDialog dialog = new PlaylistHandlerDialog(getContext(), music);
        dismiss(); // Đóng dialog hiện tại
        dialog.show(); // Hiển thị dialog danh sách phát
    }
}

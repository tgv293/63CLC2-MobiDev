package vn.giapvantai.musicplayer.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.giapvantai.musicplayer.R;
import vn.giapvantai.musicplayer.database.PlayListDatabase;
import vn.giapvantai.musicplayer.model.Music;
import vn.giapvantai.musicplayer.model.PlayList;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class PlaylistHandlerDialog extends BottomSheetDialog {
    private final PlayListDatabase database;
    private final Music music;

    public PlaylistHandlerDialog(@NonNull Context context, Music music) {
        super(context);
        setContentView(R.layout.dialog_playlist);

        this.music = music;

        RecyclerView playListView = findViewById(R.id.playlist_layout);
        assert playListView != null;
        playListView.setHasFixedSize(true);
        playListView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo cơ sở dữ liệu và quan sát danh sách các danh sách phát
        database = PlayListDatabase.getDatabase(context);
        database.dao().all().observeForever(playList -> {
            PlayListAdapter adapter = new PlayListAdapter(playList);
            playListView.setAdapter(adapter);
        });

        TextInputEditText name = findViewById(R.id.new_playlist_name);
        assert name != null;
        MaterialButton add = findViewById(R.id.new_playlist);
        assert add != null;
        add.setOnClickListener(v -> {
            // Kiểm tra tên danh sách phát mới
            if (name.getText() != null && name.getText().toString().length() > 0) {
                // Tạo mới đối tượng PlayList và thêm bài hát vào danh sách của nó
                PlayList playList = new PlayList();
                playList.title = name.getText().toString();
                List<Music> musicList = new ArrayList<>();
                musicList.add(music);
                playList.musics = musicList;

                // Hiển thị thông báo và đóng dialog
                Toast.makeText(getContext(), "Song added to " + playList.title, Toast.LENGTH_SHORT).show();
                dismiss();

                // Thêm danh sách phát mới vào cơ sở dữ liệu bất đồng bộ
                PlayListDatabase.databaseExecutor.execute(() -> database.dao().add(playList));
            } else {
                Toast.makeText(getContext(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Adapter cho danh sách các danh sách phát
    private class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.MyViewHolder> {
        private final List<PlayList> playLists;

        public PlayListAdapter(List<PlayList> playLists) {
            this.playLists = playLists;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Tạo mới ViewHolder từ layout item_playlist_list
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist_list, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            // Thiết lập tên danh sách phát trong ViewHolder
            holder.name.setText(playLists.get(position).title);
        }

        @Override
        public int getItemCount() {
            // Trả về số lượng danh sách phát
            return playLists.size();
        }

        // ViewHolder cho mỗi item trong danh sách danh sách phát
        public class MyViewHolder extends RecyclerView.ViewHolder {
            private final TextView name;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                // Ánh xạ view từ layout item_playlist_list
                name = itemView.findViewById(R.id.name);

                // Xử lý sự kiện click trên item danh sách phát
                itemView.findViewById(R.id.root_layout).setOnClickListener(v -> {
                    // Lấy danh sách phát tại vị trí của ViewHolder và thêm bài hát vào danh sách
                    PlayList playList = playLists.get(getAdapterPosition());
                    playList.musics.add(music);

                    // Hiển thị thông báo và đóng dialog
                    Toast.makeText(getContext(), "Song added to " + playList.title, Toast.LENGTH_SHORT).show();
                    dismiss();

                    // Cập nhật danh sách phát trong cơ sở dữ liệu bất đồng bộ
                    PlayListDatabase.databaseExecutor.execute(() -> database.dao().update(playList));
                });
            }
        }
    }
}

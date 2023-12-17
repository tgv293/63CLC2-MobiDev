package vn.giapvantai.musicplayer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import vn.giapvantai.musicplayer.MPConstants;
import vn.giapvantai.musicplayer.MPPreferences;
import vn.giapvantai.musicplayer.R;
import vn.giapvantai.musicplayer.adapter.PlayListAdapter;
import vn.giapvantai.musicplayer.database.PlayListDatabase;
import vn.giapvantai.musicplayer.helper.MusicLibraryHelper;
import vn.giapvantai.musicplayer.listener.MusicSelectListener;
import vn.giapvantai.musicplayer.model.Music;
import vn.giapvantai.musicplayer.model.PlayList;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PlaylistFragment extends Fragment implements PlayListAdapter.PlayListListener {

    private final MusicSelectListener musicSelectListener = MPConstants.musicSelectListener;
    private final List<PlayList> playLists = new ArrayList<>();
    private final List<Music> musicList = new ArrayList<>();
    private MaterialToolbar toolbar;
    private PlayList playList;
    private PlayListDatabase database;
    private SongsAdapter adapter;
    private PlayListAdapter playListAdapter;
    private TextView oops;

    public static PlaylistFragment newInstance() {
        return new PlaylistFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        // Khởi tạo cơ sở dữ liệu danh sách phát
        database = PlayListDatabase.getDatabase(requireContext());

        // Ánh xạ các view từ layout
        ExtendedFloatingActionButton shuffleControl = view.findViewById(R.id.shuffle_button);
        toolbar = view.findViewById(R.id.search_toolbar);
        oops = view.findViewById(R.id.oops_text);

        RecyclerView playListView = view.findViewById(R.id.playlist_layout);
        playListView.setHasFixedSize(true);
        playListView.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        playListAdapter = new PlayListAdapter(this, playLists);
        playListView.setAdapter(playListAdapter);

        RecyclerView recyclerView = view.findViewById(R.id.songs_layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new SongsAdapter(musicSelectListener, musicList);
        recyclerView.setAdapter(adapter);

        // Xử lý sự kiện khi nhấn vào nút shuffle
        shuffleControl.setOnClickListener(v -> musicSelectListener.playQueue(musicList, true));

        // Quan sát danh sách các danh sách phát từ cơ sở dữ liệu
        database.dao().all().observe(requireActivity(), playList -> {
            playLists.clear();
            playLists.addAll(playList);
            playListAdapter.notifyDataSetChanged();

            // Hiển thị danh sách phát đầu tiên nếu có
            if (playList.size() > 0) {
                setCurrPlaylist(playList.get(0));
                oops.setVisibility(View.GONE);
            } else {
                // Hiển thị thông báo nếu không có danh sách phát
                oops.setVisibility(View.VISIBLE);
            }
        });

        // Thiết lập các tùy chọn trên thanh toolbar
        setUpOptions();

        return view;
    }

    // Phương thức để thiết lập danh sách phát hiện tại
    private void setCurrPlaylist(PlayList list) {
        playList = list;
        musicList.clear();
        musicList.addAll(list.musics);
        adapter.notifyDataSetChanged();
    }

    // Phương thức để thiết lập các tùy chọn trên thanh toolbar
    private void setUpOptions() {
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            if (id == R.id.menu_add_to_queue) {
                // Thêm toàn bộ danh sách nhạc vào hàng đợi
                musicSelectListener.addToQueue(musicList);
                return true;
            } else if (id == R.id.menu_delete_playlist) {
                // Xử lý sự kiện xóa danh sách phát
                showDeletePlaylistDialog();
                return true;
            }

            return false;
        });

        // Xử lý sự kiện khi nhấn nút back trên thanh toolbar
        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());
    }

    // Phương thức để hiển thị hộp thoại xóa danh sách phát
    private void showDeletePlaylistDialog() {
        if (playList != null) {
            new MaterialAlertDialogBuilder(requireContext())
                    .setMessage("Are you sure you want to delete this playlist?")
                    .setPositiveButton("Delete", (dia, which) -> {
                        // Xóa danh sách phát
                        deletePlaylist();
                        dia.dismiss();
                    }).setNegativeButton("Cancel", (dia, which) -> dia.dismiss()).show();
        }
    }

    // Phương thức để xóa danh sách phát
    private void deletePlaylist() {
        if (playList != null) {
            musicList.clear();
            Toast.makeText(requireContext(), "Playlist deleted successfully", Toast.LENGTH_SHORT).show();
            PlayListDatabase.databaseExecutor.execute(() -> database.dao().delete(playList));
        }
    }

    // Phương thức để xóa bài hát khỏi danh sách phát
    private void removeSongFromPlayList(Music music) {
        playList.musics.remove(music);
        adapter.notifyDataSetChanged();
        Toast.makeText(requireContext(), "Song removed from playlist", Toast.LENGTH_SHORT).show();
        PlayListDatabase.databaseExecutor.execute(() -> database.dao().update(playList));
    }

    // Phương thức từ giao diện PlayListAdapter.PlayListListener
    @Override
    public void click(PlayList playList) {
        setCurrPlaylist(playList);
    }

    // Adapter cho danh sách các bài hát
    public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {

        private final List<Music> musicList;
        public MusicSelectListener listener;

        public SongsAdapter(MusicSelectListener listener, List<Music> musics) {
            this.listener = listener;
            this.musicList = musics;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // Tạo mới ViewHolder từ layout item_songs
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_songs, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            // Thiết lập thông tin bài hát trong ViewHolder
            holder.songName.setText(musicList.get(position).title);
            holder.albumName.setText(
                    String.format(Locale.getDefault(), "%s • %s",
                            musicList.get(position).artist,
                            musicList.get(position).album)
            );

            // Hiển thị thông tin thêm nếu có
            if (musicList.get(position).dateAdded == -1)
                holder.songHistory.setVisibility(View.GONE);
            else
                holder.songHistory.setText(
                        String.format(Locale.getDefault(), "%s • %s",
                                MusicLibraryHelper.formatDuration(musicList.get(position).duration),
                                MusicLibraryHelper.formatDate(musicList.get(position).dateAdded))
                );

            // Hiển thị ảnh album nếu được cho phép
            if (holder.state)
                Glide.with(holder.albumArt.getContext())
                        .load(musicList.get(position).albumArt)
                        .placeholder(R.drawable.ic_album_art)
                        .into(holder.albumArt);
        }

        @Override
        public int getItemCount() {
            return musicList.size();
        }

        // ViewHolder cho mỗi item trong danh sách bài hát
        public class MyViewHolder extends RecyclerView.ViewHolder {

            private final TextView songName;
            private final TextView albumName;
            private final TextView songHistory;
            private final ImageView albumArt;
            private final boolean state;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                // Ánh xạ view từ layout item_songs
                state = MPPreferences.getAlbumRequest(itemView.getContext());
                albumArt = itemView.findViewById(R.id.album_art);
                songHistory = itemView.findViewById(R.id.song_history);
                songName = itemView.findViewById(R.id.song_name);
                albumName = itemView.findViewById(R.id.song_album);

                // Tạo hộp thoại xác nhận xóa bài hát khỏi danh sách phát
                MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(itemView.getContext())
                        .setMessage("Are you sure you want to remove this song from the playlist?")
                        .setPositiveButton("Remove", (dia, which) -> {
                            removeSongFromPlayList(musicList.get(getAdapterPosition()));
                            dia.dismiss();
                        }).setNegativeButton("Cancel", (dia, which) -> dia.dismiss());

                // Xử lý sự kiện khi click và long click trên item bài hát
                itemView.findViewById(R.id.root_layout).setOnClickListener(v -> listener.playQueue(musicList.subList(getAdapterPosition(), musicList.size()), false));

                itemView.findViewById(R.id.root_layout).setOnLongClickListener(v -> {
                    dialog.show();
                    return true;
                });
            }
        }
    }
}

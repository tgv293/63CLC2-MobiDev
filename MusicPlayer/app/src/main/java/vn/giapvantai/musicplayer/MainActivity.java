package vn.giapvantai.musicplayer;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import vn.giapvantai.musicplayer.activities.PlayerDialog;
import vn.giapvantai.musicplayer.adapter.MainPagerAdapter;
import vn.giapvantai.musicplayer.helper.MusicLibraryHelper;
import vn.giapvantai.musicplayer.helper.PermissionHelper;
import vn.giapvantai.musicplayer.listener.MusicSelectListener;
import vn.giapvantai.musicplayer.listener.PlayerDialogListener;
import vn.giapvantai.musicplayer.listener.PlayerListener;
import vn.giapvantai.musicplayer.model.Music;
import vn.giapvantai.musicplayer.player.PlayerBuilder;
import vn.giapvantai.musicplayer.player.PlayerManager;
import vn.giapvantai.musicplayer.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity
        implements MusicSelectListener, PlayerListener, View.OnClickListener, PlayerDialogListener {

    private RelativeLayout playerView;
    private ImageView albumArt;
    private TextView songName;
    private TextView songDetails;
    private ImageButton play_pause;
    private LinearProgressIndicator progressIndicator;
    private PlayerDialog playerDialog;
    private PlayerBuilder playerBuilder;
    private PlayerManager playerManager;
    private boolean albumState;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MPConstants.musicSelectListener = this;

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Kiểm tra và yêu cầu quyền đọc storage
        if (PermissionHelper.hasReadStoragePermission(MainActivity.this)) {
            fetchMusicList();
            setUpUiElements();
        } else {
            manageStoragePermission(MainActivity.this);
        }

        // Kiểm tra và yêu cầu quyền notification
        if (!PermissionHelper.hasNotificationPermission(MainActivity.this)) {
            PermissionHelper.requestNotificationPermission(MainActivity.this);
        }

        albumState = MPPreferences.getAlbumRequest(this);

        MaterialCardView playerLayout = findViewById(R.id.player_layout);
        albumArt = findViewById(R.id.albumArt);
        progressIndicator = findViewById(R.id.song_progress);
        playerView = findViewById(R.id.player_view);
        songName = findViewById(R.id.song_title);
        songDetails = findViewById(R.id.song_details);
        play_pause = findViewById(R.id.control_play_pause);
        ImageButton queue = findViewById(R.id.control_queue);

        play_pause.setOnClickListener(this);
        playerLayout.setOnClickListener(this);
        queue.setOnClickListener(this);
    }

    private void setPlayerView() {
        if (playerManager != null && playerManager.isPlaying()) {
            playerView.setVisibility(View.VISIBLE);
            onMusicSet(playerManager.getCurrentMusic());
        }
    }

    private void fetchMusicList() {
        new Handler().post(() -> {
            List<Music> musicList = MusicLibraryHelper.fetchMusicLibrary(MainActivity.this);
            viewModel.setSongsList(musicList);
            viewModel.parseFolderList(musicList);
        });
    }

    public void setUpUiElements() {
        playerBuilder = new PlayerBuilder(MainActivity.this, this);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this, this);

        ViewPager2 viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOffscreenPageLimit(3);

        TabLayout tabs = findViewById(R.id.tabs);

        // Kết nối TabLayout và ViewPager
        new TabLayoutMediator(tabs, viewPager,
                (tab, position) -> tab.setIcon(MPConstants.TAB_ICONS[position])
        ).attach();
    }

    public void manageStoragePermission(Activity context) {
        // Kiểm tra cần hiển thị dialog không
        if (PermissionHelper.requirePermissionRationale(context)) {
            new MaterialAlertDialogBuilder(context)
                    .setTitle("Yêu cầu quyền")
                    .setMessage("Bật quyền lưu trữ để truy cập các tệp phương tiện.")
                    .setPositiveButton("Chấp nhận", (dialog, which) -> PermissionHelper.requestStoragePermission(context)).show();
        } else {
            PermissionHelper.requestStoragePermission(context);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy bỏ kết nối service và giải phóng tài nguyên khi Activity bị hủy
        if (playerBuilder != null)
            playerBuilder.unBindService();

        if (playerDialog != null)
            playerDialog.dismiss();
    }

    @Override
    public void playQueue(List<Music> musicList, boolean shuffle) {
        // Trộn danh sách nếu được yêu cầu
        if (shuffle) {
            Collections.shuffle(musicList);
        }

        if (!musicList.isEmpty()) {
            playerManager.setMusicList(musicList);
            setPlayerView();
        }
    }

    @Override
    public void addToQueue(List<Music> musicList) {
        if (!musicList.isEmpty()) {
            if (playerManager != null && playerManager.isPlaying())
                playerManager.addMusicQueue(musicList);
            else if (playerManager != null)
                playerManager.setMusicList(musicList);

            setPlayerView();
        }
    }

    @Override
    public void refreshMediaLibrary() {
        fetchMusicList();
    }

    @Override
    public void onPrepared() {
        playerManager = playerBuilder.getPlayerManager();
        setPlayerView();
    }

    @Override
    public void onStateChanged(int state) {
        // Cập nhật giao diện người nghe dựa trên trạng thái
        if (state == State.PLAYING)
            play_pause.setImageResource(R.drawable.ic_controls_pause);
        else
            play_pause.setImageResource(R.drawable.ic_controls_play);
    }

    @Override
    public void onPositionChanged(int position) {
        // Cập nhật tiến trình phát nhạc
        progressIndicator.setProgress(position);
    }

    @Override
    public void onMusicSet(Music music) {
        // Cập nhật thông tin bài hát và hiển thị albumArt nếu được yêu cầu
        songName.setText(music.title);
        songDetails.setText(
                String.format(Locale.getDefault(), "%s • %s",
                        music.artist, music.album));
        playerView.setVisibility(View.VISIBLE);

        if (albumState)
            Glide.with(getApplicationContext())
                    .load(music.albumArt)
                    .centerCrop()
                    .into(albumArt);

        // Cập nhật trạng thái nút play/pause
        if (playerManager != null && playerManager.isPlaying())
            play_pause.setImageResource(R.drawable.ic_controls_pause);
        else
            play_pause.setImageResource(R.drawable.ic_controls_play);
    }

    @Override
    public void onPlaybackCompleted() {
        // Xử lý khi phát nhạc hoàn tất
    }

    @Override
    public void onRelease() {
        // Ẩn giao diện người nghe khi không có bài hát đang phát
        playerView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Xử lý sự kiện click các nút control
        if (id == R.id.control_play_pause)
            playerManager.playPause();

        else if (id == R.id.control_queue)
            setUpQueueDialogHeadless();

        else if (id == R.id.player_layout)
            setUpPlayerDialog();
    }

    private void setUpPlayerDialog() {
        // Hiển thị dialog người nghe
        playerDialog = new PlayerDialog(this, playerManager, this);
        playerDialog.show();
    }

    @Override
    public void queueOptionSelect() {
        // Hiển thị dialog hàng đợi
        setUpQueueDialog();
    }

    private void setUpQueueDialog() {
    }

    private void setUpQueueDialogHeadless() {
    }

}

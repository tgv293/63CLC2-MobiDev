package vn.giapvantai.musicplayer.player;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.KeyEvent;

import java.util.Objects;

import vn.giapvantai.musicplayer.MPConstants;

public class PlayerService extends Service {

    private final IBinder iBinder = new LocalBinder();
    private PlayerManager playerManager;
    private PlayerNotificationManager notificationManager;
    private MediaSessionCompat mediaSessionCompat;

    // Callback cho MediaSessionCompat để xử lý sự kiện từ bảng điều khiển truyền thông
    private final MediaSessionCompat.Callback mediaSessionCallback = new MediaSessionCompat.Callback() {
        @Override
        public void onPlay() {
            playerManager.playPause();
        }

        @Override
        public void onPause() {
            playerManager.playPause();
        }

        @Override
        public void onSkipToNext() {
            playerManager.playNext();
        }

        @Override
        public void onSkipToPrevious() {
            playerManager.playPrev();
        }

        @Override
        public void onRewind() {
            playerManager.seekTo(0);
        }

        @Override
        public void onStop() {
            playerManager.playPause();
        }

        @Override
        public void onSeekTo(long pos) {
            playerManager.seekTo((int) pos);
        }

        @Override
        public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
            return handleMediaButtonEvent(mediaButtonEvent);
        }
    };

    private PowerManager.WakeLock wakeLock;

    // Constructor mặc định
    public PlayerService() {
    }

    // Getter cho PlayerManager
    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    // Getter cho PlayerNotificationManager
    public PlayerNotificationManager getNotificationManager() {
        return notificationManager;
    }

    // Phương thức onStartCommand, được gọi khi Service được khởi động
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (playerManager != null && playerManager.isPlaying())
            playerManager.attachService();

        return START_NOT_STICKY;
    }

    // Cấu hình MediaSessionCompat để xử lý sự kiện truyền thông
    private void configureMediaSession() {
        Intent mediaButtonIntent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        ComponentName mediaButtonReceiverComponentName = new ComponentName(this, PlayerManager.NotificationReceiver.class);
        PendingIntent mediaButtonReceiverPendingIntent = PendingIntent.getBroadcast(this, 0, mediaButtonIntent, PendingIntent.FLAG_IMMUTABLE);

        mediaSessionCompat = new MediaSessionCompat(this, MPConstants.MEDIA_SESSION_TAG, mediaButtonReceiverComponentName, mediaButtonReceiverPendingIntent);
        mediaSessionCompat.setActive(true);
        mediaSessionCompat.setCallback(mediaSessionCallback);
        mediaSessionCompat.setMediaButtonReceiver(mediaButtonReceiverPendingIntent);
    }

    // Xử lý sự kiện từ bảng điều khiển truyền thông
    private boolean handleMediaButtonEvent(Intent mediaButtonEvent) {
        boolean isSuccess = false;
        if (mediaButtonEvent == null) {
            return false;
        }

        KeyEvent keyEvent = mediaButtonEvent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);

        if (Objects.requireNonNull(keyEvent).getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyEvent.getKeyCode()) {
                case KeyEvent.KEYCODE_MEDIA_PAUSE:
                case KeyEvent.KEYCODE_MEDIA_PLAY:
                case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                case KeyEvent.KEYCODE_HEADSETHOOK:
                    playerManager.playPause();
                    isSuccess = true;
                    break;

                case KeyEvent.KEYCODE_MEDIA_CLOSE:
                case KeyEvent.KEYCODE_MEDIA_STOP:
                    playerManager.pauseMediaPlayer();
                    isSuccess = true;
                    break;

                case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                    playerManager.playPrev();
                    isSuccess = true;
                    break;

                case KeyEvent.KEYCODE_MEDIA_NEXT:
                    playerManager.playNext();
                    isSuccess = true;
                    break;

                case KeyEvent.KEYCODE_MEDIA_REWIND:
                    playerManager.seekTo(0);
                    isSuccess = true;
                    break;
            }
        }

        return isSuccess;
    }

    // Phương thức onBind, được gọi khi một thành phần khác muốn kết nối với Service
    @Override
    public IBinder onBind(Intent intent) {
        if (playerManager == null) {
            playerManager = new PlayerManager(this);
            notificationManager = new PlayerNotificationManager(this);
            playerManager.registerActionsReceiver();
        }

        return iBinder;
    }

    // Phương thức onCreate, được gọi khi Service được tạo
    @Override
    public void onCreate() {
        super.onCreate();
        if (wakeLock == null) {
            PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
            wakeLock.setReferenceCounted(false);
        }

        configureMediaSession();
    }

    // Phương thức onDestroy, được gọi khi Service bị hủy
    @Override
    public void onDestroy() {
        if (playerManager != null) {
            playerManager.unregisterActionsReceiver();
            playerManager.release();
        }
        notificationManager = null;
        playerManager = null;

        super.onDestroy();
    }

    // Getter cho MediaSessionCompat
    public MediaSessionCompat getMediaSessionCompat() {
        return mediaSessionCompat;
    }

    // Lớp LocalBinder để cung cấp đối tượng Binder cục bộ
    class LocalBinder extends Binder {
        public PlayerService getInstance() {
            return PlayerService.this;
        }
    }
}

package vn.giapvantai.musicplayer;

import vn.giapvantai.musicplayer.listener.MusicSelectListener;

import java.util.Arrays;
import java.util.List;

public class MPConstants {
    public static final String PACKAGE_NAME = "vn.giapvantai.musicplayer";

    public static final int PERMISSION_READ_STORAGE = 1009;
    public static final long TWENTY_SECONDS_IN_MS = 20000;

    public static final String MEDIA_SESSION_TAG = "vn.giapvantai.musicplayer.MediaSession";

    public static final int NOTIFICATION_ID = 101;
    public static final String PLAY_PAUSE_ACTION = "vn.giapvantai.musicplayer.PLAYPAUSE";
    public static final String NEXT_ACTION = "vn.giapvantai.musicplayer.NEXT";
    public static final String PREV_ACTION = "vn.giapvantai.musicplayer.PREV";
    public static final String CHANNEL_ID = "vn.giapvantai.musicplayer.CHANNEL_ID";
    public static final int REQUEST_CODE = 100;

    public static final float VOLUME_DUCK = 0.2f;
    public static final float VOLUME_NORMAL = 1.0f;
    public static final int AUDIO_NO_FOCUS_NO_DUCK = 0;
    public static final int AUDIO_NO_FOCUS_CAN_DUCK = 1;
    public static final int AUDIO_FOCUSED = 2;

    public static final int[] TAB_ICONS = new int[]{
            R.drawable.ic_music_note,
            R.drawable.ic_artist,
            R.drawable.ic_library_music,
            R.drawable.ic_settings,
    };
    public static final String SETTINGS_ALBUM_REQUEST = "shared_pref_album_request";
    public static final String SETTINGS_AUTO_PLAY = "shared_pref_auto_play_music";

    public static final int SORT_MUSIC_BY_TITLE = 0;
    public static final int SORT_MUSIC_BY_DATE_ADDED = 1;
    public static final int SORT_ARTIST_BY_NAME = 0;
    public static final int SORT_ARTIST_BY_ALBUMS = 1;
    public static final int SORT_ARTIST_BY_SONGS = 2;
    public static final int SORT_ALBUM_BY_TITLE = 0;
    public static final int SORT_ALBUM_BY_DURATION = 1;
    public static final int SORT_ALBUM_BY_SONGS = 2;
    public static MusicSelectListener musicSelectListener;
}

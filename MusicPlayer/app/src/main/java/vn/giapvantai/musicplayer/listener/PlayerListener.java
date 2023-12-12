package vn.giapvantai.musicplayer.listener;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import vn.giapvantai.musicplayer.model.Music;

public interface PlayerListener {

    void onPrepared();

    void onStateChanged(@State int state);

    void onPositionChanged(int position);

    void onMusicSet(Music music);

    void onPlaybackCompleted();

    void onRelease();


    @IntDef({State.INVALID,
            State.PLAYING,
            State.PAUSED,
            State.COMPLETED,
            State.RESUMED})
    @Retention(RetentionPolicy.SOURCE)
    @interface State {
        int INVALID = -1;
        int PLAYING = 0;
        int PAUSED = 1;
        int COMPLETED = 2;
        int RESUMED = 3;
    }
}

package vn.giapvantai.musicplayer;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Lớp quản lý và lưu trữ các thiết lập ứng dụng sử dụng SharedPreferences.
 */
public class MPPreferences {

    /**
     * Phương thức trả về Editor để chỉnh sửa SharedPreferences.
     *
     * @param context Context của ứng dụng.
     * @return Editor của SharedPreferences.
     */
    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                MPConstants.PACKAGE_NAME, Context.MODE_PRIVATE
        );
        return sharedPreferences.edit();
    }

    /**
     * Phương thức trả về SharedPreferences.
     *
     * @param context Context của ứng dụng.
     * @return SharedPreferences của ứng dụng.
     */
    private static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(
                MPConstants.PACKAGE_NAME, Context.MODE_PRIVATE
        );
    }

    /**
     * Lưu trạng thái yêu cầu album của ứng dụng.
     *
     * @param context Context của ứng dụng.
     * @param val     Giá trị trạng thái yêu cầu album cần lưu.
     */
    public static void storeAlbumRequest(Context context, boolean val) {
        getEditor(context).putBoolean(MPConstants.SETTINGS_ALBUM_REQUEST, val).apply();
    }

    /**
     * Lưu trạng thái Auto Play của ứng dụng.
     *
     * @param context Context của ứng dụng.
     * @param val     Giá trị trạng thái Auto Play cần lưu.
     */
    public static void storeAutoPlay(Context context, boolean val) {
        getEditor(context).putBoolean(MPConstants.SETTINGS_AUTO_PLAY, val).apply();
    }

    /**
     * Lấy giá trị trạng thái yêu cầu album đã lưu.
     *
     * @param context Context của ứng dụng.
     * @return Giá trị trạng thái yêu cầu album đã lưu.
     */
    public static boolean getAlbumRequest(Context context) {
        return getSharedPref(context).getBoolean(MPConstants.SETTINGS_ALBUM_REQUEST, false);
    }

    /**
     * Lấy giá trị trạng thái Auto Play đã lưu.
     *
     * @param context Context của ứng dụng.
     * @return Giá trị trạng thái Auto Play đã lưu.
     */
    public static boolean getAutoPlay(Context context) {
        return getSharedPref(context).getBoolean(MPConstants.SETTINGS_AUTO_PLAY, true);
    }

}

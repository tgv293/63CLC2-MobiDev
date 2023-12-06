package vn.giapvantai.musicplayer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;
import java.util.List;

import vn.giapvantai.musicplayer.MPConstants;
import vn.giapvantai.musicplayer.MPPreferences;
import vn.giapvantai.musicplayer.R;
import vn.giapvantai.musicplayer.model.Folder;
import vn.giapvantai.musicplayer.viewmodel.MainViewModel;

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private MainViewModel viewModel;
    private boolean state;
    private boolean autoPlayState;
    private LinearLayout chipLayout;

    private List<Folder> folderList;
    private MaterialToolbar toolbar;

    public SettingsFragment() {
        // Không sử dụng
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Gắn layout cho fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        viewModel.getFolderList().observe(requireActivity(), folders -> {
            if (folderList == null)
                folderList = new ArrayList<>();
            folderList.clear();
            folderList.addAll(folders);
        });

        SwitchMaterial switchMaterial = view.findViewById(R.id.album_switch);
        SwitchMaterial autoPlaySwitch = view.findViewById(R.id.auto_play_switch);
        chipLayout = view.findViewById(R.id.chip_layout);
        toolbar = view.findViewById(R.id.toolbar);

        LinearLayout albumOption = view.findViewById(R.id.album_options);
        LinearLayout folderOption = view.findViewById(R.id.folder_options);
        LinearLayout refreshOption = view.findViewById(R.id.refresh_options);

        state = MPPreferences.getAlbumRequest(requireActivity().getApplicationContext());
        autoPlayState = MPPreferences.getAutoPlay(requireActivity().getApplicationContext());
        switchMaterial.setChecked(state);
        autoPlaySwitch.setChecked(autoPlayState);

        albumOption.setOnClickListener(this);
        switchMaterial.setOnClickListener(this);
        autoPlaySwitch.setOnClickListener(this);
        folderOption.setOnClickListener(this);
        refreshOption.setOnClickListener(this);

        view.findViewById(R.id.night_chip).setOnClickListener(this);
        view.findViewById(R.id.light_chip).setOnClickListener(this);
        view.findViewById(R.id.auto_chip).setOnClickListener(this);
        view.findViewById(R.id.review_options).setOnClickListener(this);

        setUpOptions();

        return view;
    }

    private void setUpOptions() {
        // Xử lý sự kiện khi chọn menu trên thanh toolbar
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();

            return false;
        });
        // Xử lý sự kiện khi nhấn nút back trên thanh toolbar
        toolbar.setNavigationOnClickListener(v -> requireActivity().finish());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        // Xử lý sự kiện khi click vào các tùy chọn
        if (id == R.id.album_options)
            setAlbumRequest();
        else if (id == R.id.album_switch)
            setAlbumRequest();
        else if (id == R.id.auto_play_switch)
            setAutoPlay();
        else if (id == R.id.theme_mode_option) {
            int mode = chipLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            chipLayout.setVisibility(mode);
        } else if (id == R.id.refresh_options) {
            refreshMediaLibrary();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void refreshMediaLibrary() {
        // Cập nhật thư viện media
        Toast.makeText(requireActivity(), "Đang cập nhật thư viện media", Toast.LENGTH_SHORT).show();
        MPConstants.musicSelectListener.refreshMediaLibrary();
    }

    private void setAlbumRequest() {
        // Đặt yêu cầu hiển thị album và áp dụng cài đặt
        MPPreferences.storeAlbumRequest(requireActivity().getApplicationContext(), (!state));
    }

    private void setAutoPlay() {
        // Đặt tự động phát và lưu vào SharedPreferences
        MPPreferences.storeAutoPlay(requireActivity().getApplicationContext(), (!autoPlayState));
    }

}

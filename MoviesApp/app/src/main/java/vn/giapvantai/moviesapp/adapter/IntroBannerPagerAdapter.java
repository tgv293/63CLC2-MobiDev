package vn.giapvantai.moviesapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.giapvantai.moviesapp.fragments.Intro1Fragment;
import vn.giapvantai.moviesapp.fragments.Intro2Fragment;
import vn.giapvantai.moviesapp.fragments.Intro3Fragment;
import vn.giapvantai.moviesapp.fragments.Intro4Fragment;

public class IntroBannerPagerAdapter extends FragmentStateAdapter {

    public IntroBannerPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new Intro1Fragment();
            case 1:
                return new Intro2Fragment();
            case 2:
                return new Intro3Fragment();
            case 3:
                return new Intro4Fragment();
            default:
                throw new IllegalArgumentException("Invalid fragment index");
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
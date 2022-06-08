package com.danghuong.musicapp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.danghuong.musicapp.ui.frament.nhaccuatoifragment.NhacCuaToiFragment;
import com.danghuong.musicapp.ui.frament.watchfragment.WatchFragment;

import org.jetbrains.annotations.NotNull;

public class HomeAdapter extends FragmentStateAdapter {
    private NhacCuaToiFragment nhaccuatoifragment;
    private WatchFragment watchFragment;

    public HomeAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public HomeAdapter(@NonNull @NotNull Fragment fragment) {
        super(fragment);
    }

    public HomeAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {

            case 0:
                if (nhaccuatoifragment == null) nhaccuatoifragment = new NhacCuaToiFragment();
                return nhaccuatoifragment;
            default:
                if (watchFragment == null) watchFragment = new WatchFragment();
                return watchFragment;

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}

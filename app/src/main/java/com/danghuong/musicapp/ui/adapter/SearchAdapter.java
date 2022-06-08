package com.danghuong.musicapp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.danghuong.musicapp.ui.frament.searchfrag.searchartistfrag.SearchArtistFrag;
import com.danghuong.musicapp.ui.frament.searchfrag.searchnamesongfrag.SearchNameSongFrag;

import org.jetbrains.annotations.NotNull;

public class SearchAdapter extends FragmentStateAdapter {

    private SearchArtistFrag searchArtistFrag;
    private SearchNameSongFrag searchNameSongFrag;


    public SearchAdapter(@NonNull @NotNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public SearchAdapter(@NonNull @NotNull Fragment fragment) {
        super(fragment);
    }

    public SearchAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }
    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                if (searchNameSongFrag == null) searchNameSongFrag = new SearchNameSongFrag();
                return searchNameSongFrag;
            default:
                if (searchArtistFrag == null) searchArtistFrag = new SearchArtistFrag();
                return searchArtistFrag;

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

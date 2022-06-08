package com.danghuong.musicapp.ui.frament.searchfrag.searchartistfrag;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.databinding.SearchArtistRcBinding;
import com.danghuong.musicapp.ui.adapter.SearchArtistAdapter;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import com.danghuong.musicapp.ui.bottomsheetforitem.BottomSheetForItemFragment;

import java.util.LinkedList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SearchArtistFrag extends BaseBindingFragment<SearchArtistRcBinding, SearchArtistViewModel> {
    private SearchArtistAdapter searchArtistAdapter;
    private String artistSong;
    List<MusicListItem> searchArtistItems;

    @Override
    public int getLayoutId() {
        return R.layout.search_artist_rc;
    }

    @Override
    protected Class<SearchArtistViewModel> getViewModel() {
        return SearchArtistViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        Timber.e("huongdt oncreateview");
        initData();
        initView();
    }

    private void initView() {
        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        initAdapter();
        initListener();
    }

    private void initData() {
        mainViewModel.liveEvent.observe(getViewLifecycleOwner(), o -> {
            if (((Event)o).getTypeEvent() == Common.SEARCHWITHNAMESONG) {
                artistSong = ((Event) o).getName();
            }
            Timber.e("huongdt artistsong: " + artistSong);
            searchArtistItems = new LinkedList<>();
            if (mainViewModel.musicListsMutableLiveData.getValue() != null && artistSong != null) {
                for (int i = 0; i < mainViewModel.musicListsMutableLiveData.getValue().size(); i++) {
                    if (mainViewModel.musicListsMutableLiveData.getValue().get(i).getSingerName().toUpperCase().trim().contains(artistSong.toUpperCase().trim())) {
                        searchArtistItems.add(mainViewModel.musicListsMutableLiveData.getValue().get(i));
                    }
                }
                if (searchArtistItems.size()==0) {
                    binding.tvNotFindAResult.setVisibility(View.VISIBLE);
                } else {
                    binding.tvNotFindAResult.setVisibility(View.GONE);
                }
                searchArtistAdapter.setSearchArtistItemList(searchArtistItems);
            }
        });
    }

    private void initListener() {
    }

    private void initAdapter() {
        searchArtistAdapter = new SearchArtistAdapter();
        binding.rcSearchArtist.setAdapter(searchArtistAdapter);
        searchArtistAdapter.setiSearchArtist((position, view) ->{
                if (view.getId() == R.id.iv_more) {
                    BottomSheetForItemFragment bottomSheetForItemFragment = new BottomSheetForItemFragment();
                    if (searchArtistItems.get(position)!=null) {
                        bottomSheetForItemFragment.setSongName(searchArtistItems.get(position).getMusic_name());
                    }
                    bottomSheetForItemFragment.show(getChildFragmentManager(), null);
                } else {
                    Bundle bundle = new Bundle();
                    if (searchArtistItems!=null) {
                        bundle.putString(Common.NAME_SONG, searchArtistItems.get(position).getMusic_name());
                        bundle.putString(Common.SINGER_SEARCH, searchArtistItems.get(position).getSingerName());
                    }
                    bundle.putInt(Common.POSITION, position);
                    int vitri = 2;
                    bundle.putInt(Common.VITRI, 2);
                    ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.play_music_fragment, bundle);
                }
            });

    }
}

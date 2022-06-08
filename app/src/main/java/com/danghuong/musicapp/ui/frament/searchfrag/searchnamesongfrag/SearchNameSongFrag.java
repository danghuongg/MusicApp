package com.danghuong.musicapp.ui.frament.searchfrag.searchnamesongfrag;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.Observer;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.databinding.SearchNameSongRcBinding;
import com.danghuong.musicapp.ui.adapter.SearchNameSongAdapter;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import com.danghuong.musicapp.ui.bottomsheetforitem.BottomSheetForItemFragment;

import java.util.LinkedList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class SearchNameSongFrag extends BaseBindingFragment<SearchNameSongRcBinding, SearchNameSongViewModel> {
    private String nameSong;
    private SearchNameSongAdapter searchNameSongAdapter;
    List<MusicListItem> searchNameSongItems;

    @Override
    public int getLayoutId() {
        return R.layout.search_name_song_rc;
    }

    @Override
    protected Class<SearchNameSongViewModel> getViewModel() {
        return SearchNameSongViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        initData();
        initView();

    }

    private void initView() {
        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        initAdapter();
        initListener();
    }

    private void initListener() {
    }

    private void initAdapter() {
        searchNameSongAdapter = new SearchNameSongAdapter();
        binding.rcSearchNameSong.setAdapter(searchNameSongAdapter);
        searchNameSongAdapter.setiSearchNameSong((position, view) ->{
            if (view.getId() == R.id.iv_more) {
                BottomSheetForItemFragment bottomSheetForItemFragment = new BottomSheetForItemFragment();
                if (searchNameSongItems.get(position)!=null) {
                    bottomSheetForItemFragment.setSongName(searchNameSongItems.get(position).getMusic_name());
                }
                bottomSheetForItemFragment.show(getChildFragmentManager(), null);
            } else {
                Bundle bundle = new Bundle();
                if (searchNameSongItems!=null) {
                    bundle.putString(Common.NAME_SONG, searchNameSongItems.get(position).getMusic_name());
                    bundle.putString(Common.SINGER_SEARCH, searchNameSongItems.get(position).getSingerName());
                }
                bundle.putInt(Common.POSITION, position);
                int vitri = 2;
                bundle.putInt(Common.VITRI, 2);
                ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.play_music_fragment, bundle);
            }
        });
    }

    private void initData() {
        mainViewModel.liveEvent.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if (((Event) o).getTypeEvent() == Common.SEARCHWITHNAMESONG) {
                    nameSong = ((Event) o).getName();

                    if (mainViewModel.musicListsMutableLiveData.getValue() != null) {
                        searchNameSongItems = new LinkedList<>();
                        for (int i = 0; i < mainViewModel.musicListsMutableLiveData.getValue().size(); i++) {
                            if (mainViewModel.musicListsMutableLiveData.getValue().get(i).getMusic_name().toUpperCase().trim().contains(nameSong.toUpperCase().trim())) {
                                searchNameSongItems.add(mainViewModel.musicListsMutableLiveData.getValue().get(i));
                            }
                        }

                        if (searchNameSongItems.size() == 0) {
                            binding.tvNotFindAResult.setVisibility(View.VISIBLE);
                        } else {
                            binding.tvNotFindAResult.setVisibility(View.GONE);
                        }
                        searchNameSongAdapter.setSearchNameSongItems(searchNameSongItems);
                    }
                }
            }
        });
    }
}

//    @Override
//    public void onAttach(@NonNull @NotNull Context context) {
//        super.onAttach(context);
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        EventBus.getDefault().unregister(this);
//    }
//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void event(Event event) {
//        if (event.getTypeEvent() == Common.SEARCHWITHNAMESONG) {
//            Timber.e("tunglt event.getName(): " + event.getName());
//            nameSong = event.getName();
//            List<MusicListItem> searchNameSongItems = new LinkedList<>();
////            mainViewModel.getSearche/**/dListWithNameSong(MainActivity.this,pictureAlbum.getPictureDetails());
//
//
//            for (int i = 0; i < mainViewModel.musicListsMutableLiveData.getValue().size(); i++) {
//                Timber.e("huong dt searchNameSongItemList " + mainViewModel.musicListsMutableLiveData.getValue().size());
//                if (mainViewModel.musicListsMutableLiveData.getValue().get(i).getMusic_name().toUpperCase().trim().contains(nameSong.toUpperCase().trim())) {
//                    searchNameSongItems.add(mainViewModel.musicListsMutableLiveData.getValue().get(i));
//                }
//            }
//            Timber.e("huong dt searchNameSongItems " + searchNameSongItems.size());
//            searchNameSongAdapter.setSearchNameSongItems(searchNameSongItems);
//        }


//    }
//}

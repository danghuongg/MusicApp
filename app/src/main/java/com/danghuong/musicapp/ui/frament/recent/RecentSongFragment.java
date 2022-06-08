package com.danghuong.musicapp.ui.frament.recent;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.lifecycle.Observer;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.data.model.CurrentSongItem;
import com.danghuong.musicapp.databinding.RecentFragmentRcBinding;
import com.danghuong.musicapp.ui.adapter.RecentAdapter;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import com.danghuong.musicapp.ui.bottomsheetforitem.BottomSheetForItemFragment;

import java.util.LinkedList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class RecentSongFragment extends BaseBindingFragment<RecentFragmentRcBinding, RecentSongViewModel>  {
    private RecentAdapter recentAdapter;
    List<CurrentSongItem> currentSongItemList=new LinkedList<>();

    @Override
    public int getLayoutId() {
        return R.layout.recent_fragment_rc;
    }

    @Override
    protected Class<RecentSongViewModel> getViewModel() {
        return RecentSongViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        setData();
        setView();
    }
    private void setView() {
        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        setAdapter();
        setOnClick();
    }
    private void setOnClick() { }
    @SuppressLint("NonConstantResourceId")
    private void setAdapter() {
        mainViewModel.getCurrentSongList();
        recentAdapter = new RecentAdapter();
        binding.rcCurrentSong.setAdapter(recentAdapter);
        recentAdapter.setiCurrentSong((position, view) -> {
            if(view.getId()==R.id.iv_more) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_recent, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch(menuItem.getItemId()){
                        case R.id.menu_unRecent:
                            viewModel.unRecentSong(currentSongItemList.get(position));
                            currentSongItemList.remove(position);
                            recentAdapter.setCurrentSongItemList(currentSongItemList);
                    }
                        return false;
                });
                popupMenu.show();
            }else{
                Bundle bundle = new Bundle();
                bundle.putString(Common.NAME_SONG, currentSongItemList.get(position).getMusic_name());
                Log.e("huongdt position ", String.valueOf(position));
                bundle.putString(Common.SINGER_SEARCH, currentSongItemList.get(position).getSinger());
                bundle.putInt(Common.POSITION, position);
                int vitri = 3;
                bundle.putInt(Common.VITRI, 3);
                ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.play_music_fragment, bundle);
            }
        });
    }
    private void setData() {
        mainViewModel.currentMutableLiveData.observe(getViewLifecycleOwner(), new Observer<List<CurrentSongItem>>() {
            @Override
            public void onChanged(List<CurrentSongItem> currentSongItems) {
                if(currentSongItems.size()>4){
                    for (int i = 0; i < 5; i++) {
                        currentSongItemList.add(currentSongItems.get(i));
                    }
                    currentSongItems.clear();
//                    recentAdapter.setCurrentSongItemList(currentSongItemList);

                }else {
                    currentSongItemList.clear();
                    currentSongItemList.addAll(currentSongItems);
//                    recentAdapter.setCurrentSongItemList(currentSongItemList);
                }
               recentAdapter.setCurrentSongItemList(currentSongItemList);

            }
        });
    }

}


package com.danghuong.musicapp.ui.frament.favoritefrag;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.PopupMenu;

import androidx.lifecycle.Observer;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.data.model.FavoriteItem;
import com.danghuong.musicapp.databinding.FavoriteFragmentRcBinding;
import com.danghuong.musicapp.ui.adapter.FavoriteAdapter;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteFragment extends BaseBindingFragment<FavoriteFragmentRcBinding, FavoriteViewModel> {
    private FavoriteAdapter favoriteAdapter;
    List<FavoriteItem> favoriteItemList= new LinkedList<>();

    @Override
    public int getLayoutId() {
        return R.layout.favorite_fragment_rc;
    }

    @Override
    protected Class<FavoriteViewModel> getViewModel() {
        return FavoriteViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        setData();
        initView();
    }

    private void setData() {
        mainViewModel.listFavoriteSongMLD.observe(getViewLifecycleOwner(), new Observer<List<FavoriteItem>>() {
            @Override
            public void onChanged(List<FavoriteItem> favoriteItems) {
                favoriteItemList=favoriteItems;
                favoriteAdapter.setFavoriteList(favoriteItemList);
//                favoriteItems.clear();

            }
        });
    }

    private void initView() {
        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.white));

        initAdapter();
    }
    @SuppressLint("NonConstantResourceId")
    private void initAdapter() {
        favoriteAdapter = new FavoriteAdapter();
        binding.rcFavoriteSong.setAdapter(favoriteAdapter);
        mainViewModel.getListFavoriteSong();
        favoriteAdapter.setiFavorite((position, view) -> {
            if(view.getId()==R.id.iv_more) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_favorite, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    switch(menuItem.getItemId()){
                        case R.id.menu_unFavorite:
                            viewModel.deleteFavoriteSong(favoriteItemList.get(position));
                            favoriteItemList.remove(position);
                            favoriteAdapter.setFavoriteList(favoriteItemList);
//                            EventBus.getDefault().post(new Event(Common.Unfavorite,Common.Unfavorite));
                    }
                    return false;
                });
                popupMenu.show();
            }else{
                Bundle bundle = new Bundle();
                bundle.putString(Common.NAME_SONG, favoriteItemList.get(position).getMusic_name());
                bundle.putString(Common.SINGER_SEARCH, favoriteItemList.get(position).getSinger());
                Log.e("huongdt favoriteItem ", favoriteItemList.get(position).getSinger());
                Log.e("huongdt favoriteItem ", favoriteItemList.get(position).getMusic_name());
                bundle.putInt(Common.POSITION, position);
                int vitri = 3;
                bundle.putInt(Common.VITRI, vitri);
                ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.play_music_fragment, bundle);
            }
        });
    }
}

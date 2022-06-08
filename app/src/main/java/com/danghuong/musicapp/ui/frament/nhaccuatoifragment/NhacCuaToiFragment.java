package com.danghuong.musicapp.ui.frament.nhaccuatoifragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.GravityCompat;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.databinding.MusicListFragmenRcBinding;
import com.danghuong.musicapp.ui.adapter.NhacCuaToiAdapter;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import com.danghuong.musicapp.ui.bottomsheetforitem.BottomSheetForItemFragment;
import com.danghuong.musicapp.ui.dialog.displaybottomsheetfrag.DisplayBottomSheetFragment;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class NhacCuaToiFragment extends BaseBindingFragment<MusicListFragmenRcBinding, NhacCuaToiViewModel> {
    private NhacCuaToiAdapter nhacCuaToiAdapter;
    private List<MusicListItem> musicList = new LinkedList<>();
    private int position = -1;
    private int sortAccordingToSong;
    private int random;


    @Override
    public int getLayoutId() {
        return R.layout.music_list_fragmen_rc;
    }

    @Override
    protected Class<NhacCuaToiViewModel> getViewModel() {
        return NhacCuaToiViewModel.class;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        observeData();
        initView();
    }

    private void observeData() {
        mainViewModel.musicListsMutableLiveData.observe(getViewLifecycleOwner(), musicListItems -> {
            musicList = musicListItems;
            nhacCuaToiAdapter.setMusicListItems(musicList);

        });
    }

    private void initView() {
        if (getArguments() != null) {
            sortAccordingToSong = getArguments().getInt(Common.SORTLIST);
        }
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mainViewModel.liveEvent.postValue(new Event(Common.HideBottomNav));
        }
        initAdapter();
        initListener();
    }

    private void initListener() {
        binding.ivCurrentSong.setOnClickListener(view -> {
            ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.current_song);
        });
        binding.ivFavoriteSong.setOnClickListener(view -> {
            ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.favorite_fragment);
        });
        binding.ivMenu.setOnClickListener(view -> {
            if (!binding.drawerLayout.isDrawerOpen(GravityCompat.START))
                binding.drawerLayout.openDrawer(GravityCompat.START);
            else binding.drawerLayout.openDrawer(GravityCompat.END);
        });
//        binding.ivOrderSong.setOnClickListener(view -> {
//            ViewHelper.preventTwoClick(view, 300);
//            OrderBottomSheet orderBottomSheet = new OrderBottomSheet();
//            orderBottomSheet.show(getChildFragmentManager(), null);
//        });
        binding.ivShowSong.setOnClickListener(view -> {
//            ViewHelper.preventTwoClick(view, 300);
            DisplayBottomSheetFragment displaybottomsheet = new DisplayBottomSheetFragment();
            displaybottomsheet.show(getChildFragmentManager(), null);
        });
        binding.tvPlayRandom.setOnClickListener(view -> {
            random = new Random().nextInt(musicList.size());
            int vitri = 0;
            Bundle bundle = new Bundle();
            bundle.putInt(Common.RANDOM, random);
            bundle.putInt(Common.VITRI, vitri);
            ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.play_music_fragment, bundle);
        });
        binding.btSearch.setOnClickListener(view -> {
            ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.search_fragment);
        });
        binding.ivSelectSong.setOnClickListener(view -> {
            ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.select_fragment);
        });
//        binding.ivPlayList.setOnClickListener(view -> {
//            ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.add_list_play_fragment);
//        });
    }

    private void initAdapter() {

        nhacCuaToiAdapter = new NhacCuaToiAdapter();
        binding.rcMusicList.setAdapter(nhacCuaToiAdapter);
        nhacCuaToiAdapter.setiMusicListItem((position, view) -> {
            if (view.getId() == R.id.iv_more) {
                BottomSheetForItemFragment bottomSheetForItemFragment = new BottomSheetForItemFragment();
                bottomSheetForItemFragment.setSongName(musicList.get(position).getMusic_name());
                bottomSheetForItemFragment.show(getChildFragmentManager(), null);

            } else {
                Bundle bundle = new Bundle();
                bundle.putString(Common.NAMESONG, musicList.get(position).getMusic_name());
                bundle.putString(Common.SINGER, musicList.get(position).getSingerName());
                bundle.putInt(Common.POSITION, position);
                int vitri = 1;
                bundle.putInt(Common.VITRI, vitri);
                ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.play_music_fragment, bundle);
            }
        });


        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                Timber.e("huongdt");
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.setting_fragment);
                        break;
                    case R.id.menu_priavte:
                        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Common.URL2));
                        startActivity(myIntent);
                        break;
//                    case R.id.menu_favorite:
//                        ((MainActivity)requireActivity()).navControllerMain.navigate(R.id.favorite_fragment);
//                        break;
                    case R.id.menu_setting:
                        ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.language_setting_frag);
                        break;
                }
                return true;
            }
        });
    }


    @Override
    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(Event event) {
        if (event.getTypeEvent() == Common.SORT_SONG) {
            sortAccordingToSong = event.getIntValue();
            if (sortAccordingToSong == 1) {
                Collections.sort(musicList, new Comparator<MusicListItem>() {
                    @Override
                    public int compare(MusicListItem musicListItem, MusicListItem t1) {
                        return musicListItem.getMusic_name().compareTo(t1.getMusic_name());
                    }
                });
            }
            if (sortAccordingToSong == 2) {
                Collections.sort(musicList, new Comparator<MusicListItem>() {
                    @Override
                    public int compare(MusicListItem musicListItem, MusicListItem t1) {
                        return musicListItem.getSingerName().compareTo(t1.getSingerName());
                    }
                });
            }
            if (sortAccordingToSong == 3) {
                Collections.sort(musicList, new Comparator<MusicListItem>() {
                    @Override
                    public int compare(MusicListItem musicListItem, MusicListItem t1) {
                        return musicListItem.getDateAdd().compareTo(t1.getDateAdd());
                    }
                });
            }
            nhacCuaToiAdapter.setMusicListItems(musicList);
        }
        if(event.getTypeEvent() == Common.ACTION_EXIT_FRAGMENT){
            if (event.getIntValue() == 2){
                  Timber.e("huongdt: event.getIntValue() "+event.getIntValue());
                ((MainActivity)requireActivity()).navControllerMain.navigate(R.id.play_music_fragment);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    }



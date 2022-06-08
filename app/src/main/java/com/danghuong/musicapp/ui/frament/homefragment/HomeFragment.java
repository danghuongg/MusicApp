package com.danghuong.musicapp.ui.frament.homefragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.Observer;
import androidx.viewpager2.widget.ViewPager2;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.data.model.CurrentSongItem;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.data.model.PlayOrPauseItem;
import com.danghuong.musicapp.databinding.HomeFragmentBinding;
import com.danghuong.musicapp.ui.adapter.HomeAdapter;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import com.danghuong.musicapp.ui.dialog.dialogexit.DiaLogExit;

import java.util.LinkedList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class HomeFragment extends BaseBindingFragment<HomeFragmentBinding, HomeViewModel> {
    private MediaPlayer mediaPlayer;
    private List<CurrentSongItem> currentSongItemList = new LinkedList<>();
    private List<MusicListItem> musicListItem = new LinkedList<>();
    private int postion = -1;
    private int play_or_pause = -1;
    private float currentPostion = -1;
    private float duration = -1;

    @Override
    public int getLayoutId() {
        return R.layout.home_fragment;
    }

    @Override
    protected Class<HomeViewModel> getViewModel() {
        return HomeViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
//        ((MainActivity) requireActivity()).navControllerMain.popBackStack(R.id.splash_screen, true);
        observeData();
        initView();
        initData();
        initObserve();
        HomeAdapter homeAdapter = new HomeAdapter(getChildFragmentManager(), getViewLifecycleOwner().getLifecycle());
        binding.viewPager.setAdapter(homeAdapter);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),onBackPressedCallback);
    }

    private void observeData() {
        if (mainViewModel.musicListsMutableLiveData.getValue() != null) {
            musicListItem = mainViewModel.musicListsMutableLiveData.getValue();
            Timber.e("tunglt size " + musicListItem.size());
        }
        mainViewModel.playOrPauseItemLiveEvent.observe(getViewLifecycleOwner(), o -> {
            Timber.e("tunglt 4");
            if (((PlayOrPauseItem) o).getTypeEvent() == Common.DATA_FROM_PLAYFRAG_TO_HOMEFRAG) {
                postion = ((PlayOrPauseItem) o).getPostion();

                Timber.e("tunglt postion " + postion);

                mediaPlayer = MediaPlayer.create(getActivity(),
                        Uri.parse(musicListItem.get(postion).getLink_music()));

            }
            play_or_pause = ((PlayOrPauseItem) o).getPlayOrPause();
            duration = ((PlayOrPauseItem) o).getDuration();

        });
    }

    private void initData() {
        Timber.e("tunglt play " + play_or_pause);


    }

    @Override
    public void onDetach() {
        super.onDetach();
        Timber.e("tunglt ondetach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public OnBackPressedCallback onBackPressedCallback =new OnBackPressedCallback(true) {
        @Override
        public void handleOnBackPressed() {
            DiaLogExit diaLogExit=new DiaLogExit();
            diaLogExit.show(getChildFragmentManager(), null);
        }
    };
    private void  initObserve(){
        mainViewModel.liveEvent.observe(getViewLifecycleOwner(), new Observer() {
            @Override
            public void onChanged(Object o) {
                if( ((Event)o).getTypeEvent()==Common.HideBottomNav){
                    binding.bottomNavigation.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initView() {

        if (mainViewModel.currentMutableLiveData.getValue() != null) {
            currentSongItemList = mainViewModel.currentMutableLiveData.getValue();
            Timber.e("tunglt recently " + currentSongItemList.size());
//            binding.tvSongName.setText(currentSongItemList.get(currentSongItemList.size() - 1).getMusic_name());
//            binding.tvArtistName.setText(currentSongItemList.get(currentSongItemList.size() - 1).getSinger());
        }


        initAdapter();
        initListener();
    }

    private void initListener() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.my_music) {
                binding.viewPager.setCurrentItem(0);
            } else {
                binding.viewPager.setCurrentItem(1);
            }
            return true;
        });
        binding.viewPager.setUserInputEnabled(false);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.bottomNavigation.getMenu().findItem(R.id.my_music).setChecked(true);
                        break;
                    default:
                        binding.bottomNavigation.getMenu().findItem(R.id.watch).setChecked(true);
                        break;
                }
            }
        });
    }

    private void initAdapter() {
        HomeAdapter homeAdapter = new HomeAdapter(getChildFragmentManager(), getViewLifecycleOwner().getLifecycle());
        binding.viewPager.setAdapter(homeAdapter);
    }



//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        DiaLogExit diaLogExit = new DiaLogExit();
//        diaLogExit.show(requireActivity().getSupportFragmentManager(), null);
//    }



}

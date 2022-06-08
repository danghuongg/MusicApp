package com.danghuong.musicapp.ui.bottomsheetforitem;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.databinding.BsForItemBinding;
import com.danghuong.musicapp.ui.base.BaseBottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class BottomSheetForItemFragment extends BaseBottomSheetDialogFragment<BsForItemBinding, BottomSheetForItemVM> {

    private String songName="";

    public void setSongName(String songName) {
        this.songName = songName;
    }

    @Override
    public int getLayoutId() {
        return R.layout.bs_for_item;
    }

    @Override
    protected Class<BottomSheetForItemVM> getViewModel() {
        return BottomSheetForItemVM.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        binding.tvSongName.setText(songName);
    }

}





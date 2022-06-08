package com.danghuong.musicapp.ui.dialog.dialogplayafter;

import android.os.Bundle;
import android.view.View;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.BsForPlayAfterBinding;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import com.danghuong.musicapp.ui.base.BaseBottomSheetDialogFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DialogPlayAfterFrag extends BaseBottomSheetDialogFragment<BsForPlayAfterBinding,DialogPlayAfterVM> {
    @Override
    public int getLayoutId() {
        return R.layout.bs_for_play_after;
    }

    @Override
    protected Class<DialogPlayAfterVM> getViewModel() {
        return DialogPlayAfterVM.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {

    }
}

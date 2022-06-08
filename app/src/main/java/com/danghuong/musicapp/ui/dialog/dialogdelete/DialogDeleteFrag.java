package com.danghuong.musicapp.ui.dialog.dialogdelete;

import android.os.Bundle;
import android.view.View;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.BsDeleteBinding;
import com.danghuong.musicapp.databinding.BsForPlayAfterBinding;
import com.danghuong.musicapp.ui.base.BaseBottomSheetDialogFragment;
import com.danghuong.musicapp.ui.dialog.dialogplayafter.DialogPlayAfterVM;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DialogDeleteFrag extends BaseBottomSheetDialogFragment<BsDeleteBinding,DialogDeleteVM> {
    @Override
    public int getLayoutId() {
        return R.layout.bs_delete;
    }

    @Override
    protected Class<DialogDeleteVM> getViewModel() {
        return DialogDeleteVM.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
            initListener();
    }

    private void initListener() {
        binding.tvDelete.setOnClickListener(view -> {
            dismiss();
        });
    }
}

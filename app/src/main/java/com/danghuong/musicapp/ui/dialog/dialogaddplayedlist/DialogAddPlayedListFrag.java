package com.danghuong.musicapp.ui.dialog.dialogaddplayedlist;

import android.os.Bundle;
import android.view.View;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.BsAddPlayListBinding;
import com.danghuong.musicapp.databinding.BsDeleteBinding;
import com.danghuong.musicapp.ui.base.BaseBottomSheetDialogFragment;
import com.danghuong.musicapp.ui.dialog.dialogdelete.DialogDeleteVM;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DialogAddPlayedListFrag extends BaseBottomSheetDialogFragment<BsAddPlayListBinding, DialogAddPlayedListVM> {
    @Override
    public int getLayoutId() {
        return R.layout.bs_add_play_list;
    }

    @Override
    protected Class<DialogAddPlayedListVM> getViewModel() {
        return DialogAddPlayedListVM.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {

    }
}

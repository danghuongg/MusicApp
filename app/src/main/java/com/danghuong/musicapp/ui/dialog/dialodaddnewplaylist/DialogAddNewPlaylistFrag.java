package com.danghuong.musicapp.ui.dialog.dialodaddnewplaylist;

import android.content.Context;
import android.graphics.Insets;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.DialogNameOfPlayListBinding;
import com.danghuong.musicapp.ui.base.BaseBottomSheetDialogFragment;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DialogAddNewPlaylistFrag extends BaseBottomSheetDialogFragment<DialogNameOfPlayListBinding, DialogAddNewPlayListVm> {
    @Override
    protected Class<DialogAddNewPlayListVm> getViewModel() {
        return DialogAddNewPlayListVm.class;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_name_of_play_list;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        binding.btCancel.setOnClickListener(view1 -> DialogAddNewPlaylistFrag.this.dismiss());
        binding.etEnterNameOfPlayList.setFocusable(true);
        binding.etEnterNameOfPlayList.requestFocus();
//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//
//        if ( getDialog() != null)
//        {//int height = ViewGroup.LayoutParams.MATCH_PARENT;
//            int width = ViewGroup.LayoutParams.MATCH_PARENT;
//            Point size = new Point();
//            //int width = (int)(size.x * 0.96);
//            int h = (int)(size.y * 1.57);
//            getDialog().getWindow().setLayout(width, h);
//        }
    }
}

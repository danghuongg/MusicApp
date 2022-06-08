package com.danghuong.musicapp.ui.dialog.dialogexit;

import android.graphics.ImageDecoder;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.common.LiveEvent;
import com.danghuong.musicapp.databinding.DialogExitBinding;
import com.danghuong.musicapp.ui.base.BaseBindingDialogFragment;

import org.greenrobot.eventbus.EventBus;

public class DiaLogExit extends BaseBindingDialogFragment<DialogExitBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.dialog_exit;
    }
    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
            binding.tvExit.setOnClickListener(view1 -> {
             requireActivity().finish();
            });
            binding.tvCancel.setOnClickListener(view1 ->{
                dismiss();
            });

    }
}

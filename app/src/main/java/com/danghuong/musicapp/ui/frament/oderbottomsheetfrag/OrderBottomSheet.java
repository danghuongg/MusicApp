package com.danghuong.musicapp.ui.frament.oderbottomsheetfrag;

import android.os.Bundle;
import android.view.View;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.databinding.BsArrgangeBinding;
import com.danghuong.musicapp.ui.base.BaseBottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OrderBottomSheet extends BaseBottomSheetDialogFragment<BsArrgangeBinding, OrderBottomSheetViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.bs_arrgange;
    }

    @Override
    protected Class<OrderBottomSheetViewModel> getViewModel() {
        return OrderBottomSheetViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        initListener();
    }

    private void initListener() {
        binding.tvSortWithDateAdd.setOnClickListener(view -> {
            EventBus.getDefault().post(new Event(Common.SORT_SONG,3));
            dismiss();
        });
    }
}

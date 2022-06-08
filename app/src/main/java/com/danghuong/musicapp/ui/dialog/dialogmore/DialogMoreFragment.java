package com.danghuong.musicapp.ui.dialog.dialogmore;

import android.os.Bundle;
import android.view.View;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.BsMoreBinding;
import com.danghuong.musicapp.ui.base.BaseBottomSheetDialogFragment;

public class DialogMoreFragment extends BaseBottomSheetDialogFragment<BsMoreBinding, DialogMoreVM> {
    @Override
    public int getLayoutId() {
        return R.layout.bs_more;
    }

    @Override
    protected Class<DialogMoreVM> getViewModel() {
        return DialogMoreVM.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {

    }
}

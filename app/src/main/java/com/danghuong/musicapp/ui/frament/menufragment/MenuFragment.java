package com.danghuong.musicapp.ui.frament.menufragment;

import android.os.Bundle;
import android.view.View;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.MenuFragmentBinding;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;

public class MenuFragment extends BaseBindingFragment<MenuFragmentBinding,MenuViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.menu_fragment;
    }

    @Override
    protected Class<MenuViewModel> getViewModel() {
        return MenuViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {

    }
}

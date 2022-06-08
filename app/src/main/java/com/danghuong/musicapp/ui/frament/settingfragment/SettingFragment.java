package com.danghuong.musicapp.ui.frament.settingfragment;

import android.os.Bundle;
import android.view.View;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.SettingFragmentBinding;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingFragment extends BaseBindingFragment<SettingFragmentBinding,SettingViewModel> {
    @Override
    public int getLayoutId() {
        return R.layout.setting_fragment;
    }

    @Override
    protected Class<SettingViewModel> getViewModel() {
        return SettingViewModel.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
            initListener();
    }

    private void initListener() {
            binding.ivBack.setOnClickListener(view -> {
                ((MainActivity)requireActivity()).navControllerMain.popBackStack(R.id.setting_fragment,true);
            });
            binding.tvAccountMi.setOnClickListener(view -> {
                ((MainActivity)requireActivity()).navControllerMain.navigate(R.id.account_mi_fragment);
            });
            binding.tvPasscode.setOnClickListener(view -> {
                ((MainActivity)requireActivity()).navControllerMain.navigate(R.id.account_mi_fragment);
            });
    }
}

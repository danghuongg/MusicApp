package com.danghuong.musicapp.ui.frament.settinglanguagefrag;

import android.os.Bundle;
import android.view.View;
import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.FragmentSettingBinding;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FragSettingLanguage extends BaseBindingFragment<FragmentSettingBinding,SettingLanguageVM> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected Class<SettingLanguageVM> getViewModel() {
        return SettingLanguageVM.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.white));
            binding.tvNgonNgu.setOnClickListener(view1 -> {
                ((MainActivity)requireActivity()).navControllerMain.navigate(R.id.select_languge_frag);
            });

    }
}

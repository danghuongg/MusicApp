package com.danghuong.musicapp.ui.changelanguage;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.databinding.FragSelectLanguageBinding;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;
import com.danghuong.musicapp.utils.LocaleUtils;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class ChangeLanguageFrag extends BaseBindingFragment<FragSelectLanguageBinding, ChangeLanguageVM> {

    @Override
    public int getLayoutId() {
        return R.layout.frag_select_language;
    }

    @Override
    protected Class<ChangeLanguageVM> getViewModel() {
        return ChangeLanguageVM.class;
    }

    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {
        binding.ivBack.setOnClickListener(v -> {
            ((MainActivity)requireActivity()).navControllerMain.popBackStack(R.id.select_languge_frag,true);
        });
        binding.tvVietnam.setOnClickListener(v -> {
            LocaleUtils.applyLocaleAndRestart(requireActivity(), Common.LANGUAGE_VN);
        });
        binding.ivVietnamFlag.setOnClickListener(view1 -> {
            LocaleUtils.applyLocaleAndRestart(requireActivity(), Common.LANGUAGE_VN);
        });
        binding.tvEnglish.setOnClickListener(v -> {
            LocaleUtils.applyLocaleAndRestart(requireActivity(), Common.LANGUAGE_EN);
        });
        binding.ivAmericanFlag.setOnClickListener(view1 -> {
            LocaleUtils.applyLocaleAndRestart(requireActivity(), Common.LANGUAGE_EN);
        });
    }
}

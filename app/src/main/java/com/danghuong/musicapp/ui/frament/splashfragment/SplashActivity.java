package com.danghuong.musicapp.ui.frament.splashfragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.RequiresApi;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.databinding.SplashScreenFragmentBinding;
import com.danghuong.musicapp.ui.base.BaseBindingActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SplashActivity extends BaseBindingActivity<SplashScreenFragmentBinding,SplashScreenViewModel>{

    @Override
    public int getLayoutId() {
        return R.layout.splash_screen_fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setupView(Bundle savedInstanceState) {
        getWindow().setStatusBarColor(this.getColor(R.color.purple));
        Handler handler =new Handler(Looper.getMainLooper());
        handler.postDelayed(SplashActivity.this::startActivity, 300);
    }

    @Override
    public void setupData() {

    }
    public void startActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected Class<SplashScreenViewModel> getViewModel() {
        return SplashScreenViewModel.class;
    }
}

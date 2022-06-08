//package com.danghuong.musicapp.ui.frament.splashfragment;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.view.View;
//import com.danghuong.musicapp.MainActivity;
//import com.danghuong.musicapp.R;
//import com.danghuong.musicapp.databinding.SplashScreenFragmentBinding;
//import com.danghuong.musicapp.ui.base.BaseBindingFragment;
//import dagger.hilt.android.AndroidEntryPoint;
//
//
//@AndroidEntryPoint
//class SplashScreenFragment extends BaseBindingFragment<SplashScreenFragmentBinding, SplashScreenViewModel> {
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.splash_screen_fragment;
//    }
//
//    @Override
//    protected Class<SplashScreenViewModel> getViewModel() {
//        return SplashScreenViewModel.class;
//    }
//
//    @Override
//    protected void onCreatedView(View view, Bundle savedInstanceState) {
//        initView();
//    }
//
//    @SuppressLint("ResourceAsColor")
//    private void initView() {
////        getActivity().getWindow().setStatusBarColor(getActivity().getColor(R.color.white));
//        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.purple));
//        Handler splashscreen = new Handler(Looper.getMainLooper());
//        splashscreen.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.home_fragment);
//
//            }
//        }, 500);
//    }
//}
//
//

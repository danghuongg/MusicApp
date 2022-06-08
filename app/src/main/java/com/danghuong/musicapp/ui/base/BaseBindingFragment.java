package com.danghuong.musicapp.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.danghuong.musicapp.MainViewModel;

import dagger.hilt.android.AndroidEntryPoint;


public abstract class BaseBindingFragment<B extends ViewDataBinding, T extends BaseViewModel> extends Fragment {
    public B binding;
    public T viewModel;
    public MainViewModel mainViewModel;
    private boolean loaded = false;
    public abstract int getLayoutId();
    protected abstract Class<T> getViewModel();
    protected abstract void onCreatedView(View view, Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (!loaded) {
            binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(getViewModel());
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        if (!needToKeepView()) {
            onCreatedView(view, savedInstanceState);
        } else {
            if (!loaded) {
                onCreatedView(view, savedInstanceState);
                loaded = true;
            }
        }
    }

    public boolean needToKeepView() {
        return false;
    }
}

package com.danghuong.musicapp.ui.base;

import androidx.lifecycle.ViewModel;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {
    // Thu thaapj tat ca context hien tai dang su dung de huy bo, tranh viec memoryleak
    public CompositeDisposable compositeDisposable= new CompositeDisposable();

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}

package com.danghuong.musicapp.ui.frament.recent;

import androidx.lifecycle.MutableLiveData;

import com.danghuong.musicapp.data.model.CurrentSongItem;
import com.danghuong.musicapp.data.repository.CurrentRepository;
import com.danghuong.musicapp.ui.base.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class RecentSongViewModel extends BaseViewModel {
    MutableLiveData<Boolean> deleteRecentSongMLD = new MutableLiveData<>();
    private final CurrentRepository currentRepository;
    @Inject
    public RecentSongViewModel(CurrentRepository currentRepository) {
        this.currentRepository = currentRepository;
    }
    public void unRecentSong(CurrentSongItem currentSongItem){
        currentRepository.deleteCurrentSongItem(currentSongItem).subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) { }
            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {deleteRecentSongMLD.postValue(aBoolean);}
            @Override
            public void onError(@NonNull Throwable e) { }
        });


    }


}

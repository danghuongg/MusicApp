package com.danghuong.musicapp.ui.frament.favoritefrag;

import androidx.lifecycle.MutableLiveData;

import com.danghuong.musicapp.data.model.FavoriteItem;
import com.danghuong.musicapp.data.repository.FavoriteRepository;
import com.danghuong.musicapp.ui.base.BaseViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import timber.log.Timber;

@HiltViewModel
public class FavoriteViewModel extends BaseViewModel {
    public MutableLiveData<Boolean> deleteFavoriteSongMLD = new MutableLiveData<>();
    private final FavoriteRepository favoriteRepository;
    @Inject
    public FavoriteViewModel(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public void deleteFavoriteSong(FavoriteItem favoriteItem) {
        favoriteRepository.deleteFavoriteSongItem(favoriteItem).subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                deleteFavoriteSongMLD.postValue(aBoolean);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e("Huongdt");
            }
        });
    }
}

package com.danghuong.musicapp;

import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.common.LiveEvent;
import com.danghuong.musicapp.common.SongEvent;
import com.danghuong.musicapp.data.model.AudioAlbum;
import com.danghuong.musicapp.data.model.AudioDetail;
import com.danghuong.musicapp.data.model.CurrentSongItem;
import com.danghuong.musicapp.data.model.FavoriteItem;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.data.model.PictureAlbum;
import com.danghuong.musicapp.data.model.PictureDetail;
import com.danghuong.musicapp.data.model.PlayOrPauseItem;
import com.danghuong.musicapp.data.model.SearchArtistItem;
import com.danghuong.musicapp.data.model.SearchNameSongItem;
import com.danghuong.musicapp.data.model.SelectCheckItem;
import com.danghuong.musicapp.data.model.SelectItem;
import com.danghuong.musicapp.data.repository.AudioRepository;
import com.danghuong.musicapp.data.repository.CurrentRepository;
import com.danghuong.musicapp.data.repository.FavoriteRepository;
import com.danghuong.musicapp.data.repository.PhotoRepository;
import com.danghuong.musicapp.data.repository.SearchRepository;
import com.danghuong.musicapp.ui.base.BaseViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import timber.log.Timber;

@HiltViewModel
public class MainViewModel extends BaseViewModel {
    public MutableLiveData<List<MusicListItem>> musicListsMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<MusicListItem> musicItemMutableLiveData = new MutableLiveData<>();
    public LiveEvent<PlayOrPauseItem> playOrPauseItemLiveEvent=new LiveEvent<>();
    public LiveEvent<Event> liveEvent =new LiveEvent<>();
    public LiveEvent<SongEvent> songLiveEvent=new LiveEvent<>();
    public MutableLiveData<List<CurrentSongItem>> currentMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<PictureAlbum> pictureAlbumMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<List<FavoriteItem>> listFavoriteSongMLD = new MutableLiveData<>();
    public MutableLiveData<List<SelectCheckItem>> selectedItemMLD = new MutableLiveData<>();
    public MutableLiveData<Boolean> favoriteCurrentSongMLD = new MutableLiveData<Boolean>();
    public MutableLiveData<Boolean> favoriteSongMLD = new MutableLiveData<>();
    public MutableLiveData<Boolean> deleteFavoriteSongMLD = new MutableLiveData<>();
    public MutableLiveData<Boolean> isPlayingSongMLD = new MutableLiveData<>();
    private final PhotoRepository photoRepository;
    private final AudioRepository audioRepository;
    private final FavoriteRepository favoriteRepository;
    private final CurrentRepository currentSongRepository;

    @Inject
    public MainViewModel(PhotoRepository photoRepository, AudioRepository audioRepository,
                         FavoriteRepository favoriteRepository,
                         CurrentRepository currentSongRepository) {
        this.photoRepository = photoRepository;
        this.audioRepository = audioRepository;
        this.favoriteRepository = favoriteRepository;
        this.currentSongRepository = currentSongRepository;
    }

    public void getPictureAlbum(AppCompatActivity mActivity) {
        photoRepository.getPhotoAlbums(mActivity, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).subscribe(new SingleObserver<PictureAlbum>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull PictureAlbum pictureAlbum) {
                pictureAlbumMutableLiveData.postValue(pictureAlbum);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e("HuongDT: " + e);
            }
        });
    }

    public void musícListList(AppCompatActivity mActivity, ArrayList<PictureDetail> pictureDetails) {
        audioRepository.getMusicitemList(mActivity, pictureDetails).subscribe(new SingleObserver<List<MusicListItem>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull List<MusicListItem> musicListItems) {
//                Collections.reverse(musicListItems);
                musicListsMutableLiveData.postValue(musicListItems);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
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


    public void insertFavoriteSong(FavoriteItem favoriteItem) {
        favoriteRepository.insertFavoriteSongItem(favoriteItem).subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                favoriteSongMLD.postValue(aBoolean);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e("Huongdt");
            }
        });
    }

    public void getCurrentSongList() {
        currentSongRepository.getCurrentSongFromDb().subscribe(new SingleObserver<List<CurrentSongItem>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull List<CurrentSongItem> currentSongItems) {
                Collections.reverse(currentSongItems);
                currentMutableLiveData.postValue(currentSongItems);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e("Huongdt" + e);
            }
        });
    }

    public void insertCurrentSong(CurrentSongItem currentSongItem) {
        currentSongRepository.insertCurrentSongItem(currentSongItem).subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                favoriteCurrentSongMLD.postValue(aBoolean);
            }
            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e("Huongdt");
            }
        });
    }

    public void getListFavoriteSong() {
        favoriteRepository.getListFavoriteSong().subscribe(new SingleObserver<List<FavoriteItem>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull List<FavoriteItem> favoriteItems) {
                Collections.reverse(favoriteItems);
                listFavoriteSongMLD.postValue(favoriteItems);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Timber.e("Huongdt");
            }
        });
    }


    public void getSelectedListItem(AppCompatActivity mActivity,ArrayList<PictureDetail> pictureDetails) {
        audioRepository.getSelectedList(mActivity,pictureDetails)
                .subscribe(new SingleObserver<List<SelectCheckItem>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull List<SelectCheckItem> selectItemList) {
//                        Collections.reverse(selectItemList);
                        selectedItemMLD.postValue(selectItemList);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                    }
                });
    }


}

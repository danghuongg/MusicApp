package com.danghuong.musicapp.data.repository;

import com.danghuong.musicapp.data.database.MusicDatabase;
import com.danghuong.musicapp.data.model.FavoriteItem;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FavoriteRepository {
    public MusicDatabase musicdb;
    @Inject
    public FavoriteRepository(MusicDatabase musicdb) {
        this.musicdb = musicdb;
    }

    public Single<List<FavoriteItem>> getListFavoriteSong() {
        return Single.fromCallable(() -> musicdb.dao().getListFavorite())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public boolean insertFavoriteSong(FavoriteItem favoriteItem) {
        try {
            musicdb.dao().insertFavorite(favoriteItem);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Single<Boolean> insertFavoriteSongItem(FavoriteItem favoriteItem) {
        return Single.fromCallable(() -> insertFavoriteSong(favoriteItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public boolean deleteFavorite(FavoriteItem favoriteItem) {
        try {
            musicdb.dao().deleteFavoriteSong(favoriteItem.getLink_music());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Single<Boolean> deleteFavoriteSongItem(FavoriteItem favoriteItem) {
        return Single.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return FavoriteRepository.this.deleteFavorite(favoriteItem);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public boolean delete(String linkmusic) {
        try {
            musicdb.dao().deleteFavoriteSong(linkmusic);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

package com.danghuong.musicapp.data.repository;

import com.danghuong.musicapp.data.database.MusicDatabase;
import com.danghuong.musicapp.data.model.CurrentSongItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CurrentRepository {
    MusicDatabase musicDb;

    @Inject
    public CurrentRepository(MusicDatabase musicDb) {
        this.musicDb = musicDb;
    }

    public Single<List<CurrentSongItem>> getCurrentSongFromDb() {
        return Single.fromCallable(musicDb.dao()::getListCurrentSong)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public boolean insertCurrentSong(CurrentSongItem currentSongItem) {
        try {
            musicDb.dao().insertCurrentSong(currentSongItem);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean deleteCurrentSong(CurrentSongItem currentSongItem) {
        try {
            musicDb.dao().deleteRecenteSong(currentSongItem.getLink_music());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Single<Boolean> insertCurrentSongItem(CurrentSongItem currentSongItem) {
        return Single.fromCallable(() -> insertCurrentSong(currentSongItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Single<Boolean> deleteCurrentSongItem(CurrentSongItem currentSongItem) {
        return Single.fromCallable(() -> deleteCurrentSong(currentSongItem))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

package com.danghuong.musicapp.data.repository;

import com.danghuong.musicapp.data.database.MusicDatabase;
import com.danghuong.musicapp.data.model.SearchedHistoryItem;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchRepository {
    public MusicDatabase musicDb;

    @Inject
    public SearchRepository(MusicDatabase musicDb) {
        this.musicDb = musicDb;
    }

    public Single<List<SearchedHistoryItem>> getListHistorySong() {
        return Single.fromCallable(() -> {
            return musicDb.dao().getListHistory();
        })
                .subscribeOn(Schedulers.io())      // đăng ký luồng
                .observeOn(AndroidSchedulers.mainThread()); // quan sát mỗi khi dữ liệu thay đổi
    }

    public Single<Boolean> insertSearchedHistory(SearchedHistoryItem searchedHistoryItem) {
        return Single.fromCallable(() -> {
            return inserSearchedHistory(searchedHistoryItem);
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public boolean inserSearchedHistory(SearchedHistoryItem searchedHistoryItem) {
        try {
            musicDb.dao().insertSearchedHistory(searchedHistoryItem);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Single<Boolean> deleteSearchedHistoryAll() {
        return Single.fromCallable(() -> {
            return deleteSearchedHistory();
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public boolean deleteSearchedHistory() {
        try {
            musicDb.dao().deleteSearchHistory();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

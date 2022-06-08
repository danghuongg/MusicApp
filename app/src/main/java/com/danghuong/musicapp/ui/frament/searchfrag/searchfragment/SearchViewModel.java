package com.danghuong.musicapp.ui.frament.searchfrag.searchfragment;

import androidx.lifecycle.MutableLiveData;

import com.danghuong.musicapp.data.model.SearchedHistoryItem;
import com.danghuong.musicapp.data.repository.SearchRepository;
import com.danghuong.musicapp.ui.base.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class SearchViewModel extends BaseViewModel {

    public MutableLiveData<List<SearchedHistoryItem>> searchedHistoryListMLD = new MutableLiveData<>();
    public MutableLiveData<Boolean> booleanInsertHistoryItem = new MutableLiveData<>();
    public MutableLiveData<Boolean> booleanDeleteHistoryItem = new MutableLiveData<>();
    private SearchRepository searchRepository;

    @Inject
    public SearchViewModel(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public void getsearchedHistoryList() {
        searchRepository.getListHistorySong().subscribe(new SingleObserver<List<SearchedHistoryItem>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull List<SearchedHistoryItem> searchedHistoryItems) {
                searchedHistoryListMLD.postValue(searchedHistoryItems);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }
        });
    }

    public void insertHistoryList(SearchedHistoryItem searchedHistoryItems) {
        searchRepository.insertSearchedHistory(searchedHistoryItems).subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                booleanInsertHistoryItem.postValue(aBoolean);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }
        });
    }

    public void removeAllHistoryList() {
        searchRepository.deleteSearchedHistoryAll().subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                booleanDeleteHistoryItem.postValue(aBoolean);
            }

            @Override
            public void onError(@NonNull Throwable e) {
            }
        });
    }
}

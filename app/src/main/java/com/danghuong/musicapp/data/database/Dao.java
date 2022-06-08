package com.danghuong.musicapp.data.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.danghuong.musicapp.data.model.CurrentSongItem;
import com.danghuong.musicapp.data.model.FavoriteItem;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.data.model.SearchedHistoryItem;

import java.util.List;

@androidx.room.Dao
public interface Dao {
    @Insert
    void insertFavorite(FavoriteItem favorite);

    @Query("SELECT*FROM FavoriteItem")
    List<FavoriteItem> getListFavorite();

    @Delete
    void deleteFavorite(FavoriteItem favorite);

    @Query("DELETE FROM FavoriteItem WHERE link_music = :linkmusic")
    void deleteFavoriteSong(String linkmusic);

    @Query("SELECT*FROM FavoriteItem where music_name=:musicName")
    List<FavoriteItem> checkName(String musicName);

    @Query("SELECT*FROM CurrentSongItem where music_name=:musicName")
    List<CurrentSongItem> checkCurrent(String musicName);

    @Query("DELETE FROM CurrentSongItem WHERE link_music = :linkmusic")
    void deleteRecenteSong(String linkmusic);


    @Insert
    void insertCurrentSong(CurrentSongItem currentSongItem);

    @Insert
    void insertSearchedHistory(SearchedHistoryItem searchedHistoryItem);

    @Delete
    void deleteSearchedHistory(SearchedHistoryItem historyItem);

    @Query("DELETE FROM SearchedHistoryItem")
    void deleteSearchHistory();

    @Query("SELECT*FROM SearchedHistoryItem")
    List<SearchedHistoryItem> getListHistory();

    @Query("SELECT*FROM CurrentSongItem")
    List<CurrentSongItem> getListCurrentSong();


}

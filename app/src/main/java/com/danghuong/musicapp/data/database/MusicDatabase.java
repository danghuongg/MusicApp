package com.danghuong.musicapp.data.database;

import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.danghuong.musicapp.data.model.CurrentSongItem;
import com.danghuong.musicapp.data.model.FavoriteItem;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.data.model.SearchArtistItem;
import com.danghuong.musicapp.data.model.SearchNameSongItem;
import com.danghuong.musicapp.data.model.SearchedHistoryItem;

@androidx.room.Database(entities = {FavoriteItem.class, CurrentSongItem.class,SearchedHistoryItem.class}, version = 1, exportSchema = false)
public abstract class MusicDatabase extends RoomDatabase {
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//            database.execSQL("CREATE TABLE IF NOT EXISTS `Pet` (`name` TEXT NOT NULL, PRIMARY KEY(`name`))");
//            database.execSQL("INSERT INTO Customers (CustomerName, ContactName, Address, City, PostalCode, Country)\n" +
//                    "VALUES ('Cardinal', 'Tom B. Erichsen', 'Skagen 21', 'Stavanger', '4006', 'Norway')");
        }
    };

    public abstract Dao dao();
}

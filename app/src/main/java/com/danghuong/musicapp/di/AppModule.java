package com.danghuong.musicapp.di;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.room.Room;

import com.danghuong.musicapp.data.database.Dao;
import com.danghuong.musicapp.data.database.MusicDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public SharedPreferences provideSharedPreference(Application context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    @Provides
    @Singleton
    public MusicDatabase provideRoomDb3(Application context) {
        return Room.databaseBuilder(context.getApplicationContext(), MusicDatabase.class,"DATABASE_NAME_1235")
                .addMigrations(MusicDatabase.MIGRATION_1_2)
                .build();
    }
    @Provides
    @Singleton
    public Dao provideMessageThreadDao3(MusicDatabase db) {
        return db.dao();
    }

}

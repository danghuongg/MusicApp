package com.danghuong.musicapp;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.utils.LocaleUtils;
import com.danghuong.musicapp.utils.MyDebugTree;

import dagger.hilt.android.HiltAndroidApp;
import timber.log.Timber;

@HiltAndroidApp
public class App extends MultiDexApplication {
    public static Context appContext;
    public static int WIDTH_SCREEN = 0;
    public static int HEIGHT_SCREEN = 0;
    public static int HEIGHT_STATUS_BAR = 0;
    private static App instance;
    public SharedPreferences mPrefs;

    public static Context getContext() {
        return appContext;
    }

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        initLog();
        appContext = getApplicationContext();
        WIDTH_SCREEN = getResources().getDisplayMetrics().widthPixels;
        HEIGHT_SCREEN = getResources().getDisplayMetrics().heightPixels;
        createChannelNotification();
//        Glide.get(getApplicationContext()).clearMemory();
//        new Thread(() -> Glide.get(getApplicationContext()).clearDiskCache()).start();
    }

    public void createChannelNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(Common.CHANNEL_ID, "Channel service",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void initLog() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new MyDebugTree());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocaleUtils.applyLocale(this);
    }
}

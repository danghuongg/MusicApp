package com.danghuong.musicapp.service;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.broadcastreceiver.ReceiverNotification;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.common.LiveEvent;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.ui.frament.playmusicfragment.PlayMusicFragment;

import org.greenrobot.eventbus.EventBus;

import timber.log.Timber;

public class MusicService extends Service {
    private static final int ACTION_PLAY = 24;
    private static final int ACTION_PAUSE = 25;
    private static final int ACTION_CLEAR = 26;
    boolean isPlaying = false;
    private NotificationManager notificationManager;
    private String musicName;
    private String musicLink;
    private MusicListItem musicListItem;
    private MediaPlayer mediaPlayer;
    private int actionToService;
    private int isExitPlayFragment = 1;

    @SuppressLint("TimberArgCount")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Timber.e("huongdt: onStartCommand");

        isPlaying = intent.getBooleanExtra(Common.isPlaying, false);
        Timber.e("huongdt: isplaying1%s", isPlaying);
        musicName = intent.getStringExtra(Common.MUSICNAME);
        Timber.e("huongdt: Common.MUSICNAME%s", musicName);
        actionToService = intent.getIntExtra(Common.ACTION_TO_SERVICE, 0);

        showNotification(musicName);
        hanleMusicAction(actionToService);
        return START_NOT_STICKY;
    }

    private void hanleMusicAction(int actionToService) {
        switch (actionToService) {
            case ACTION_PLAY:
                EventBus.getDefault().post(new Event(Common.ACTION_TO_FRAGMENT, Common.ACTION_PLAY_TO_FRAGMENT));
//                EventBus.getDefault().post(new Event(Common.ACTION_EXIT_FRAGMENT, isExitPlayFragment));
//                isExitPlayFragment=1;
                break;
            case ACTION_PAUSE:
                EventBus.getDefault().post(new Event(Common.ACTION_TO_FRAGMENT, Common.ACTION_PAUSE_TO_FRAGMENT));
//                EventBus.getDefault().post(new Event(Common.ACTION_EXIT_FRAGMENT, isExitPlayFragment));
//                isExitPlayFragment=1;
                break;
            case ACTION_CLEAR:
                stopSelf();
                EventBus.getDefault().post(new Event(Common.ACTION_TO_FRAGMENT, Common.ACTION_CLEAR_TO_FRAGMENT));
                break;
        }
    }


    @Override
    public void onCreate() {

        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        stopSelf();
    }


    @SuppressLint("UnspecifiedImmutableFlag")
    private PendingIntent getpendingIntentNotification(Context context, int action) {
        Intent playOrPauseIntent = new Intent(this, ReceiverNotification.class);
        playOrPauseIntent.putExtra(Common.ACTION_NOTIFICATION, action);
        playOrPauseIntent.putExtra(Common.MUSICNAME, musicName);
        playOrPauseIntent.putExtra(Common.isPlaying, isPlaying);
        return PendingIntent.getBroadcast(this, action, playOrPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNotification(String musicName) {
        Timber.e("huongdt: shownotification");
        Intent playOrPauseIntent = new Intent(this, PlayMusicFragment.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, playOrPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap songImage = BitmapFactory.decodeResource(getResources(), R.drawable.animation);
        RemoteViews remoteViewsNotification = new RemoteViews(getPackageName(), R.layout.notification);
        remoteViewsNotification.setTextViewText(R.id.tv_song_name, musicName);
        remoteViewsNotification.setImageViewBitmap(R.id.iv_songImage, songImage);
        if (isExitPlayFragment == 1) {
            remoteViewsNotification.setImageViewResource(R.id.iv_clear, R.drawable.ic_baseline_clear_24);
            remoteViewsNotification.setOnClickPendingIntent(R.id.iv_clear, getpendingIntentNotification(this, ACTION_CLEAR));
            isExitPlayFragment = 2;
        }

        Timber.e("huongdt: isplaying2 %s", isPlaying);
        remoteViewsNotification.setImageViewResource(R.id.iv_play, R.drawable.ic_baseline_pause_circle_outline_24);
        if (isPlaying) {
            isPlaying = false;
            remoteViewsNotification.setOnClickPendingIntent(R.id.iv_play, getpendingIntentNotification(this, ACTION_PAUSE));
            remoteViewsNotification.setImageViewResource(R.id.iv_play, R.drawable.ic_baseline_pause_circle_outline_24);


        } else {
            isPlaying = true;
            remoteViewsNotification.setOnClickPendingIntent(R.id.iv_play, getpendingIntentNotification(this, ACTION_PLAY));
            remoteViewsNotification.setImageViewResource(R.id.iv_play, R.drawable.ic_baseline_play_circle_outline_24);

        }

        Notification notification = new NotificationCompat.Builder(this, Common.CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.animation_circle)
                .setCustomContentView(remoteViewsNotification)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .setSound(null)
                .build();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Common.NOTIFICATION_ID_1, notification);
        startForeground(0, notification);

    }

}

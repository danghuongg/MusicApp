package com.danghuong.musicapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.broadcastreceiver.ReceiverNotification;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.data.model.PictureAlbum;
import com.danghuong.musicapp.databinding.ActivityMainBinding;
import com.danghuong.musicapp.service.MusicService;
import com.danghuong.musicapp.ui.base.BaseBindingActivity;
import com.danghuong.musicapp.ui.frament.playmusicfragment.PlayMusicFragment;

import java.io.Serializable;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class MainActivity extends BaseBindingActivity<ActivityMainBinding, MainViewModel> implements Serializable  {
//    private static final String ACTION_PLAY = "ACTION_PLAY";
//    private static final String ACTION_NEXT = "ACTION_NEXT";
//    private static final String ACTION_PREVIOUS = "ACTION_PREVIOUS";
    private static final String TAG = "Tag";
    public boolean isPlaying=false;
    public MusicListItem musicListItem;
    public NavController navControllerMain;
    public NavHostFragment navHostFragmentMain;
    public Intent intent;

    protected ActivityResultLauncher<String> requestPermissionLauncherWriteExternal =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    viewModel.getPictureAlbum(this);
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    String channel_Id = "Channel_id";
    private String nameSong;
    private NotificationManager notificationManager;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void setupView(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white, this.getTheme()));
        }
        navHostFragmentMain = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.my_nav_host_fragment_main);
        if (navHostFragmentMain != null) {
            navControllerMain = navHostFragmentMain.getNavController();
        }
        startService();
    }



    public void startService() {
        intent = new Intent(this, MusicService.class);
        viewModel.musicItemMutableLiveData.observe(this, new Observer<MusicListItem>() {
            @Override
            public void onChanged(MusicListItem musicListItem) {
                intent.putExtra(Common.MUSICNAME, musicListItem.getMusic_name());
                intent.putExtra(Common.MUSICLINK, musicListItem.getLink_music());

            }
        });
        viewModel.isPlayingSongMLD.observe(this, new Observer<Boolean>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(Boolean aBoolean) {
                isPlaying = aBoolean;
                if (isPlaying) {
                   intent.putExtra(Common.isPlaying, true);
                        startService(intent);
                }else {
                    intent.putExtra(Common.isPlaying, false);
                    startService(intent);
                }
            }
        });
        viewModel.liveEvent.observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
              if(((Event)o).getTypeEvent()==Common.CLEARTOACTIVITY){
                  stopService(intent);
              }
            }
        });
    }



    @Override
    public void setupData() {

        checkReadPermission();

        viewModel.pictureAlbumMutableLiveData.observe(this, new Observer<PictureAlbum>() {
            @Override
            public void onChanged(PictureAlbum pictureAlbum) {
                viewModel.musícListList(MainActivity.this, pictureAlbum.getPictureDetails());
                viewModel.getSelectedListItem(MainActivity.this, viewModel.pictureAlbumMutableLiveData.getValue().getPictureDetails());
            }
        });

    }


    @Override
    protected Class<MainViewModel> getViewModel() {
        return MainViewModel.class;
    }

    public void checkReadPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncherWriteExternal.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                viewModel.getPictureAlbum(this);
            }
        } else {
            viewModel.getPictureAlbum(this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(intent);


    }




}





























































































//
//    @SuppressLint("UnspecifiedImmutableFlag")
//    private PendingIntent onButtonNotificationClick(@IdRes int id) {
//        Intent intent = new Intent("ACTION_NOTIFICATION_BUTTON_CLICK");
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.putExtra("EXTRA_BUTTON_CLICKED", id);
//        return PendingIntent.getBroadcast(getApplicationContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//    }

//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public void showNotification(MusicListItem musicListItem) {
//        Intent playOrPauseIntent = new Intent(this, PlayMusicFragment.class);
//        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent=PendingIntent.getActivity(this,0,playOrPauseIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//        Bitmap songImage = BitmapFactory.decodeResource(getResources(), R.drawable.animation);
//        RemoteViews remoteViewsNotification = new RemoteViews(getPackageName(), R.layout.notification);
//        remoteViewsNotification.setTextViewText(R.id.tv_song_name, musicListItem.getMusic_name());
//        remoteViewsNotification.setImageViewBitmap(R.id.iv_songImage, songImage);
//        remoteViewsNotification.setImageViewResource(R.id.iv_clear, R.drawable.ic_baseline_clear_24);
//        remoteViewsNotification.setOnClickPendingIntent(R.id.iv_clear, getpendingIntentNotification(this, ACTION_CLEAR));
//        remoteViewsNotification.setImageViewResource(R.id.iv_play, R.drawable.ic_baseline_pause_circle_outline_24);
//        viewModel.liveEvent.observe(this, o -> {
//            if (((Event) o).getTypeEvent() == Common.PLAYORPAUSE) {
//                if (((Event) o).getIntValue() == Common.PLAY) {
//                    remoteViewsNotification.setOnClickPendingIntent(R.id.iv_play, getpendingIntentNotification(this, ACTION_PLAY));
//                    remoteViewsNotification.setImageViewResource(R.id.iv_play, R.drawable.ic_baseline_pause_circle_outline_24);
//                    Timber.e("huongdt play: ");
//                } else if (((Event) o).getIntValue() == Common.PAUSE) {
//
//                    remoteViewsNotification.setOnClickPendingIntent(R.id.iv_play, getpendingIntentNotification(this, ACTION_PAUSE));
//                    remoteViewsNotification.setImageViewResource(R.id.iv_play, R.drawable.ic_baseline_play_circle_outline_24);
//                    Timber.e("huongdt pause: ");
//                }
//            }
//        });
//
//        Notification notification = new NotificationCompat.Builder(this, Common.CHANNEL_ID)
//                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//                .setSmallIcon(R.drawable.animation_circle)
//                .setCustomContentView(remoteViewsNotification)
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(false)
//                .setSound(null)
//                .build();
//        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(Common.NOTIFICATION_ID_1, notification);
////
//    }
//    @SuppressLint("UnspecifiedImmutableFlag")
//    private PendingIntent onButtonNotificationClick(@IdRes int id) {
//        Intent intent = new Intent("ACTION_NOTIFICATION_BUTTON_CLICK");
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent.putExtra("EXTRA_BUTTON_CLICKED", id);
//        return PendingIntent.getBroadcast(getApplicationContext(), id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//    }

//    @SuppressLint("UnspecifiedImmutableFlag")
//    private PendingIntent getpendingIntentNotification(Context context, int action) {
//        Intent playOrPauseIntent = new Intent(this, ReceiverNotification.class);
//        playOrPauseIntent.putExtra(Common.ACTION_NOTIFICATION, action);
//        return PendingIntent.getBroadcast(this, action, playOrPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }

//    @SuppressLint("UnspecifiedImmutableFlag")
//    private PendingIntent getpendingIntentNotification(Context context, String action) {
//        Intent playOrPauseIntent = new Intent(this, ReceiverNotification.class).setAction(action);
//        playOrPauseIntent.putExtra(Common.ACTION_NOTIFICATION, action);
//        return PendingIntent.getBroadcast(this, action, playOrPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//    }
//
//
//    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getStringExtra("action_name");
//            switch (action) {
//                case ACTION_PREVIOUS:
//                    Toast.makeText(navHostFragmentMain.requireActivity(), "Báo thức đã hoàn thành", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//        }
//    };

//
//@RequiresApi(api = Build.VERSION_CODES.O)
//public void showNotification(MusicListItem musicListItem) {
//    Intent playOrPauseIntent = new Intent(this, PlayMusicFragment.class);
//    @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent=PendingIntent.getActivity(this,0,playOrPauseIntent,PendingIntent.FLAG_UPDATE_CURRENT);
//    MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(this, "tag");
//
//    mediaSessionCompat.setActive(true);
//    Notification notification = new NotificationCompat.Builder(this, Common.CHANNEL_ID)
//            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
//            .setSmallIcon(R.drawable.animation_circle)
////            .setCustomContentView(remoteViewsNotification)
//            .addAction(R.drawable.ic_baseline_fast_rewind_24,"previous",getpendingIntentNotification(this,ACTION_PREVIOUS))
//            .addAction(R.drawable.ic_baseline_play_arrow_24,"play",getpendingIntentNotification(this,ACTION_PLAY))
//            .addAction(R.drawable.ic_baseline_fast_forward_24,"next",getpendingIntentNotification(this,ACTION_NEXT))
//            .setStyle(new androidx.media.app.NotificationCompat.MediaStyle().setShowActionsInCompactView(0,1,2).setMediaSession(mediaSessionCompat.getSessionToken()))
//            .setContentIntent(pendingIntent)
//            .setAutoCancel(false)
//            .setSound(null)
//            .build();
//    notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//    notificationManager.notify(Common.NOTIFICATION_ID_1, notification);
////
//}
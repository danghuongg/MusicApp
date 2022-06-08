package com.danghuong.musicapp.broadcastreceiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.service.MusicService;

public class ReceiverNotification extends BroadcastReceiver {
//    private static final String ACTION_PLAY = "ACTION_PLAY";
//    private static final String ACTION_NEXT = "ACTION_NEXT";
//    private static final String ACTION_PREVIOUS = "ACTION_PREVIOUS";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onReceive(Context context, Intent intent) {
        int action = intent.getIntExtra(Common.ACTION_NOTIFICATION, 0);
        String  musicname = intent.getStringExtra(Common.MUSICNAME);
        Boolean  isPlaying = intent.getBooleanExtra(Common.isPlaying,false);
        Intent intent1 = new Intent(context, MusicService.class);
        intent1.putExtra(Common.ACTION_TO_SERVICE, action);
        intent1.putExtra(Common.MUSICNAME, musicname);
        intent1.putExtra(Common.isPlaying, isPlaying);
        context.startService(intent1);

    }
}

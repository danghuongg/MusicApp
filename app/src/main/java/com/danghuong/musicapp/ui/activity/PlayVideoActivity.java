package com.danghuong.musicapp.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.databinding.PlayVideoActivityBinding;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import dagger.hilt.android.AndroidEntryPoint;


public class PlayVideoActivity extends YouTubeBaseActivity {
    private String id;
    private int REQUEST_VIDEO = 1;
    private PlayVideoActivityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.play_video_activity);
        initData();
    }

    private void initData() {
        id = getIntent().getStringExtra(Common.VIDEO_ID);
        binding.ytPlayerView.initialize(Common.API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(id);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                if (youTubeInitializationResult.isUserRecoverableError()) {
                    youTubeInitializationResult.getErrorDialog(PlayVideoActivity.this, REQUEST_VIDEO);
                } else {
                    Toast.makeText(PlayVideoActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

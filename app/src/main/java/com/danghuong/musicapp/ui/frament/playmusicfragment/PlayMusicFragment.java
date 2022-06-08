package com.danghuong.musicapp.ui.frament.playmusicfragment;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;

import com.bumptech.glide.Glide;
import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.common.Event;
import com.danghuong.musicapp.data.model.CurrentSongItem;
import com.danghuong.musicapp.data.model.FavoriteItem;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.data.model.PlayOrPauseItem;
import com.danghuong.musicapp.databinding.PlayMusicFragmentBinding;
import com.danghuong.musicapp.service.MusicService;
import com.danghuong.musicapp.ui.base.BaseBindingFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class PlayMusicFragment extends BaseBindingFragment<PlayMusicFragmentBinding, PlayMusicViewModel> {
    private MediaPlayer mediaPlayer;
    private List<MusicListItem> musicList = new LinkedList<>();
    private int postion = -1;
    private boolean checkRandom = false;
    private boolean checkLoop = false;
    private boolean favorite = false;
    private List<FavoriteItem> favoriteItemList = new LinkedList<>();
    private List<CurrentSongItem> currentSongItemList = new LinkedList<>();
    private Calendar currentTime;
    private Calendar alertTime;
    private long setAlertTime;
    private int random = 1;
    private int checkRecent = 2;
    private int play_or_pause = 0;
    private int sentDataRandom = 0;
    private int sentDataRepeat = 0;
    private float currentProgress = 0;
    private int play_Or_Pause_to_Creat_Notification = 1;
    private boolean isPlaying;

    @Override
    public int getLayoutId() {
        return R.layout.play_music_fragment;
    }

    @Override
    protected Class<PlayMusicViewModel> getViewModel() {
        return PlayMusicViewModel.class;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreatedView(View view, Bundle savedInstanceState) {

        //set status bar
        ((MainActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.purple));
        setupData();
        initListener();
    }


    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setupData() {

//        mainViewModel.isPlayingSongMLD.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                isPlaying=aBoolean;
//            }
//        });

        mainViewModel.musicListsMutableLiveData.observe(getViewLifecycleOwner(), musicLists -> {
            musicList = musicLists;
            if (getArguments() != null) {
                random = getArguments().getInt(Common.VITRI);
                if (random == 0) {
                    postion = getArguments().getInt(Common.RANDOM);
                    binding.tvMusicmane.setText(musicList.get(postion).getMusic_name());
                    binding.tvSingerName.setText(musicList.get(postion).getSingerName());
                } else if (random == 1) {
                    postion = getArguments().getInt(Common.POSITION);
                    binding.tvMusicmane.setText(getArguments().getString(Common.NAMESONG));
                    binding.tvSingerName.setText(getArguments().getString(Common.SINGER));
                } else if (random == 2 || random == 3) {
                    for (int i = 0; i < musicList.size(); i++) {
                        if (musicList.get(i).getMusic_name().equals(getArguments().getString(Common.NAME_SONG))) {
                            postion = i;

                            binding.tvMusicmane.setText(getArguments().getString(Common.NAME_SONG));
                            binding.tvSingerName.setText(getArguments().getString(Common.SINGER_SEARCH));
                        }
                    }
                }
            }
            mediaPlayer = MediaPlayer.create(getActivity(),
                    Uri.parse(musicList.get(postion).getLink_music()));

            mediaPlayer.start();

//            if(postion>=1){
//                if(isPlaying){
//                    mediaPlayer
//                }
//            }

            MusicListItem musicListItem = getMusicListItem(postion);


            // gửi dữ liệu bài hát đang phát hay dừng để tạo icon cho notification
            if (mediaPlayer.isPlaying()) {
//                ((MainActivity) requireActivity()).showNotification(musicList.get(postion));
                mainViewModel.musicItemMutableLiveData.postValue(musicListItem);

                mainViewModel.isPlayingSongMLD.postValue(true);
            } else {
//                mainViewModel.liveEvent.postValue(new Event(Common.PLAYORPAUSE, Common.PAUSE));
                mainViewModel.isPlayingSongMLD.postValue(false);
//                ((MainActivity) requireActivity()).showNotification(musicList.get(postion));
            }
            play_or_pause = 1;
            setAnimationRotate();
            binding.tvEndingtime.setText(new SimpleDateFormat("mm:ss").format(mediaPlayer.getDuration()));
            updateTime();
            setSeekBar();


            // bài hát gần đây:
            CurrentSongItem currentSongItem = new CurrentSongItem(musicList.get(postion).getMusic_name(),
                    R.drawable.ic_baseline_phone_android_24,
                    musicList.get(postion).getSingerName(), musicList.get(postion).getSongImage(),
                    musicList.get(postion).getLink_music(), R.drawable.ic_baseline_more_24);
            if (mainViewModel.currentMutableLiveData.getValue() != null) {
                currentSongItemList = mainViewModel.currentMutableLiveData.getValue();
                for (int i = 0; i < currentSongItemList.size(); i++) {
                    if (musicList.get(postion).getLink_music().equals(currentSongItemList.get(i).getLink_music())) {
                        checkRecent = 3;

                        break;
                    }
                }
            }
            if (checkRecent != 3) {
                mainViewModel.insertCurrentSong(currentSongItem);
            }


            //getListOfFavoriteSongs
            mainViewModel.getListFavoriteSong();
            mainViewModel.listFavoriteSongMLD.observe(PlayMusicFragment.this.getViewLifecycleOwner(), new Observer<List<FavoriteItem>>() {
                @Override
                public void onChanged(List<FavoriteItem> favoriteItems) {
                    favoriteItemList = favoriteItems;
                    for (int j = 0; j < favoriteItemList.size(); j++) {
                        if (musicList.get(postion).getLink_music().equals(favoriteItemList.get(j).getLink_music())) {
                            Glide.with(PlayMusicFragment.this.requireActivity()).load(R.drawable.ic_baseline_favorite_24).into(binding.ivLove);
                            favorite = true;
                            break;
                        }
                    }
                }
            });
        });

    }

    @NonNull
    private MusicListItem getMusicListItem(int position) {
        MusicListItem musicListItem = new MusicListItem(musicList.get(position).getMusic_name(), musicList.get(position).getIconSong(),
                musicList.get(position).getIconMore(), musicList.get(position).getSingerName(), musicList.get(position).getSongImage(), musicList.get(position).getLink_music(), musicList.get(position).getNhaccuatoi(), musicList.get(position).getDateAdd());
        return musicListItem;

    }

    private void setAnimationRotate() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                binding.ivAnimCircle.animate().rotationBy(360).setDuration(8000).withEndAction(this)
                        .setInterpolator(new AccelerateDecelerateInterpolator()).start();
            }
        };
        binding.ivAnimCircle.animate().rotationBy(360).setDuration(8000).withEndAction(runnable)
                .setInterpolator(new AccelerateDecelerateInterpolator()).start();
    }

    @SuppressLint({"NonConstantResourceId", "SimpleDateFormat"})
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initListener() {
        binding.ivBackMusiclist.setOnClickListener(v -> {
            ((MainActivity) requireActivity()).navControllerMain.popBackStack(R.id.play_music_fragment, true);
        });
        binding.ivShare.setOnClickListener(view -> {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("audio/*");
            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri photoURI = FileProvider.getUriForFile(requireActivity(), requireActivity().getApplicationContext().getPackageName() + ".provider", new File(musicList.get(postion).getLink_music()));
            share.putExtra(Intent.EXTRA_STREAM, photoURI);
            startActivity(Intent.createChooser(share, "Share Sound File"));
        });
        binding.ivClock.setOnClickListener(view -> {
            currentTime = Calendar.getInstance();
            alertTime = Calendar.getInstance();
            TimePickerDialog timePickerDialog = new TimePickerDialog(((MainActivity) requireActivity()), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int i, int i1) {
                    alertTime.set(0, 0, 0, i, i1);
                    setAlertTime = (alertTime.getTimeInMillis() / (1000 * 60) - currentTime.getTimeInMillis() / (1000 * 60));
                    Toast.makeText(getActivity(), "Báo thức sẽ được bật " + setAlertTime + " phút tiếp theo", Toast.LENGTH_SHORT).show();
                    Log.d("tunglt", "setAlertTime: " + setAlertTime * 60);
                    CountDownTimer countDownTimer = new CountDownTimer((setAlertTime * 60), 1000) {
                        long time = 0;

                        @Override
                        public void onTick(long l) {
                            if (time == setAlertTime * 60) {
                                mediaPlayer.stop();
                                Toast.makeText(getActivity(), "Báo thức đã hoàn thành", Toast.LENGTH_SHORT).show();
                            }
                            time += 1000;
                        }

                        @Override
                        public void onFinish() {
                        }
                    };
                    countDownTimer.start();
                }
            }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), true);
            timePickerDialog.show();
        });
        binding.ivSwapCalls.setOnClickListener(view -> {
            Timber.e("repeat 1");
            if (!checkRandom) {
                Glide.with(this).load(R.drawable.ic_baseline_shuffle_24)
                        .into(binding.ivSwapCalls);
                Toast.makeText(getActivity(), Common.PLAYRANDOMSONG, Toast.LENGTH_SHORT).show();
                checkRandom = true;
                sentDataRandom = 1;
            } else {
                checkRandom = false;
                Toast.makeText(getActivity(), Common.PLAYCONTINUE, Toast.LENGTH_SHORT).show();
                Glide.with(this).load(R.drawable.ic_baseline_swap_calls_24)
                        .into(binding.ivSwapCalls);
                sentDataRandom = 0;
            }
        });
        binding.ivRepeat.setOnClickListener(view -> {
            if (!checkLoop) {
                Toast.makeText(getActivity(), Common.REPEATCIRCLE, Toast.LENGTH_SHORT).show();
                Timber.e("repeat 2");
                Glide.with(this).load(R.drawable.ic_baseline_repeat_one_24)
                        .into(binding.ivRepeat);

                checkLoop = true;
                sentDataRepeat = 1;
            } else {
                checkLoop = false;
                Toast.makeText(getActivity(), Common.NOREPEATCIRCLE, Toast.LENGTH_SHORT).show();
                Glide.with(this).load(R.drawable.ic_baseline_repeat_24)
                        .into(binding.ivRepeat);
                sentDataRepeat = 0;
            }
        });
        binding.ivBackMusiclist.setOnClickListener(v -> ((MainActivity) requireActivity())
                .navControllerMain.popBackStack(R.id.music_list, true));
        if (mediaPlayer != null) {
            binding.tvEndingtime.setText(new SimpleDateFormat("mm:ss")
                    .format(mediaPlayer.getDuration()));
        }
        binding.ivPlaySong.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    MusicListItem musicListItem = getMusicListItem(postion);
                    Glide.with(this).load(R.drawable.ic_baseline_play_circle_outline_24)
                            .into(binding.ivPlaySong);
                    mediaPlayer.pause();


                    // gửi dữ liệu đến notification
                    mainViewModel.musicItemMutableLiveData.postValue(musicListItem);
                    mainViewModel.isPlayingSongMLD.postValue(false);
                    //gửi dữ liệu để thay đổi iconNotification
                    mainViewModel.liveEvent.postValue(new Event(Common.PLAYORPAUSE, Common.PAUSE));
//                    ((MainActivity) requireActivity()).showNotification(musicList.get(postion));

                    play_or_pause = 0;
                    binding.ivAnimCircle.animate().cancel();
                    Timber.e("HuongDT pause");
                } else {
                    Glide.with(this).load(R.drawable.ic_baseline_pause_circle_outline_24)
                            .into(binding.ivPlaySong);
                    mediaPlayer.start();
                    MusicListItem musicListItem = getMusicListItem(postion);
                    mainViewModel.musicItemMutableLiveData.postValue(musicListItem);

                    //gửi dữ liệu để thay đổi iconNotification
                    mainViewModel.isPlayingSongMLD.postValue(true);


                    play_or_pause = 1;
                    setAnimationRotate();
                    setSeekBar();
                    updateTime();
                }
            }
        });
        binding.ivFastForward.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                if (!checkRandom) {
                    if (postion < musicList.size() - 1) {
                        forwardRewind(postion + 1);
                        postion += 1;


                    } else {
                        forwardRewind(0);
                        postion = 0;
                    }
                } else {
                    randomMusic();
                }
            }
        });
        binding.ivFastRewind.setOnClickListener(view -> {
            if (mediaPlayer != null) {
                if (!checkRandom) {
                    if (postion > 0) {
                        forwardRewind(postion - 1);
                        postion -= 1;

                    } else {
                        forwardRewind(musicList.size() - 1);
                        postion = musicList.size() - 1;

                    }
                } else {
                    randomMusic();
                }
            }
        });
        binding.ivMoreMusic.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(getActivity(), binding.ivMoreMusic);
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.upload_item:
                        Toast.makeText(getActivity(), "The song is loaded", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.favorite_item:
                        ((MainActivity) requireActivity()).navControllerMain.navigate(R.id.favorite_fragment);
                        break;
                }
                return false;
            });
            popup.show();
        });

        binding.ivLove.setOnClickListener(view -> {
            FavoriteItem favoriteSong = new FavoriteItem(musicList.get(postion).getMusic_name(), R.drawable.ic_baseline_phone_android_24,
                    musicList.get(postion).getSingerName(), musicList.get(postion).getSongImage(),
                    musicList.get(postion).getLink_music(), R.drawable.ic_baseline_more_24);
            Glide.with(this).load(!favorite ? R.drawable.ic_baseline_favorite_24 : R.drawable.ic_baseline_favorite_border_24).into(binding.ivLove);
            if (!favorite) {
                mainViewModel.insertFavoriteSong(favoriteSong);
                favorite = true;
            } else {
                //deleteFavoriteSong
                mainViewModel.deleteFavoriteSong(favoriteSong);

                favorite = false;
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void forwardRewind(int nextOrBack) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            Timber.e("forward");
            Glide.with(this).load(R.drawable.ic_baseline_play_circle_outline_24).into(binding.ivPlaySong);
        }
        mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(musicList.get(nextOrBack).getLink_music()));

//        ((MainActivity) requireActivity()).showNotification(musicList.get(nextOrBack));

        binding.tvMusicmane.setText(musicList.get(nextOrBack).getMusic_name());
        mediaPlayer.start();

        MusicListItem musicListItem = getMusicListItem(nextOrBack);
        mainViewModel.musicItemMutableLiveData.postValue(musicListItem);
        mainViewModel.isPlayingSongMLD.postValue(true);

        play_or_pause = 1;
        binding.tvEndingtime.setText(new SimpleDateFormat("mm:ss").format(mediaPlayer.getDuration()));
        updateTime();
        setSeekBar();
        Glide.with(this).load(R.drawable.ic_baseline_pause_circle_outline_24).into(binding.ivPlaySong);
    }

    private void setSeekBar() {
        if (mediaPlayer != null) {
            binding.sbListenMusic.setMax(mediaPlayer.getDuration());

        }
        binding.sbListenMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateTime() {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    binding.tvBiginningtime.setText(new SimpleDateFormat("mm:ss").format(mediaPlayer.getCurrentPosition()));
                    binding.sbListenMusic.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 100);
                }
            }
        }, 100);

        mediaPlayer.setOnCompletionListener(mP -> {
            if (!checkLoop) {
                if (!checkRandom) {
                    if (postion >= musicList.size() - 1) {
                        mediaPlayer.stop();
                        Toast.makeText(requireContext(), "Bạn đã nghe hết danh sách nhạc", Toast.LENGTH_SHORT).show();
                    } else {
                        postion += 1;
                        forwardRewind(postion);
                    }
                } else {
                    randomMusic();
                }
            } else {
                if (!checkRandom) {
                    postion = postion >= musicList.size() - 1 ? 0 : postion + 1;
                    forwardRewind(postion);
                } else {
                    randomMusic();
                }
            }
//            ((MainActivity) requireActivity()).showNotification(musicList.get(postion));
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void randomMusic() {
        int random = new Random().nextInt(musicList.size());
        if (postion != musicList.size() - 1) {
            postion = postion == random ? random + 1 : random;
        } else {
            postion = postion == random ? 0 : random;
        }
        forwardRewind(postion);
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    public void onAttach(@NonNull @NotNull Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Timber.e("tunglt ondetach");
        EventBus.getDefault().unregister(this);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mainViewModel.liveEvent.postValue(new Event(Common.PLAYORPAUSE));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.e("tunglt onPause");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(Event event) throws IOException {
        if (event.getTypeEvent() == Common.NOTIFICATION) {
            if (event.getIntValue() == Common.PLAY) {
                Toast.makeText(requireActivity(), "Play", Toast.LENGTH_SHORT).show();
                mainViewModel.liveEvent.postValue(new Event(Common.PLAYORPAUSE, Common.PLAY));
            } else {
                Toast.makeText(requireActivity(), "Pause", Toast.LENGTH_SHORT).show();
                mainViewModel.liveEvent.postValue(new Event(Common.PLAYORPAUSE, Common.PAUSE));
            }
//            ((MainActivity) requireActivity()).showNotification(musicList.get(postion));
        }
        if (event.getTypeEvent() == Common.Unfavorite) {
            if (event.getIntValue() == Common.Unfavorite) {
                Glide.with(binding.ivLove).load(R.drawable.ic_baseline_favorite_border_24).into(binding.ivLove);
//                mainViewModel.deleteFavoriteSong(musicList.get(postion).getLink_music());
            }
        }
        if (event.getTypeEvent() == Common.ACTION_TO_FRAGMENT) {
            if (event.getIntValue() == Common.ACTION_PLAY_TO_FRAGMENT) {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    Glide.with(this).load(R.drawable.ic_baseline_pause_circle_outline_24).into(binding.ivPlaySong);
                }
            } else if (event.getIntValue() == Common.ACTION_PAUSE_TO_FRAGMENT) {
                mediaPlayer.pause();
                Glide.with(this).load(R.drawable.ic_baseline_play_circle_outline_24).into(binding.ivPlaySong);
//                mainViewModel.isPlayingSongMLD.postValue(false);
            } else if (event.getIntValue() == Common.ACTION_CLEAR_TO_FRAGMENT) {
//                Intent intent =new Intent(getActivity(), MusicService.class);
//                requireActivity().stopService(intent);
//                Toast.makeText(getActivity(),"ACTION_CLEAR_TO_FRAGMENT SUCCESS",Toast.LENGTH_SHORT);
                mediaPlayer.pause();
                mainViewModel.isPlayingSongMLD.postValue(false);
                ((MainActivity) requireActivity()).navControllerMain.popBackStack(R.id.play_music_fragment, true);

            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}

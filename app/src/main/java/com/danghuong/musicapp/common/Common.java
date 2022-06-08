package com.danghuong.musicapp.common;

import android.support.v4.media.session.PlaybackStateCompat;

public class Common {
    public final static String API_KEY = "AIzaSyBqyw3ALAU_hD_i8Cq9b6jlxzIjEZNlqq0";
    public final static String KEY_VIDEO = "PLC2m8ClY3e8s5wZTObNk5ZESrxi6ZuovU";
    public final static String URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=PLC2m8ClY3e8s5wZTObNk5ZESrxi6ZuovU&key=AIzaSyBqyw3ALAU_hD_i8Cq9b6jlxzIjEZNlqq0&maxResults=50";
    public final static String URL2 = "https://stackoverflow.com/questions/2201917/how-can-i-open-a-url-in-androids-web-browser-from-my-application";
    public static final String POSITION = "POSITION";
    public static final String NAMESONG = "namesong";
    public static final String SINGER = "SINGER";
    public static final String NAME_SONG = "NAME_SONG";
    public static final String SINGER_SEARCH = "SINGER_SEARCH";
    public static final String VIDEO_ID = "Video_Id";
    public static final String RANDOM = "random";
    public static final String VITRI = "vitri";
    public static final String SORTLIST = "sortList";
    public static final String PLAYRANDOMSONG = "Bài hát sẽ được lặp lại ngẫu nhiên";
    public static final String PLAYCONTINUE = "Bài hát sẽ được chạy liên tục";
    public static final String REPEATCIRCLE = "Vòng bài hát sẽ được lặp lại";
    public static final String NOREPEATCIRCLE = "Vòng bài hát sẽ không được lặp lại";
    public static final String PREF_SETTING_LANGUAGE = "pref_setting_language";
    public static final String LANGUAGE_EN = "en";
    public static final String LANGUAGE_VN = "vi";

    //eventBus
    public static final int SORT_SONG = 1;
    public static final int SEARCHWITHNAMESONG = 2;
    public static final int Unfavorite = 15;
    public static final int ACTION_PLAY_TO_FRAGMENT = 27;
    public static final int ACTION_PAUSE_TO_FRAGMENT = 28;
    public static final int ACTION_CLEAR_TO_FRAGMENT = 29;

    //setHintEditext
    public static final String HINTFOROPINION = "Hãy cho chúng tôi biết làm thế nào để cải thiển MIUI";
    public static final String HINTFORPROBLEM = "Mô tả vấn đề bạn gặp phải ở đây." + "\n" +
            "1. Bạn đã gặp vấn đề trên ứng dụng hoặc trên trang nào?+\n" + " " +
            "2. Sau khi bạn thực hiện những hành động nào+\n+" + "thì vấn đề xuất hiện? +\n" +
            "3. Cung cấp cho chúng tôi thông tin bổ sung có +\n" + "thể giúp chúng tôi khắc phục vấn đề.";

    public static final int DATA_FROM_PLAYFRAG_TO_HOMEFRAG = 3;

//RequestCode
    public static final int REQUEST_CODE_1 = 1;
    public static final String SONG_ID = "SONG_ID";
    public static final String PRIORITY_SONG_ID = "PRIORITY_SONG_ID";
    public static final int NOTIFICATION = 6;
    public static final int NOTIFICATION_SONG = 5;
    public static final int NOTIFICATION_ID_1 = 7;
    public static final int EXTRA_BUTTON_CLICKED = 8;
    public static final int PLAY = 10;
    public static final int PAUSE = 11;
    public static final int PLAYORPAUSE = 9;
    public static final int HideBottomNav = 12;
    public static final int ACTION_TO_FRAGMENT = 13;
    public static final int CLEARTOACTIVITY = 16;
    public static final int ACTION_EXIT_FRAGMENT = 17;
    public static final String ACTION_TO_ACTIVITY = "ACTION_TO_ACTIVITY";
    public static final String CHANNEL_ID = "CHANNEL_ID";
    public static final String ACTION_PLAY_OR_PAUSE = "ACTION_PLAY_OR_PAUSE";
    public static final String ACTION_NOTIFICATION = "ACTION_NOTIFICATION";
    public static final String SONG = "SONG";
    public static final String isPlaying = "isPlaying";
    public static final String MUSICNAME = "MUSICNAME";
    public static final String MUSICSINGER = "MUSICSINGER";
    public static final String MUSICLINK = "MUSICLINK";
    public static final String ACTION_TO_SERVICE = "ACTION_TO_SERVICE";


    // Player playing statuses
    int PLAYING = PlaybackStateCompat.STATE_PLAYING;
    int PAUSED = PlaybackStateCompat.STATE_PAUSED;
    int RESUMED = PlaybackStateCompat.STATE_NONE;


}

package com.danghuong.musicapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
public class SearchNameSongItem {

    private Long iTopic;
    private String music_name;
    private int iconSong;
    private int iconMore;
    private String singerName;
    private String songImage;
    private String link_music;
    private String nhaccuatoi;

    public SearchNameSongItem(String music_name, int iconSong, int iconMore, String singerName, String songImage, String link_music, String nhaccuatoi) {
        this.music_name = music_name;
        this.iconSong = iconSong;
        this.iconMore = iconMore;
        this.singerName = singerName;
        this.songImage = songImage;
        this.link_music = link_music;
        this.nhaccuatoi = nhaccuatoi;
    }

    public Long getITopic() {
        return iTopic;
    }
    public void setITopic(Long iTopic ) {
        this.iTopic = iTopic;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public int getIconSong() {
        return iconSong;
    }

    public void setIconSong(int iconSong) {
        this.iconSong = iconSong;
    }

    public int getIconMore() {
        return iconMore;
    }

    public void setIconMore(int iconMore) {
        this.iconMore = iconMore;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public String getLink_music() {
        return link_music;
    }

    public void setLink_music(String link_music) {
        this.link_music = link_music;
    }

    public String getNhaccuatoi() {
        return nhaccuatoi;
    }

    public void setNhaccuatoi(String nhaccuatoi) {
        this.nhaccuatoi = nhaccuatoi;
    }
}

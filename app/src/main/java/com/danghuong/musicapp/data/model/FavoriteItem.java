package com.danghuong.musicapp.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavoriteItem {

    @PrimaryKey(autoGenerate = true)
    private Long iTopic;
    private int iconSong;
    private String music_name;
    private String singer;
    private String image;
    private String link_music;
    private int icon;


    public FavoriteItem(String music_name, int iconSong, String singer, String image, String link_music, int icon) {
        this.music_name = music_name;
        this.iconSong = iconSong;
        this.singer = singer;
        this.image = image;
        this.link_music = link_music;
        this.icon=icon;
    }

    public Long getITopic() {
        return iTopic;
    }
    public void setITopic(Long iTopic ) {
        this.iTopic = iTopic;
    }

    public int getIconSong() {
        return iconSong;
    }

    public void setIconSong(int iconSong) {
        this.iconSong = iconSong;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getSinger() {
        return singer;
    }
    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink_music() {
        return link_music;
    }

    public void setLink_music(String link_music) {
        this.link_music = link_music;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

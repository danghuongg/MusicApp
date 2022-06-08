package com.danghuong.musicapp.data.model;

public class SelectItem {
    private Long iTopic;
    private int iconSong;
    private String music_name;
    private String singer;
    private String serial;
    private String link_music;
    private String nhaccuatoi;
    private int selectImage;


    public SelectItem(String music_name, int iconSong, String singer, String serial, String link_music, String nhaccuatoi, int selectImage) {
        this.music_name = music_name;
        this.iconSong = iconSong;
        this.singer = singer;
        this.serial = serial;
        this.link_music = link_music;
        this.nhaccuatoi = nhaccuatoi;
        this.selectImage = selectImage;
    }


    public Long getITopic() {
        return iTopic;
    }

    public void setITopic(Long iTopic) {
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

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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

    public int getSelectImage() {
        return selectImage;
    }

    public void setSelectImage(int selectImage) {
        this.selectImage = selectImage;
    }

}

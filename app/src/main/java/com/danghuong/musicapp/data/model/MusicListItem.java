package com.danghuong.musicapp.data.model;

public class MusicListItem {
    private int idNuCuoiHongTopic;
    private String music_name;
    private int iconSong;
    private int iconMore;
    private String singerName;
    private String songImage;
    private String link_music;
    private String nhaccuatoi;
    private String dateAdd;

    public MusicListItem(String music_name, int iconSong, int iconMore, String singerName, String songImage, String link_music, String nhaccuatoi, String dateAdd) {
        this.music_name = music_name;
        this.iconSong = iconSong;
        this.iconMore = iconMore;
        this.singerName = singerName;
        this.songImage = songImage;
        this.link_music = link_music;
        this.nhaccuatoi = nhaccuatoi;
        this.dateAdd = dateAdd;
    }

    public String getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(String dateAdd) {
        this.dateAdd = dateAdd;
    }

    public int getIdNuCuoiHongTopic() {
        return idNuCuoiHongTopic;
    }

    public void setIdNuCuoiHongTopic(int idNuCuoiHongTopic) {
        this.idNuCuoiHongTopic = idNuCuoiHongTopic;
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

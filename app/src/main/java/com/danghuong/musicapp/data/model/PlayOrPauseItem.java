package com.danghuong.musicapp.data.model;

public class PlayOrPauseItem {
    private int typeEvent;
    private int postion;
    private float getProgress;
    private String songName;
    private String artist;
    private int playOrPause;
    private int checkRandom;
    private int checkRepeat;
    private float duration;

    public PlayOrPauseItem() {
    }

    public PlayOrPauseItem(int typeEvent, int postion, float getProgress, String songName, String artist, int playOrPause, int checkRandom, int checkRepeat,float duration) {
        this.typeEvent = typeEvent;
        this.postion = postion;
        this.getProgress = getProgress;
        this.songName = songName;
        this.artist = artist;
        this.playOrPause = playOrPause;
        this.checkRandom = checkRandom;
        this.checkRepeat = checkRepeat;
        this.duration=duration;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public float getGetProgress() {
        return getProgress;
    }

    public void setGetProgress(float getProgress) {
        this.getProgress = getProgress;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getPlayOrPause() {
        return playOrPause;
    }

    public void setPlayOrPause(int playOrPause) {
        this.playOrPause = playOrPause;
    }

    public int getCheckRandom() {
        return checkRandom;
    }

    public void setCheckRandom(int checkRandom) {
        this.checkRandom = checkRandom;
    }

    public int getCheckRepeat() {
        return checkRepeat;
    }

    public void setCheckRepeat(int checkRepeat) {
        this.checkRepeat = checkRepeat;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
}

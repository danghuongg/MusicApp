package com.danghuong.musicapp.common;

public class SongEvent {
    private int typeEvent;
    private String path ;
    private String title;
    private int duration ;
    private String composer;

    public SongEvent() {
    }

    public SongEvent(int typeEvent,String path, String title, int duration, String composer) {
        this.typeEvent=typeEvent;
        this.path = path;
        this.title = title;
        this.duration = duration;
        this.composer = composer;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getComposer() {
        return composer;
    }

    public void setComposer(String composer) {
        this.composer = composer;
    }
}

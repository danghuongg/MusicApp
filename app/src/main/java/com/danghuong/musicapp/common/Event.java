package com.danghuong.musicapp.common;

import android.text.Editable;

public class Event {

    private int typeEvent;
    private int intValue;
    private String name;
    private Boolean isPlay;


    public Event(int typeEvent, int intValue) {
        this.typeEvent = typeEvent;
        this.intValue = intValue;
    }

    public Event(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public Event(int typeEvent, String name) {
        this.typeEvent = typeEvent;
        this.name = name;
    }

    public Event(int typeEvent, Boolean isPlay) {
        this.typeEvent = typeEvent;
        this.isPlay = isPlay;
    }

    public Boolean getPlay() {
        return isPlay;
    }

    public void setPlay(Boolean play) {
        isPlay = play;
    }

    public int getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(int typeEvent) {
        this.typeEvent = typeEvent;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

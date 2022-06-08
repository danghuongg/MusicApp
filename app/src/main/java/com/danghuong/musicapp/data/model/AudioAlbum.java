package com.danghuong.musicapp.data.model;

import java.util.ArrayList;

public class AudioAlbum {
    private long bucketId;
    private String bucketName;
    private boolean isTheAlbumsCreated;
    private ArrayList<AudioDetail> audioDetail = new ArrayList<>();
    private boolean isSelect;
    public AudioAlbum() {
    }

    public AudioAlbum(long bucketId, String bucketName, boolean isTheAlbumsCreated, ArrayList<AudioDetail> audioDetail, boolean isSelect) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.isTheAlbumsCreated = isTheAlbumsCreated;
        this.audioDetail = audioDetail;
        this.isSelect = isSelect;
    }

    public AudioAlbum(ArrayList<AudioDetail> audioDetail) {
        this.audioDetail = audioDetail;
    }

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public boolean isTheAlbumsCreated() {
        return isTheAlbumsCreated;
    }

    public void setTheAlbumsCreated(boolean theAlbumsCreated) {
        isTheAlbumsCreated = theAlbumsCreated;
    }

    public ArrayList<AudioDetail> getAudioDetail() {
        return audioDetail;
    }

    public void setAudioDetail(ArrayList<AudioDetail> audioDetail) {
        this.audioDetail = audioDetail;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}


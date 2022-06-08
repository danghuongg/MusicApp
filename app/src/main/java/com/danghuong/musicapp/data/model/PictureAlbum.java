package com.danghuong.musicapp.data.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class PictureAlbum {
    private long bucketId;
    private String bucketName;
    private long dateLastModified;
    private String imgThumb;
    private int totalCount = 0;
    private boolean video;
    private int totalCountVideo = 0;
    private int totalCountImage = 0;
    private boolean isTheAlbumsCreated;
    private ArrayList<PictureDetail> pictureDetails = new ArrayList<>();
    private boolean isSelect;

    public PictureAlbum() {
    }

    public PictureAlbum(long bucketId, @NonNull String bucketName, long dateLastModified, @NonNull String imgThumb, int totalCount, boolean video, int totalCountVideo, int totalCountImage, boolean isTheAlbumsCreated, @NonNull ArrayList<PictureDetail> pictureDetails, boolean isSelect) {
        this.bucketId = bucketId;
        this.bucketName = bucketName;
        this.dateLastModified = dateLastModified;
        this.imgThumb = imgThumb;
        this.totalCount = totalCount;
        this.video = video;
        this.totalCountVideo = totalCountVideo;
        this.totalCountImage = totalCountImage;
        this.isTheAlbumsCreated = isTheAlbumsCreated;
        this.pictureDetails = pictureDetails;
        this.isSelect = isSelect;
    }

    public long getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(long dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    @NonNull
    public ArrayList<PictureDetail> getPictureDetails() {
        return pictureDetails;
    }

    public void setPictureDetails(@NonNull ArrayList<PictureDetail> pictureDetails) {
        this.pictureDetails = pictureDetails;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public long getBucketId() {
        return bucketId;
    }

    public void setBucketId(long bucketId) {
        this.bucketId = bucketId;
    }

    @NonNull
    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(@NonNull String bucketName) {
        this.bucketName = bucketName;
    }

    @NonNull
    public String getImgThumb() {
        return imgThumb;
    }

    public void setImgThumb(@NonNull String imgThumb) {
        this.imgThumb = imgThumb;
    }


    public int getTotalCountVideo() {
        return totalCountVideo;
    }

    public void setTotalCountVideo(int totalCountVideo) {
        this.totalCountVideo = totalCountVideo;
    }

    public int getTotalCountImage() {
        return totalCountImage;
    }

    public void setTotalCountImage(int totalCountImage) {
        this.totalCountImage = totalCountImage;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isTheAlbumsCreated() {
        return isTheAlbumsCreated;
    }

    public void setTheAlbumsCreated(boolean theAlbumsCreated) {
        isTheAlbumsCreated = theAlbumsCreated;
    }
}

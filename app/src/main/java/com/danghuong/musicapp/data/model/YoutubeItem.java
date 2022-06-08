package com.danghuong.musicapp.data.model;

public class YoutubeItem {
    private String title;
    private String videoId;
    private String thumbnails;
    private int channelImage;
    private int icon;

    public YoutubeItem(String title, String videoId, String thumbnails, int channelImage, int icon) {
        this.title = title;
        this.videoId = videoId;
        this.thumbnails = thumbnails;
        this.channelImage = channelImage;
        this.icon=icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }

    public int getChannelImage() {
        return channelImage;
    }

    public void setChannelImage(int channelImage) {
        this.channelImage = channelImage;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

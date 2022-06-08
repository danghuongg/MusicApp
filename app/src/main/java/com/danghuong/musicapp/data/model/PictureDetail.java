package com.danghuong.musicapp.data.model;

import java.util.Date;
import java.util.Objects;

public class PictureDetail   {
    private String album = "";
    private String path = "";
    private String size = "";
    private String title = "";
    private String width = "";
    private String height = "";
    private boolean select = false;
    private int section = 0;
    private boolean video;
    private long day = 0;
    private long month = 0;
    private long year = 0;
    private long dateLastModified = 0;
    private String address;
    private Long duration = 0L;
    private Date timeDelete;
    private long countDayDelete = 0;
    private String pathOld = "";
    private long timeEdit = 0;
    private boolean isSelectAllDay;
    private String uriContentResolver;
    private long bucketId;
    private String bucketName;
    private int totalCount = 0;
    private boolean videoAlbum;
    private String datetime= "";
    private int totalCountVideo = 0;
    private int totalCountImage = 0;
    private boolean isTheAlbumsCreated;
    private boolean isFavorite = false;
    public PictureDetail() {

    }
    public PictureDetail(String title, long dateLastModified, String path) {
        this.title = title;
        this.dateLastModified = dateLastModified;
        this.path = path;
    }

    public PictureDetail(long dateLastModified, String album, String path, String size, String title, String width, String height, boolean select, int section, boolean video) {
        this.dateLastModified = dateLastModified;
        this.album = album;
        this.path = path;
        this.size = size;
        this.title = title;
        this.width = width;
        this.height = height;
        this.select = select;
        this.section = section;
        this.video = video;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public long getDateLastModified() {
        return dateLastModified;
    }

    public void setDateLastModified(long dateLastModified) {
        this.dateLastModified = dateLastModified;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUriContentResolver() {
        return uriContentResolver;
    }

    public void setUriContentResolver(String uriContentResolver) {
        this.uriContentResolver = uriContentResolver;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public long getMonth() {
        return month;
    }

    public void setMonth(long month) {
        this.month = month;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Date getTimeDelete() {
        return timeDelete;
    }

    public void setTimeDelete(Date timeDelete) {
        this.timeDelete = timeDelete;
    }

    public long getCountDayDelete() {
        return countDayDelete;
    }

    public void setCountDayDelete(long countDayDelete) {
        this.countDayDelete = countDayDelete;
    }

    public String getPathOld() {
        return pathOld;
    }

    public void setPathOld(String pathOld) {
        this.pathOld = pathOld;
    }

    public long getTimeEdit() {
        return timeEdit;
    }

    public void setTimeEdit(long timeEdit) {
        this.timeEdit = timeEdit;
    }

    public boolean isSelectAllDay() {
        return isSelectAllDay;
    }

    public void setSelectAllDay(boolean selectAllDay) {
        isSelectAllDay = selectAllDay;
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

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public boolean isVideoAlbum() {
        return videoAlbum;
    }

    public void setVideoAlbum(boolean videoAlbum) {
        this.videoAlbum = videoAlbum;
    }


    public boolean isTheAlbumsCreated() {
        return isTheAlbumsCreated;
    }

    public void setTheAlbumsCreated(boolean theAlbumsCreated) {
        isTheAlbumsCreated = theAlbumsCreated;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PictureDetail that = (PictureDetail) o;
        return Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path);
    }

}

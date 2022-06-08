package com.danghuong.musicapp.data.repository;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.appcompat.app.AppCompatActivity;

import com.danghuong.musicapp.data.database.MusicDatabase;
import com.danghuong.musicapp.data.model.PictureAlbum;
import com.danghuong.musicapp.data.model.PictureDetail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PhotoRepository {
    private MusicDatabase musicDb;

    @Inject
    public PhotoRepository(MusicDatabase musicDb) {
        this.musicDb = musicDb;
    }

    private static final Calendar calendar = Calendar.getInstance(Locale.US);



    public Single<PictureAlbum> getPhotoAlbums(AppCompatActivity mActivity, Uri uri) {
        return Single.fromCallable(new Callable<PictureAlbum>() {
            @Override
            public PictureAlbum call() throws Exception {
                return getItemPhotoAlbum(mActivity, uri);
            }
        })
                .subscribeOn(Schedulers.io())  //dangkychaynen
                .observeOn(AndroidSchedulers.mainThread()); // quansatguidulieu
    }

    @SuppressLint("Range")
    public static PictureAlbum getItemVideoAlbum(AppCompatActivity mActivity, Uri uri) {
        PictureAlbum albumDetail = new PictureAlbum();
        ArrayList<PictureDetail> arrPt = new ArrayList<>();
        String[] projection1;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            projection1 = new String[]{MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.DATA, MediaStore.Video.Media.BUCKET_ID,
                    MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Video.Media.DATE_TAKEN,
                    MediaStore.Video.Media.WIDTH,
                    MediaStore.Video.Media.DATE_MODIFIED,
                    MediaStore.Video.Media.HEIGHT,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.DATE_ADDED,
                    MediaStore.Video.Media.DURATION
            };
        } else {
            projection1 = new String[]{MediaStore.Video.Media._ID,
                    MediaStore.Video.Media.RELATIVE_PATH,
                    MediaStore.Video.Media.BUCKET_ID,
                    MediaStore.Video.Media.DISPLAY_NAME,
                    MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Video.Media.DATE_MODIFIED,
                    MediaStore.Video.Media.DATE_TAKEN,
                    MediaStore.Video.Media.WIDTH,
                    MediaStore.Video.Media.HEIGHT,
                    MediaStore.Video.Media.SIZE,
                    MediaStore.Video.Media.TITLE,
                    MediaStore.Video.Media.DATE_ADDED,
                    MediaStore.Video.Media.DURATION
            };
        }
        Cursor mImageCursor = mActivity.getContentResolver().query(uri, projection1, null, null, "datetaken DESC");
        Calendar calendar = Calendar.getInstance(Locale.US);
        while (true) {
            assert mImageCursor != null;
            if (!mImageCursor.moveToNext()) break;
            PictureDetail photo = new PictureDetail();
            String path;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                path = mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Video.Media.DATA));
            } else {
                path = "/storage/emulated/0/" + mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Video.Media.RELATIVE_PATH)) +
                        mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
            }
            path = path.replace("_tmp", "");
            photo.setPath(path);
            photo.setUriContentResolver(String.valueOf(ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    mImageCursor.getInt(mImageCursor.getColumnIndex(MediaStore.Video.VideoColumns._ID)))));
            String bucket = mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
            long bucketId = mImageCursor.getInt(mImageCursor.getColumnIndex(MediaStore.Video.Media.BUCKET_ID));
            long dateLastModified = getDateLastModifiedVideo(mImageCursor);
            photo.setBucketId(bucketId);
            photo.setBucketName(bucket != null ? bucket : "all photo");
            photo.setDateLastModified(dateLastModified);
            photo.setVideo(true);

            setTimeForMedia(photo, dateLastModified, calendar);
            photo.setWidth(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Video.Media.WIDTH)));
            photo.setHeight(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Video.Media.HEIGHT)));
            photo.setSize(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Video.Media.SIZE)));
            String title = mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Video.Media.TITLE));
            long duration = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Video.Media.DURATION));
            if (duration > 0) {
                photo.setDuration(duration);
            } else {
                continue;
            }
            photo.setTitle(title);
            photo.setSelect(false);
            photo.setVideo(true);
            arrPt.add(photo);
        }
        albumDetail.setBucketName("video");
        albumDetail.setVideo(true);
        albumDetail.setTotalCountImage(0);
        albumDetail.setTotalCountVideo(arrPt.size());
        albumDetail.setTotalCount(albumDetail.getTotalCountVideo());
        albumDetail.setPictureDetails(arrPt);
        mImageCursor.close();
        return albumDetail;
    }



    //this, MediaStore.Video.Media.EXTERNAL_CONTENT_URI
    @SuppressLint("Range")
    public static PictureAlbum getItemPhotoAlbum(AppCompatActivity mActivity, Uri uri) {
        PictureAlbum albumDetail = new PictureAlbum();
        ArrayList<PictureDetail> arrPt = new ArrayList<>();
        String[] projection1;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            projection1 = new String[]{MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_MODIFIED,
                    MediaStore.Images.Media.DATE_TAKEN,
                    MediaStore.Images.Media.WIDTH,
                    MediaStore.Images.Media.HEIGHT,
                    MediaStore.Images.Media.SIZE,
                    MediaStore.Images.Media.TITLE,
                    MediaStore.Images.ImageColumns.DATE_ADDED,
                    MediaStore.Images.ImageColumns.IS_PRIVATE
            };
        } else {
            projection1 = new String[]{MediaStore.Images.Media._ID,
                    MediaStore.Images.Media.RELATIVE_PATH,
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.BUCKET_ID,
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Images.Media.DATE_TAKEN,
                    MediaStore.Images.Media.DATE_MODIFIED,
                    MediaStore.Images.Media.WIDTH,
                    MediaStore.Images.Media.HEIGHT,
                    MediaStore.Images.Media.SIZE,
                    MediaStore.Images.Media.TITLE,
                    MediaStore.Images.ImageColumns.DATE_ADDED,
                    MediaStore.Images.ImageColumns.IS_PRIVATE
            };
        }
        Cursor mImageCursor = mActivity.getContentResolver().query(uri,
                projection1, null, null, null);
        if (mImageCursor != null) {
            while (mImageCursor.moveToNext()) {
                PictureDetail photo = new PictureDetail();
                String size = mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                if (size == null) continue;
                photo.setSize(size);
                String path;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                    path = mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                } else {
                    path = "/storage/emulated/0/" + mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Images.Media.RELATIVE_PATH)) +
                            mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                }
                path = path.replace("_tmp", "");
                photo.setPath(path);
                photo.setUriContentResolver(String.valueOf(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        mImageCursor.getInt(mImageCursor.getColumnIndex(MediaStore.Images.ImageColumns._ID)))));
                long bucketId = mImageCursor.getInt(mImageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID));
                String bucketName = mImageCursor.getString(mImageCursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                long dateLastModified = getDateLastModifiedImage(mImageCursor);
                setTimeForMedia(photo, dateLastModified, calendar);
                photo.setBucketId(bucketId);
                photo.setBucketName(bucketName);
                photo.setVideo(false);
                photo.setWidth(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Images.Media.WIDTH)));
                photo.setHeight(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Images.Media.HEIGHT)));
                photo.setTitle(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Images.Media.TITLE)));
                photo.setSelect(false);
                photo.setVideo(false);
                arrPt.add(photo);
            }
            mImageCursor.close();
        }

        albumDetail.setBucketName("photo");
        albumDetail.setTotalCountVideo(0);
        albumDetail.setTotalCountImage(arrPt.size());
        albumDetail.setTotalCount(albumDetail.getTotalCountVideo());
        albumDetail.setPictureDetails(arrPt);
        return albumDetail;
    }

    @SuppressLint("Range")
    private static long getDateLastModifiedImage(Cursor mImageCursor) {
        long dateTaken = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN));
        long dateAdded = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
        long dateModified = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
        long dateLastModified = maxNumber(dateTaken, dateAdded, dateModified);

        if (String.valueOf(dateLastModified).length() == 10)
            dateLastModified = dateLastModified * 1000;

//        if (dateLastModified >= 1000000000 && dateLastModified <= 9999999999L) {
//            dateLastModified = dateLastModified * 1000;
//        }
        return dateLastModified;
    }

    @SuppressLint("Range")
    private static long getDateLastModifiedVideo(Cursor mImageCursor) {
        long dateTaken = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Video.Media.DATE_TAKEN));
        long dateAdded = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED));
        long dateModified = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
        long dateLastModified = maxNumber(dateTaken, dateAdded, dateModified);
        if (dateLastModified >= 1000000000 && dateLastModified <= 9999999999L) {
            dateLastModified = dateLastModified * 1000;
        }
        return dateLastModified;
    }

    private static void setTimeForMedia(PictureDetail photo, long date1, Calendar calendar) {
        calendar.setTimeInMillis(date1);
        photo.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        photo.setMonth((calendar.get(Calendar.MONTH) + 1));
        photo.setYear(calendar.get(Calendar.YEAR));
        String day = photo.getDay() < 10 ? "0" + photo.getDay() : "" + photo.getDay();
        String month = photo.getMonth() < 10 ? "0" + photo.getMonth() : "" + photo.getMonth();
        String year = photo.getYear() + "";

        long hourL = calendar.get(Calendar.HOUR_OF_DAY);
        String hour = hourL < 10 ? "0" + hourL : hourL + "";

        long minuteL = calendar.get(Calendar.MINUTE);
        String minute = minuteL < 10 ? "0" + minuteL : minuteL + "";

        long secondL = calendar.get(Calendar.SECOND);
        String second = secondL < 10 ? "0" + secondL : secondL + "";

        photo.setDatetime(year + "-" + month + "-" + day + "-" + hour + "-" + minute + "-" + second);
    }

    @SuppressLint("Range")
    private static void setInfor(Cursor mImageCursor, PictureDetail photo, boolean b) {
        photo.setWidth(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.MediaColumns.WIDTH)));
        photo.setHeight(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.MediaColumns.HEIGHT)));
        photo.setSize(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.MediaColumns.SIZE)));
        photo.setTitle(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));
        photo.setSelect(false);
        photo.setVideo(b);
    }


    private static PictureAlbum getAllVideo(PictureAlbum albumDetail) {
        PictureAlbum albumDetail1 = new PictureAlbum();
        ArrayList<PictureDetail> detailArrayList1 = new ArrayList<>();
        ArrayList<PictureDetail> detailArrayList = new ArrayList<>(albumDetail.getPictureDetails());
        int countVideo = 0;
        for (PictureDetail pic : detailArrayList) {
            if (pic.isVideo()) {
                countVideo++;
                detailArrayList1.add(pic);
            }
        }
        albumDetail1.setTotalCountVideo(countVideo);
        albumDetail1.setTotalCount(countVideo);
        albumDetail1.setPictureDetails(detailArrayList1);
        return albumDetail1;
    }
    public static long maxNumber(long number1, long number2, long number3) {
        long max = number1;
        if (number2 > max) {
            max = number2;
        }
        if (number3 > max) {
            max = number3;
        }
        return max;
    }
}

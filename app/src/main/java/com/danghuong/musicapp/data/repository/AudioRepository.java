package com.danghuong.musicapp.data.repository;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.danghuong.musicapp.R;
import com.danghuong.musicapp.data.database.MusicDatabase;
import com.danghuong.musicapp.data.model.AudioAlbum;
import com.danghuong.musicapp.data.model.AudioDetail;
import com.danghuong.musicapp.data.model.MusicListItem;
import com.danghuong.musicapp.data.model.PictureAlbum;
import com.danghuong.musicapp.data.model.PictureDetail;
import com.danghuong.musicapp.data.model.SelectCheckItem;
import com.danghuong.musicapp.data.model.SelectItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class AudioRepository {

    public MusicDatabase musicDatabase;
    public ArrayList<AudioDetail> audioDetails = new ArrayList<>();

    @Inject
    public AudioRepository(MusicDatabase musicDatabase) {
        this.musicDatabase = musicDatabase;
    }
    @SuppressLint("Range")
    public Single<List<MusicListItem>> getMusicitemList(AppCompatActivity mActivity,
                                                        ArrayList<PictureDetail> pictureDetails) {
        return Single.fromCallable(new Callable<List<MusicListItem>>() {
            @Override
            public List<MusicListItem> call() throws Exception {
                return AudioRepository.this.musicListItemList(mActivity, pictureDetails);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("Range")
    public Single<List<SelectCheckItem>> getSelectedList(AppCompatActivity mActivity,ArrayList<PictureDetail> pictureDetails) {
        return Single.fromCallable(new Callable<List<SelectCheckItem>>() {
            @Override
            public List<SelectCheckItem> call() throws Exception {
                return AudioRepository.this.selectItemList(mActivity,pictureDetails);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public List<MusicListItem> musicListItemList(AppCompatActivity mActivity, ArrayList<PictureDetail> pictureDetails) {
        List<MusicListItem> musicListItemList = new LinkedList<>();
        audioDetails = getAudioAlbum(mActivity, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI).getAudioDetail();
        for (int i = 12; i < audioDetails.size(); i++) {
            musicListItemList.add(new MusicListItem(audioDetails.get(i).getTitle(),
                    R.drawable.ic_baseline_phone_android_24, R.drawable.ic_baseline_more_24,
                    audioDetails.get(i).getWriter(), pictureDetails.get(i).getPath(),
                    audioDetails.get(i).getPath(), "NhacCuaTui.com", audioDetails.get(i).getDateTime()));
        }
        return musicListItemList;
    }

    public List<SelectCheckItem> selectItemList(AppCompatActivity mActivity,ArrayList<PictureDetail> pictureDetails) {
        List<SelectCheckItem> selectList = new LinkedList<>();
        List<MusicListItem> musicListItemList=musicListItemList(mActivity,  pictureDetails);
            for (int i = 0; i < musicListItemList.size(); i++) {
                if (i < 9) {
                    selectList.add(new SelectCheckItem(false, new SelectItem(musicListItemList.get(i).getMusic_name(), R.drawable.ic_baseline_phone_android_24
                            , musicListItemList.get(i).getSingerName(), "0" + (i+1), musicListItemList.get(i).getLink_music(), "NhacCuaTui.com", R.drawable.ic_baseline_panorama_fish_eye_24)));
                } else {
                    selectList.add(new SelectCheckItem(false, new SelectItem(musicListItemList.get(i).getMusic_name(), R.drawable.ic_baseline_phone_android_24
                            , musicListItemList.get(i).getSingerName(), (i+1) + "", musicListItemList.get(i).getLink_music(), "NhacCuaTui.com", R.drawable.ic_baseline_panorama_fish_eye_24)));
                }

        }
        return selectList;
    }

    @SuppressLint("Range")
    public AudioAlbum getAudioAlbum(AppCompatActivity mActivity, Uri uri) {
        AudioAlbum audioAlbum = new AudioAlbum();
        ArrayList<AudioDetail> audioList = new ArrayList<>();
        String[] projection1;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            projection1 = new String[]{
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.DATA,
                    MediaStore.Audio.Media.BUCKET_ID,
                    MediaStore.Audio.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DATE_ADDED,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.COMPOSER
            };
        } else {
            projection1 = new String[]{
                    MediaStore.Audio.Media._ID,
                    MediaStore.Audio.Media.RELATIVE_PATH,
                    MediaStore.Audio.Media.BUCKET_ID,
                    MediaStore.Audio.Media.DISPLAY_NAME,
                    MediaStore.Audio.Media.BUCKET_DISPLAY_NAME,
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.DATE_ADDED,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.COMPOSER
            };
        }
        Cursor mImageCursor = mActivity.getContentResolver().query(uri, projection1, null, null, "datetaken DESC");
        Calendar calendar = Calendar.getInstance(Locale.US);
        while (true) {
            assert mImageCursor != null;
            if (!mImageCursor.moveToNext()) break;
            AudioDetail audio = new AudioDetail();
            String path;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
                path = mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            } else {
                path = "/storage/emulated/0/" + mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Audio.Media.RELATIVE_PATH)) +
                        mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
            }
            path = path.replace("_tmp", "");
            audio.setUriContentResolver(String.valueOf(ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    mImageCursor.getInt(mImageCursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID)))));
            String bucket = mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Audio.Media.BUCKET_DISPLAY_NAME));
            long bucketId = mImageCursor.getInt(mImageCursor.getColumnIndex(MediaStore.Audio.Media.BUCKET_ID));
            long dateLastModified = getDateLastModifiedVideo(mImageCursor);
            audio.setBucketId(bucketId);
            audio.setBucketName(bucket != null ? bucket : "all audio");
            setTimeForMedia(audio, dateLastModified, calendar);

            String singer = mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            if (singer == null || singer.isEmpty()) singer = "No Name";

            String composer = mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Audio.Media.COMPOSER));

            long dateAdd = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
            String datetime = dateAdd + "";


            String title = mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            long duration = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            if (duration > 0) {
                audio.setDuration(duration);
            } else {
                continue;
            }

            audio.setDateTime(datetime);
            audio.setComposer(composer);
            audio.setWriter(singer);
            audio.setPath(path);
            audio.setTitle(title);
            audio.setDownload(true);
            audio.setFavorite(false);
            audio.setSelect(false);
            audioList.add(audio);
        }
        audioAlbum.setBucketName("audio");
        audioAlbum.setAudioDetail(audioList);
        mImageCursor.close();

        return audioAlbum;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("Range")
    private long getDateLastModifiedImage(Cursor mImageCursor) {
        long dateTaken = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Audio.Media.DATE_TAKEN));
        long dateAdded = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
        long dateModified = mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED));
        long dateLastModified = maxNumber(dateTaken, dateAdded, dateModified);

        if (String.valueOf(dateLastModified).length() == 10)
            dateLastModified = dateLastModified * 1000;

//        if (dateLastModified >= 1000000000 && dateLastModified <= 9999999999L) {
//            dateLastModified = dateLastModified * 1000;
//        }
        return dateLastModified;
    }

    @SuppressLint("Range")
    private long getDateLastModifiedVideo(Cursor mImageCursor) {
        return mImageCursor.getLong(mImageCursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED));
    }


    private void setTimeForMedia(AudioDetail audio, long date1, Calendar calendar) {
        calendar.setTimeInMillis(date1);
        audio.setDay(calendar.get(Calendar.DAY_OF_MONTH));
        audio.setMonth((calendar.get(Calendar.MONTH) + 1));
        audio.setYear(calendar.get(Calendar.YEAR));
        String day = audio.getDay() < 10 ? "0" + audio.getDay() : "" + audio.getDay();
        String month = audio.getMonth() < 10 ? "0" + audio.getMonth() : "" + audio.getMonth();
        String year = audio.getYear() + "";

        long hourL = calendar.get(Calendar.HOUR_OF_DAY);
        String hour = hourL < 10 ? "0" + hourL : hourL + "";

        long minuteL = calendar.get(Calendar.MINUTE);
        String minute = minuteL < 10 ? "0" + minuteL : minuteL + "";

        long secondL = calendar.get(Calendar.SECOND);
        String second = secondL < 10 ? "0" + secondL : secondL + "";

        audio.setDateTime(year + "-" + month + "-" + day + "-" + hour + "-" + minute + "-" + second);
    }

    @SuppressLint("Range")
    private void setInfor(Cursor mImageCursor, AudioDetail audio, boolean b) {
//        photo.setWidth(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.MediaColumns.WIDTH)));
//        photo.setHeight(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.MediaColumns.HEIGHT)));
//        photo.setSize(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.MediaColumns.SIZE)));
        audio.setTitle(mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.MediaColumns.TITLE)));
        audio.setSelect(false);
//        photo.setVideo(b);
    }


    private PictureAlbum getAllVideo(PictureAlbum albumDetail) {
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

    public long maxNumber(long number1, long number2, long number3) {
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


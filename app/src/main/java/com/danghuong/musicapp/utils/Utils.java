package com.danghuong.musicapp.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.DisplayMetrics;

public class Utils {
    public static int convertToPx(int dp, Context context) {
        // Get the screen's density scale
        final float scale = context.getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (dp * scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        return displaymetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displaymetrics = context.getResources().getDisplayMetrics();
        return displaymetrics.heightPixels;
    }

    public static int pxToDp(float px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static long maxNumber(long number1, long number2, long number3) {
        long max = number1;
        if(number2 > max){
            max = number2;
        }
        if (number3 > max) {
            max = number3;
        }
        return max;
    }

    public static boolean isAtLeastSdkVersion(int versionCode) {
        return Build.VERSION.SDK_INT >= versionCode;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        boolean isResize = false;
        if (width > height && width > 1080) {
            height = 1080 * height / width;
            width = 1080;
            isResize = true;
        } else if (height > width && height > 1920) {
            width = 1920 * width / height;
            height = 1920;
            isResize = true;
        }
        if (width % 2 != 0) {
            width -= 1;
            isResize = true;
        }
        if (height % 2 != 0) {
            height -= 1;
            isResize = true;
        }
        if (isResize) {
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        }
        return bitmap;
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        boolean isResize = false;
        if (width > height && width > w) {
            height = w * height / width;
            width = w;
            isResize = true;
        } else if (height > width && height > h) {
            width = h * width / height;
            height = h;
            isResize = true;
        }
        if (width % 2 != 0) {
            width += 1;
            isResize = true;
        }
        if (height % 2 != 0) {
            height += 1;
            isResize = true;
        }
        if (isResize) {
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        }
        return bitmap;
    }


}

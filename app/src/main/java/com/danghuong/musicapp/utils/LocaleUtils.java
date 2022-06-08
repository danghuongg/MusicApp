package com.danghuong.musicapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import androidx.core.app.ActivityCompat;

import com.danghuong.musicapp.MainActivity;
import com.danghuong.musicapp.R;
import com.danghuong.musicapp.common.Common;
import com.danghuong.musicapp.data.model.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import timber.log.Timber;

import static com.danghuong.musicapp.App.getContext;
import static com.danghuong.musicapp.utils.Utils.isAtLeastSdkVersion;

public class LocaleUtils {

    public static String codeLanguageCurrent = "null";

    public static void applyLocale(Context context) {
        //it to miss the previous language setting
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        codeLanguageCurrent = preferences.getString(Common.PREF_SETTING_LANGUAGE, "null");
        if (codeLanguageCurrent.equals("null")) {
            codeLanguageCurrent = Locale.getDefault().getLanguage();
        }

        if (TextUtils.isEmpty(codeLanguageCurrent)) {
            codeLanguageCurrent = Common.LANGUAGE_EN;
        }
        Locale newLocale = new Locale(codeLanguageCurrent);
        updateResource(context, newLocale);
        if (context != context.getApplicationContext()) {
            updateResource(context.getApplicationContext(), newLocale);
        }

    }

    public static void updateResource(Context context, Locale locale) {
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Locale current = getLocaleCompat(resources);
        if (current == locale) {
            return;
        }
        Configuration configuration = new Configuration(resources.getConfiguration());
        if (isAtLeastSdkVersion(Build.VERSION_CODES.N)) {
            configuration.setLocale(locale);
        } else if (isAtLeastSdkVersion(Build.VERSION_CODES.JELLY_BEAN_MR1)) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        Timber.e("applyLocale " + locale);
    }

    public static Locale getLocaleCompat(Resources resources) {
        Configuration configuration = resources.getConfiguration();
        return isAtLeastSdkVersion(Build.VERSION_CODES.N) ? configuration.getLocales().get(0) : configuration.locale;
    }

    public static void applyLocaleAndRestart(Activity activity, String localeString) {
        Timber.e("applyLocaleAndRestart " + localeString);
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(activity);
        preferences.edit().putString(Common.PREF_SETTING_LANGUAGE, localeString).apply();
        LocaleUtils.applyLocale(activity);
        Intent intent = new Intent(activity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        ActivityCompat.finishAffinity(activity);
    }

    public static List<Language> getLanguages() {
        List<Language> list = new ArrayList<>();
        list.add(new Language(Common.LANGUAGE_EN, getContext().getResources().getString(R.string.text_EN)));
        list.add(new Language(Common.LANGUAGE_VN, getContext().getResources().getString(R.string.text_VN)));
        return list;

//        list.add(new Language(AppConstant.LANGUAGE_HI, context.getResources().getString(R.string.hindi)));
//        list.add(new Language(AppConstant.LANGUAGE_JA, context.getResources().getString(R.string.japan)));
//        list.add(new Language(AppConstant.LANGUAGE_KO, context.getResources().getString(R.string.korea)));

//        return list;
//    }

    }
}

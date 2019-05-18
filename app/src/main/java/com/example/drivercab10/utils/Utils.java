package com.example.drivercab10.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.Locale;

public class Utils {

    public static void hideStatusBar(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


    public static void loadLocale(Context context) {
        String langPref = "Language";
        SharedPreferences prefs = context.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        String language = prefs.getString(langPref, Locale.getDefault().getDisplayLanguage());
        changeLang(context, language);
    }

//    public static void changeLang(Context context, String lang) {
//        Resources res = context.getResources();
//        DisplayMetrics d = res.getDisplayMetrics();
//        Configuration configuration = res.getConfiguration();
//        configuration.setLocale(new Locale(lang));
//        res.updateConfiguration(configuration, d);
//    }

    public static void changeLang(Context context, String lang) {
        Configuration config = context.getResources().getConfiguration();
        if (!"".equals(lang) && !config.locale.getLanguage().equals(lang)) {

            SharedPreferences.Editor ed = PreferenceManager.getDefaultSharedPreferences(context).edit();
            ed.putString("lang", lang);
            ed.apply();

            Locale locale = new Locale(lang);
            Locale.setDefault(locale);
            Configuration conf = new Configuration(config);
            conf.locale = locale;
            context.getResources().updateConfiguration(conf, context.getResources().getDisplayMetrics());
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

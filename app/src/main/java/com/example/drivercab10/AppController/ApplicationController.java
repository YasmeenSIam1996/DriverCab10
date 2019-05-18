package com.example.drivercab10.AppController;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


import com.example.drivercab10.model.User;

import java.util.Locale;

public class ApplicationController extends Application {

    private static ApplicationController mInstance;
    private static Context context;
    private Locale myLocale;
    public static int langNum = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        mInstance = this;

//       VolleySingleton.getInstance();
//       FirebaseApp.initializeApp(this);

        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String lang = prefs.getString("Language", "ar");
        changeLang(lang);

    }

    public static synchronized ApplicationController getInstance() {
        if (mInstance == null)
            return mInstance = new ApplicationController();
        else
            return mInstance;
    }


    public static Context getAppContext() {
        return ApplicationController.context;
    }


    public void loadLocale() {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        String language = prefs.getString(langPref, "ar");
        changeLang(language);
    }

    public void changeLang(String lang) {

        Log.e("changeLang", "changeLang");

        if (lang.equalsIgnoreCase(""))
            return;

        myLocale = new Locale(lang);
        saveLocale(lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        if (lang.equals("ar")) {
            langNum = 1;
        } else {
            langNum = 0;
        }
    }

    public void saveLocale(String lang) {
        String langPref = "Language";
        SharedPreferences prefs = getSharedPreferences("CommonPrefs", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(langPref, lang);
        editor.apply();
    }


    public void loginUser(final User user) {
        SharedPreferences.Editor editor = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE).edit();
        editor.putString("UserToken", user.getToken());
        editor.putInt("token_expiry", user.getToken_expiry());
        editor.putString("login_by", user.getLogin_by());
        editor.putString("UserEmail", user.getEmail());
        editor.putString("UserName", user.getName());
        editor.putString("first_name", user.getFirst_name());
        editor.putString("last_name", user.getLast_name());
        editor.putString("mobile", user.getMobile());
        editor.putString("gender", user.getGender());
        editor.putString("is_approved", user.getIs_approved());
        editor.putString("picture", user.getPicture());
        editor.putInt("UserId", user.getId());
        editor.putString("social_unique_id", user.getSocial_unique_id());
        editor.putString("payment_mode_status", user.getPayment_mode_status());
        editor.putString("currency_code", user.getCurrency_code());
        editor.putString("country", user.getCountry());
        editor.putString("timezone", user.getTimezone());
        editor.putString("wallet_bay_key", user.getWallet_bay_key());
        editor.commit();
    }


    public User getUser() {
        SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);
        User user = new User();
        user.setToken(sharedPreferences.getString("UserToken", ""));
        user.setToken_expiry(sharedPreferences.getInt("token_expiry", 0));
        user.setLogin_by(sharedPreferences.getString("login_by", ""));
        user.setEmail(sharedPreferences.getString("UserEmail", ""));
        user.setName(sharedPreferences.getString("UserName", ""));
        user.setFirst_name(sharedPreferences.getString("first_name", ""));
        user.setLast_name(sharedPreferences.getString("last_name", ""));
        user.setMobile(sharedPreferences.getString("mobile", ""));
        user.setGender(sharedPreferences.getString("gender", ""));
        user.setIs_approved(sharedPreferences.getString("is_approved", ""));
        user.setPicture(sharedPreferences.getString("picture", ""));
        user.setId(sharedPreferences.getInt("UserId", 0));
        user.setSocial_unique_id(sharedPreferences.getString("social_unique_id", ""));
        user.setPayment_mode_status(sharedPreferences.getString("payment_mode_status", ""));
        user.setCurrency_code(sharedPreferences.getString("currency_code", ""));
        user.setCountry(sharedPreferences.getString("country", ""));
        user.setTimezone(sharedPreferences.getString("timezone", ""));
        user.setWallet_bay_key(sharedPreferences.getString("wallet_bay_key", ""));
        return user;
    }

    public void DeleteUser() {
        SharedPreferences.Editor editor = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE).edit();
        editor.putString("UserToken", "");
        editor.putInt("token_expiry", 0);
        editor.putString("login_by", "");
        editor.putString("UserEmail", "");
        editor.putString("UserName", "");
        editor.putString("first_name", "");
        editor.putString("last_name", "");
        editor.putString("mobile", "");
        editor.putString("gender", "");
        editor.putString("is_approved", "");
        editor.putString("picture", "");
        editor.putInt("UserId", 0);
        editor.putString("social_unique_id", "");
        editor.putString("payment_mode_status", "");
        editor.putString("currency_code", "");
        editor.putString("country", "");
        editor.putString("timezone", "");
        editor.putString("wallet_bay_key", "");
        editor.commit();
    }


    public void putDeviceToken(String deviceToken) {
        SharedPreferences.Editor edit = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE).edit();

        edit.putString("DEVICE_TOKEN", deviceToken);
        edit.commit();
    }

    public String getDeviceToken() {
        SharedPreferences sharedPreferences = ApplicationController.getAppContext().getSharedPreferences("access_token", Context.MODE_PRIVATE);

        return sharedPreferences.getString("DEVICE_TOKEN", null);

    }


    public boolean IsUserLoggedIn() {
        if (ApplicationController.getInstance().getUser().getToken().trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}

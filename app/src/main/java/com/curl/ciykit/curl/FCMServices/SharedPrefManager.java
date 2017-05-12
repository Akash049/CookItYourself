package com.curl.ciykit.curl.FCMServices;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Akash Chandra on 11-01-2017.
 */

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "FCMSharedPref";
    private static final String TAG_TOKEN = "tagtoken";

    private static final String SHARED_SAMPLE = "SampleSharedPref";
    private static final String TAG_SAMPLE = "tagSample";

    private static final String SHARED_LOGGED_IN = "Log";
    private static final String TAG_LOGGED_IN = "tagLog";

    private static final String SHARED_USER_ID = "UserID";
    private static final String TAG_USER_ID = "tagUserID";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_TOKEN, null);
    }

    public boolean saveNews(String sample){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_SAMPLE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_SAMPLE, sample);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getNews(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_SAMPLE, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_SAMPLE, null);
    }


    public boolean saveLogState(String sample){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGGED_IN, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_LOGGED_IN, sample);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getLogState(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_LOGGED_IN, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_LOGGED_IN, null);
    }

    public boolean saveUserId(String sample){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_USER_ID, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TAG_USER_ID, sample);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getUserId(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_USER_ID, Context.MODE_PRIVATE);
        return  sharedPreferences.getString(TAG_USER_ID, null);
    }
}

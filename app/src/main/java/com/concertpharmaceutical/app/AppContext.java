package com.concertpharmaceutical.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;


import com.concertpharmaceutical.network.LiveDataProvider;
import com.concertpharmaceutical.preferences.AppConstants;
import com.concertpharmaceutical.preferences.PreferenceHandler;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class AppContext extends Application {
    static final String TAG = "Application Controller";
    public static LiveDataProvider dataProvider;
    public static PreferenceHandler preferenceHandler;

    static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        dataProvider = new LiveDataProvider(this);
        preferenceHandler = new PreferenceHandler(context);
    }

    public static Context getContext() {
        return context;
    }

    public static String getTag() {
        return TAG;
    }

    public static void setContext(Context context) {
        AppContext.context = context;
    }

    public static void setDefaultSettings(){
        if(PreferenceHandler.getStringPreferences(AppConstants.SCANNER).equals("")){
            PreferenceHandler.updatePreferences(AppConstants.SCANNER, AppConstants.SCANDIT);
        }
    }
}
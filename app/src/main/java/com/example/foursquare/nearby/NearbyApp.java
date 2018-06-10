package com.example.foursquare.nearby;

import android.app.Application;
import android.content.Context;

/*
 * Base class for maintaining global application state
 * */
public final class NearbyApp extends Application {

    private static Context appContext;

    /*
     *Returns Application context
     * */
    public static Context getAppContext() {
        return appContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
    }
}

package com.example.foursquare.nearby.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.foursquare.nearby.NearbyApp;

/**
 * Class representing utility functions
 *
 * @author Shubham
 */

public final class Utils {

    /**
     * Indicates whether network connectivity exists
     *
     * @return -- true if connected or false
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) NearbyApp.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

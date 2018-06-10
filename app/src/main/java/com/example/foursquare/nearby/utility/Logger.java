package com.example.foursquare.nearby.utility;

import android.util.Log;

import com.example.foursquare.nearby.BuildConfig;


public final class Logger {

    public static final boolean DEBUG = BuildConfig.DEBUG;

    private Logger() {
    }

    /**
     * Send a {@link #VERBOSE} log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (DEBUG) {
            Log.v(tag, msg);
        }
    }

    /**
     * Send a {@link #DEBUG} log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (DEBUG) {
            Log.d(tag, msg);
        }
    }

    /**
     * Send an {@link #INFO} log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    /**
     * Send a {@link #WARN} log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (DEBUG) {
            Log.w(tag, msg);
        }
    }

    /**
     * Send an {@link #ERROR} log message.
     *
     * @param tag Used to identify the source of a log message. It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

}

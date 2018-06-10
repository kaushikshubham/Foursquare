package com.example.foursquare.nearby.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.foursquare.nearby.utility.Logger;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.ref.WeakReference;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

final class LocationHandler {
    private static final String TAG = LocationHandler.class.getSimpleName();
    private static final int ACCESS_FINE_LOCATION_INTENT_ID = 1001;
    private static final String BROADCAST_ACTION = "android.location.PROVIDERS_CHANGED";
    private final WeakReference<MainActivity> mContext;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private long UPDATE_INTERVAL = 5 * 1000;  /* 5 sec */
    private long FASTEST_INTERVAL = 1000;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;

    /* Broadcast receiver to check status of GPS */
    private BroadcastReceiver gpsLocationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //If Action is Location
            if (intent.getAction().matches(BROADCAST_ACTION)) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                //Check if GPS is turned ON or OFF
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Logger.e(TAG, "About GPS is Enabled in your device");
                    startLocationUpdates();
                } else {
                    //If GPS turned OFF show Location Dialog
                    Logger.e(TAG, "About GPS is Disabled in your device");
                }

            }
        }
    };

    LocationHandler(MainActivity mContext) {
        this.mContext = new WeakReference<>(mContext);
        init();
        checkPermissions();
    }

    private void init() {
        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        mSettingsClient = LocationServices.getSettingsClient(mContext.get());

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext.get());

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Logger.d(TAG, "Current location : " + locationResult);
                double lat = locationResult.getLastLocation().getLatitude();
                double lng = locationResult.getLastLocation().getLongitude();
                if (mContext.get() != null)
                    mContext.get().fetchVennueListBasedOnLocation("" + lat + "," + lng);
                stopLocationUpdates();
            }
        };
    }

    private void startLocationUpdates() {
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest).addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Logger.v(TAG, "onSuccess");
                try {
                    mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                } catch (SecurityException e) {
                    Logger.e(TAG, "Error loc perm : " + e);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Logger.v(TAG, "onFailure");
            }
        });
    }

    private void stopLocationUpdates() {
        mFusedLocationClient.removeLocationUpdates(mLocationCallback);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Logger.v(TAG, "Received response for location permission request.");
        if (requestCode == ACCESS_FINE_LOCATION_INTENT_ID && grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startLocationUpdates();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case ACCESS_FINE_LOCATION_INTENT_ID:
                switch (resultCode) {
                    case RESULT_OK:
                        Logger.e(TAG, "Settings Result OK GPS is Enabled in your device");
                        startLocationUpdates();
                        break;
                    case RESULT_CANCELED:
                        Logger.e(TAG, "Settings Result Cancel GPS is Disabled in your device");
                        break;
                }
                break;
        }
    }

    /* Check Location Permission for Marshmallow Devices */
    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(mContext.get(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else
            startLocationUpdates();
    }

    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mContext.get(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(mContext.get(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);

        } else {
            ActivityCompat.requestPermissions(mContext.get(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_INTENT_ID);
        }
    }

    public void onPause() {
        mContext.get().unregisterReceiver(gpsLocationReceiver);
    }

    public void onResume() {
        mContext.get().registerReceiver(gpsLocationReceiver, new IntentFilter(BROADCAST_ACTION));//Register broadcast receiver to check the status of GPS
    }

    void onDestroy() {
        stopLocationUpdates();
        mLocationRequest = null;
        mFusedLocationClient = null;
        mLocationSettingsRequest = null;
    }
}

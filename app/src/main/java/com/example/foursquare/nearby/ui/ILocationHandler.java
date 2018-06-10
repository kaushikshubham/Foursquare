package com.example.foursquare.nearby.ui;

import android.content.Intent;

interface ILocationHandler {

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    /*
    Delegator for view onPause
     */
    void onPause();

    /*
    Delegator for view onResume
     */
    void onResume();

    /*
    Delegator for view onDestroy
     */
    void onDestroy();
}

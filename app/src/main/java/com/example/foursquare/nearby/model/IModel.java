package com.example.foursquare.nearby.model;

interface IModel {

    /**
     * Base method to fetch data
     */
    void fetchData();

    /**
     * Base method to called onDestroy of view
     */
    void onDestroy();
}

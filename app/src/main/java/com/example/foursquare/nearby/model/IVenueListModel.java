package com.example.foursquare.nearby.model;


public interface IVenueListModel {

    /**
     * Method to fetch venue list from server
     *
     * @param locString -- location string
     */
    void fetchData(String locString);

    /**
     * Base method to called onDestroy of view
     */
    void onDestroy();
}

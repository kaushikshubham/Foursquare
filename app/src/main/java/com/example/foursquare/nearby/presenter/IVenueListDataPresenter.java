package com.example.foursquare.nearby.presenter;

import com.example.foursquare.nearby.ui.adapterr.IViewHolder;

public interface IVenueListDataPresenter extends IPresenter {

    /**
     * Method to store view interface
     *
     * @param data : location string
     *             eg: (lat,lng)
     */
    void fetchData(String data);

    /**
     * Method to store view interface
     *
     * @param holder : recyclerView holder callback to set data on ui
     * @param pos    : position of recyclerView item
     */
    void onbindView(IViewHolder holder, int pos);

    /**
     * Method to return list item count
     */
    int getCount();

    /**
     * Method to sort list based on distance
     */
    void sort();
}

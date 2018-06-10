package com.example.foursquare.nearby.presenter;


import com.example.foursquare.nearby.ui.IView;

interface IPresenter {

    /**
     * Method to store view interface
     *
     * @param iView
     */
    void attachView(IView iView);

    /**
     * Method to remove view callback
     **/
    void detachView();

    /**
     * Method to clean up resources on activity destroy
     **/
    void onDestroy();

    /**
     * Method to handle onClick of recycler list
     *
     * @param position : recyclerView item pos
     */
    void onClick(int position);
}

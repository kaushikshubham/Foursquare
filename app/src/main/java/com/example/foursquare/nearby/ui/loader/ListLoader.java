package com.example.foursquare.nearby.ui.loader;


import android.content.Context;
import android.content.Loader;

import com.example.foursquare.nearby.presenter.IVenueListDataPresenter;


public class ListLoader extends Loader<IVenueListDataPresenter> {

    private IVenueListDataPresenter presenter;

    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context   used to retrieve the application context.
     * @param presenter
     */
    public ListLoader(Context context, IVenueListDataPresenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    /* This function is called when activity is being started.
       Hence we deliver the result, our presenter, here. If the
       activity is destroyed in rotation, the loader isn't created
       again as system made sure it survived the rotation, and
       thus when starting of activity calls this function again
       automatically and we deliver the pre-rotation presenter as
       our result to the activity in onLoadFinished callback*/
    @Override
    protected void onStartLoading() {
        deliverResult(presenter);
    }
}

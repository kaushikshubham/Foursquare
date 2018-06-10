package com.example.foursquare.nearby.ui.adapterr;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.foursquare.nearby.R;
import com.example.foursquare.nearby.presenter.IVenueListDataPresenter;
import com.example.foursquare.nearby.utility.Logger;

public class VenueListAdapter extends RecyclerView.Adapter<VenueListHolder> {
    private static final String TAG = VenueListAdapter.class.getSimpleName();
    IVenueListDataPresenter presenter;

    public VenueListAdapter(IVenueListDataPresenter presenter) {
        Logger.v(TAG, "init adapter");
        this.presenter = presenter;
    }

    public void setVenueListDataPresenter(IVenueListDataPresenter iVenueListDataPresenter) {
        Logger.v(TAG, "setVenueListDataPresenter");
        this.presenter = iVenueListDataPresenter;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VenueListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Logger.v(TAG, "onCreateViewHolder");
        return new VenueListHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VenueListHolder holder, int position) {
        Logger.v(TAG, "onBindViewHolder : " + position);
        presenter.onbindView(holder, position);
    }

    @Override
    public int getItemCount() {
        int size = presenter == null ? 0 : presenter.getCount();
        Logger.i(TAG, "Size : " + size);
        return size;
    }
}

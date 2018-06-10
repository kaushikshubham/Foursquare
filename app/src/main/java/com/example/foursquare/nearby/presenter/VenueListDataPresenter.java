package com.example.foursquare.nearby.presenter;


import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.example.foursquare.nearby.NearbyApp;
import com.example.foursquare.nearby.data.Category;
import com.example.foursquare.nearby.data.Data;
import com.example.foursquare.nearby.data.Icon;
import com.example.foursquare.nearby.data.Location;
import com.example.foursquare.nearby.data.Venue;
import com.example.foursquare.nearby.model.IVenueListModel;
import com.example.foursquare.nearby.model.VenueListModel;
import com.example.foursquare.nearby.ui.adapterr.IViewHolder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class VenueListDataPresenter extends BasePresenter<Data> implements IVenueListDataPresenter {
    private Data data;
    private IVenueListModel model;
    private String locString;
    private List<Venue> list;
    private Comparator distanceComparator = new Comparator<Venue>() {
        @Override
        public int compare(Venue v1, Venue v2) {
            if (v1 == v2) {
                return 0;
            } else if (v2 == null) {
                return -1;
            } else if (v1 == null) {
                return 1;
            } else {
                int diff = v1.getLocation().getDistance() - v2.getLocation().getDistance();
                return diff;
            }
        }
    };

    public VenueListDataPresenter() {
        model = new VenueListModel(this);
    }

    @Override
    void saveData(Data value) {
        this.data = value;
        list = data.getResponse().getVenues();
    }

    @Override
    public void sort() {
        if (list != null && !list.isEmpty()) {
            Collections.sort(list, distanceComparator);
            notifyView();
        }
    }

    @Override
    public void fetchData(String locString) {
        if (data != null) {
            notifyView();
            return;
        }
        if(locString == null || locString.isEmpty()) {
            return;
        }
        this.locString = locString;
        model.fetchData(locString);
    }

    @Override
    public void onbindView(IViewHolder holder, int pos) {
        Venue venue = list.get(pos);

        holder.setName(venue.getName());
        List<Category> list = venue.getCategories();
        if (list != null && list.size() > 0) {
            Category category = list.get(0);
            if (!isEmpty(category.getName())) {
                holder.setCategory(category.getName());
            }
            Icon icon = category.getIcon();
            if (icon != null && !isEmpty(icon.toString())) {
                holder.setImageUrl(icon.toString());
            }
        }

        Location location = venue.getLocation();
        if (location != null) {
            String place = "";
            if (!isEmpty(location.getCity())) {
                holder.setCity(location.getCity());
                place += location.getCity();
            }

            if (!isEmpty(location.getState())) {
                holder.setCity(location.getState());
                if (!isEmpty(place)) {
                    place += ",";
                }
                place += location.getState();
            }
            if (!isEmpty(location.getCountry())) {
                holder.setCity(location.getCountry());
                if (!isEmpty(place)) {
                    place += ",";
                }
                place += location.getCountry();
            }
            if (!isEmpty(place)) {
                holder.setPlace(place);
            }
        }
    }

    private boolean isEmpty(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getCount() {
        int size = 0;
        if (data != null && data.getResponse() != null && data.getResponse().getVenues() != null) {
            size = data.getResponse().getVenues().size();
        }
        return size;
    }

    @Override
    public void onDestroy() {
        model.onDestroy();
    }

    @Override
    public void onClick(int position) {
        Location location = list.get(position).getLocation();
        if (location == null || location.getLng() == null || location.getLat() == null) {
            return;
        }
        String address = "loc:" + location.getLat() + "," + location.getLng() + " (" + list.get(position).getName() + ")";
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme("geo")
                .path("0,0")
                .appendQueryParameter("q", address);

        Intent intent = new Intent(Intent.ACTION_VIEW, uriBuilder.build());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(NearbyApp.getAppContext().getPackageManager()) != null) {
            NearbyApp.getAppContext().startActivity(intent);
        }
    }

}

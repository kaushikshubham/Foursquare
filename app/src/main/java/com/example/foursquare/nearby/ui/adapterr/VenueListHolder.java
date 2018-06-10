package com.example.foursquare.nearby.ui.adapterr;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foursquare.nearby.R;
import com.example.foursquare.nearby.utility.Logger;
import com.squareup.picasso.Picasso;

class VenueListHolder extends RecyclerView.ViewHolder implements IViewHolder {

    private static final String TAG = VenueListHolder.class.getSimpleName();
    private ImageView imageView;
    private TextView nameTextView, placeTextView;

    public VenueListHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
        nameTextView = itemView.findViewById(R.id.name);
        placeTextView = itemView.findViewById(R.id.place);
    }

    @Override
    public void setName(String name) {
        nameTextView.setText(name.trim());
    }

    @Override
    public void setImageUrl(String url) {
        Logger.v(TAG, "URL : " + url);
        Picasso.get().load(url).into(imageView);
    }

    @Override
    public void setCategory(String cat) {

    }

    @Override
    public void setCity(String city) {

    }

    @Override
    public void setState(String state) {

    }

    @Override
    public void setCountry(String country) {

    }

    @Override
    public void setPlace(String place) {
        placeTextView.setText(place.trim());
    }
}

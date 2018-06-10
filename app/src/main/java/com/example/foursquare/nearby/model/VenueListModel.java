package com.example.foursquare.nearby.model;

import com.example.foursquare.nearby.data.Data;
import com.example.foursquare.nearby.model.network.IResponseListener;
import com.example.foursquare.nearby.model.service.IApiService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Single;

public final class VenueListModel extends BaseModel<Data> implements IVenueListModel {
    private String queryLocString;

    public VenueListModel(IResponseListener<Data> listener) {
        super(listener);
    }

    @Override
    public void fetchData(String locString) {
        queryLocString = locString;
        fetchData();
    }


    @Override
    public Single<Data> startNetworkCall() {
        IApiService iApiService = getClient().create(IApiService.class);
        Map map = getQueryMap();
        map.put(NetworkConstant.KEY_LOCATION, queryLocString);
        return iApiService.getVenueList(map);
    }


    private Map getQueryMap() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(NetworkConstant.KEY_CLIENT_ID, NetworkConstant.CLIENT_ID);
        queryParams.put(NetworkConstant.KEY_CLIENT_SECRET, NetworkConstant.CLIENT_SECRET);
        queryParams.put(NetworkConstant.KEY_VERSION, new SimpleDateFormat("yyyyMMdd", Locale.UK).format(new Date()));
        return queryParams;
    }

}

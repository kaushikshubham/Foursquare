package com.example.foursquare.nearby.model.service;


import com.example.foursquare.nearby.data.Data;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface IApiService {

    @GET("venues/search")
    Single<Data> getVenueList(@QueryMap Map<String, String> map);
}

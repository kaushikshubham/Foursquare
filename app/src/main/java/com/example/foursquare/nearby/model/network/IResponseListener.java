package com.example.foursquare.nearby.model.network;

public interface IResponseListener<T> {

    /**
     * Method to return on success response
     *
     * @param response -- response
     */
    void onSuccessResponse(T response);

    /**
     * Method to return on Error
     *
     * @param errorCode
     */
    void onError(String errorCode);
}

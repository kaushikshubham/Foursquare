package com.example.foursquare.nearby.model;

import com.example.foursquare.nearby.NearbyApp;
import com.example.foursquare.nearby.R;
import com.example.foursquare.nearby.model.network.IResponseListener;
import com.example.foursquare.nearby.model.network.NetworkClient;
import com.example.foursquare.nearby.utility.Logger;
import com.example.foursquare.nearby.utility.Utils;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

abstract class BaseModel<T> implements IModel {

    private static final String TAG = BaseModel.class.getSimpleName();
    private IResponseListener<T> listener;
    private Disposable disposable;

    public BaseModel(IResponseListener<T> listener) {
        this.listener = listener;
    }

    protected Retrofit getClient() {
        return NetworkClient.getClient();
    }

    @Override
    public void onDestroy() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
        listener = null;
    }

    protected abstract Single<T> startNetworkCall();

    @Override
    public void fetchData() {
        if (!Utils.isNetworkAvailable()) {
            listener.onError(NearbyApp.getAppContext().getResources().getString(R.string.network_not_available));
        } else {
            startNetworkCall().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<T>() {
                @Override
                public void onSubscribe(Disposable d) {
                    disposable = d;
                }

                @Override
                public void onSuccess(T value) {
                    Logger.i(TAG, "" + value);
                    if (listener != null) {
                        listener.onSuccessResponse(value);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Logger.i(TAG, e.getMessage());
                    if (listener != null) {
                        listener.onError(e.getMessage());
                    }
                }
            });
        }
    }
}

package com.example.foursquare.nearby.presenter;

import com.example.foursquare.nearby.model.network.IResponseListener;
import com.example.foursquare.nearby.ui.IView;

public abstract class BasePresenter<T> implements IResponseListener<T> {
    protected IView view;

    protected BasePresenter() {
    }

    public void attachView(IView view) {
        this.view = view;
    }

    public void detachView() {
        view = null;
    }

    abstract void saveData(T value);

    @Override
    public void onSuccessResponse(T response) {
        saveData(response);
        notifyView();
    }

    protected void notifyView() {
        if(view != null) {
            view.dataDownLoaded();
        }
    }

    @Override
    public void onError(String errorCode) {
        if(view != null) {
            view.displayError(errorCode);
        }
    }
}

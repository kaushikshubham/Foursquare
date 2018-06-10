package com.example.foursquare.nearby.model.network;

import com.example.foursquare.nearby.model.NetworkConstant;
import com.example.foursquare.nearby.utility.Logger;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static final String TAG = NetworkClient.class.getSimpleName();

    private static Retrofit retrofit = null;
    private static int REQUEST_TIMEOUT = 60;

    /*
     * Provide retrofit client object to be used to make api call
     * */
    public static Retrofit getClient() {

        if (retrofit == null) {
            synchronized (NetworkClient.class) {
                if (retrofit == null) {
                    OkHttpClient okHttpClient = getOkHttpClient();
                    retrofit = new Retrofit.Builder()
                            .baseUrl(NetworkConstant.BASE_URL)
                            .client(okHttpClient)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }

    /*
     * creating okhttpclient
     * */
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                long currTime = System.currentTimeMillis();
                Response response = chain.proceed(request);
                Logger.i(TAG, "time taken by api : " + (System.currentTimeMillis() - currTime));
                return response;
            }
        });

        return httpClient.build();
    }
}

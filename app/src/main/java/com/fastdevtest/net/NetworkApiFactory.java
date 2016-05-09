
package com.fastdevtest.net;


import com.fastdevtest.net.api.NewsApi;
import com.fastdevtest.net.api.UserApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 通过此类可以获取network api的实例对象 Created by guojun on 16/4/18 10:51.
 */
public class NetworkApiFactory {
    private volatile static NetworkApiFactory sNetwork;

    private volatile UserApi mUserApi;

    private volatile NewsApi mNewsApi;

    private OkHttpClient mOkHttpClient;

    private static final long DEFAULT_TIME_OUT = 5;

    private Converter.Factory mGsonConverterFactory = GsonConverterFactory.create();

    private static CallAdapter.Factory mRxJavaCallAdapterFactory = RxJavaCallAdapterFactory
            .create();

    private NetworkApiFactory() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        mOkHttpClient = builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS).build();
    }

    public static synchronized NetworkApiFactory getInstance() {
        if (sNetwork == null) {
            sNetwork = new NetworkApiFactory();
        }
        return sNetwork;
    }

    /**
     * 获取用户相关net api
     *
     * @return UserApi
     */
    public synchronized UserApi getUserApi() {
        if (mUserApi == null) {
            Retrofit retrofit = new Retrofit.Builder().client(mOkHttpClient).baseUrl(Url.BASE_URL)
                    .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                    .addConverterFactory(mGsonConverterFactory).build();
            mUserApi = retrofit.create(UserApi.class);
        }
        return mUserApi;
    }

    /**
     * 获取新闻相关net api
     *
     * @return UserApi
     */
    public NewsApi getNewsApi() {
        if (mNewsApi == null) {
            Retrofit retrofit = new Retrofit.Builder().client(mOkHttpClient).baseUrl(Url.BASE_URL)
                    .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                    .addConverterFactory(mGsonConverterFactory).build();
            mNewsApi = retrofit.create(NewsApi.class);
        }
        return mNewsApi;
    }

}

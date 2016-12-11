
package com.fastdevtest.net;


import android.text.TextUtils;

import com.fastdevtest.net.api.INewsApi;
import com.fastdevtest.net.api.IUserApi;
import com.fastdevtest.net.api.OtherApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;

/**
 * 通过此类可以获取network api的实例对象 Created by guojun on 16/4/18 10:51.
 */
public class NetworkManager {

    private volatile IUserApi mUserApi;

    private volatile INewsApi mNewsApi;

    private volatile OtherApi mOtherApi;

    private OkHttpClient mOkHttpClient;

    private static final long DEFAULT_TIME_OUT = 5;

    private Converter.Factory mGsonConverterFactory = GsonConverterFactory.create();

    private static CallAdapter.Factory mRxJavaCallAdapterFactory = RxJavaCallAdapterFactory
            .create();

    private HashMap<String, ArrayList<Subscription>> mSubscriptionMap  ;

    private NetworkManager() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        mOkHttpClient = builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS).build();
        mSubscriptionMap=new HashMap<>();
    }

    /**
     * 用内部类实现单例
     */
    private static class NetworkApiProviderHolder {
        private final static NetworkManager NETWORKMANAGER = new NetworkManager();
    }

    public static synchronized NetworkManager getInstance() {
        return NetworkApiProviderHolder.NETWORKMANAGER;
    }

    /**
     * 获取用户相关net api
     *
     * @return IUserApi
     */
    public synchronized IUserApi getUserApi() {
        if (mUserApi == null) {
            Retrofit retrofit = new Retrofit.Builder().client(mOkHttpClient).baseUrl(Url.BASE_URL)
                    .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                    .addConverterFactory(mGsonConverterFactory).build();
            mUserApi = retrofit.create(IUserApi.class);
        }
        return mUserApi;
    }

    /**
     * 获取新闻相关net api
     *
     * @return IUserApi
     */
    public INewsApi getNewsApi() {
        if (mNewsApi == null) {
            Retrofit retrofit = new Retrofit.Builder().client(mOkHttpClient).baseUrl(Url.BASE_URL)
                    .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                    .addConverterFactory(mGsonConverterFactory).build();
            mNewsApi = retrofit.create(INewsApi.class);
        }
        return mNewsApi;
    }

    /**
     * 获取其它相关net api
     *
     * @return IUserApi
     */
    public OtherApi getOtherApi() {
        if (mOtherApi == null) {
            mOtherApi = createNetworkApi(Url.BASE_URL,OtherApi.class);
        }
        return mOtherApi;
    }

    public <T> T createNetworkApi(String baseUrl, Class<T> cls) {
        return new Retrofit.Builder().client(mOkHttpClient).baseUrl(baseUrl)
                .addCallAdapterFactory(mRxJavaCallAdapterFactory)
                .addConverterFactory(mGsonConverterFactory).build().create(cls);
    }

    public void addSubscription(String tag, Subscription subscription) {
        if (TextUtils.isEmpty(tag)) {
            tag = System.currentTimeMillis() + "";
        }
        if (mSubscriptionMap.containsKey(tag)) {
            mSubscriptionMap.get(tag).add(subscription);
        } else {
            ArrayList<Subscription> arrayList = new ArrayList<>();
            arrayList.add(subscription);
            mSubscriptionMap.put(tag, arrayList);
        }
    }

    public void addSubscription(Subscription subscription) {
        addSubscription(null, subscription);
    }



    public void unSubsription(String tag){
        ArrayList<Subscription> value= mSubscriptionMap.get(tag);
        if(value!=null&&!value.isEmpty()){
            for (int i = 0; i < value.size(); i++) {
                Subscription subscription=value.get(i);
                subscription.unsubscribe();
            }
        }
    }

}

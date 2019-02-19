package com.wd.tech.https;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2019/1/2 0002.
 */
public class NetWorkManager {
    private String INSIDE_BASE_URL = "https://172.17.8.100/techApi/";
    private String OUTSIDE_BASE_URL = "https://mobile.bwstudent.com/techApi/";
    private static NetWorkManager instance;
    private Retrofit retrofit;


    private NetWorkManager() {
        init();
    }

    //单列模式
    public static NetWorkManager getInstance(){

        if (instance == null) {
            instance = new NetWorkManager();
        }

        return instance;
    }

    private void init() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .writeTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSlSocketFactory(SSLSocketClient.getInputStream()))
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())
                .readTimeout(20, TimeUnit.SECONDS).build();


        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(OUTSIDE_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }
    public <T> T create(final Class<T> service){
        return retrofit.create(service);
    }
}

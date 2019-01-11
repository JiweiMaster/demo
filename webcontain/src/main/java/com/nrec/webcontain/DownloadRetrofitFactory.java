package com.nrec.webcontain;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 18099 on 2018/12/26.
 */

public class DownloadRetrofitFactory extends CommonNetManager {
    private static DownloadRetrofitFactory factory;
    public static synchronized DownloadRetrofitFactory getInstance(){
        if(factory == null){
            factory = new DownloadRetrofitFactory();
        }
        return factory;
    }

    public Retrofit initRetrofit(String urlStr) {
        return retrofit = new Retrofit.Builder()
                .client(initOkHttpClient())
                .baseUrl(urlStr)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}

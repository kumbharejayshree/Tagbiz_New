package com.tagloy.tagbiz.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 3/24/2021.
 */

public class ApiManager {
    private static Retrofit retrofit = null;
    private static Retrofit retrofitFb = null;
    public static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();
    public static ApiService getClient(){

        // change your base URL
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl("https://biz.tagloy.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //Creating object for our interface
        ApiService api = retrofit.create(ApiService.class);
        return api; // return the APIInterface object
    }



}

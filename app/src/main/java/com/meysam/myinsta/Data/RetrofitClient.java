package com.meysam.myinsta.Data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance;
    private Retrofit retrofit;

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }


    private RetrofitClient() {
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.1.6/advanced/")
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public api getApi() {
        return retrofit.create(api.class);
    }

}

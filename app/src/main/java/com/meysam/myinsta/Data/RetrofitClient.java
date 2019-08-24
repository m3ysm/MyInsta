package com.meysam.myinsta.Data;

import android.content.Context;

import com.meysam.myinsta.R;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance;
    private Retrofit retrofit;

    public static RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }


    private RetrofitClient(Context context) {
        retrofit = new Retrofit.Builder().baseUrl(context.getString(R.string.server_address))
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    public api getApi() {
        return retrofit.create(api.class);
    }

}

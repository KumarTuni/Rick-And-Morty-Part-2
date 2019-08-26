package com.example.rickandmorty.model.repositories.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rohin on 5/11/2019 @8:16 AM.
 */
public class RetrofitClient {

    private static RetrofitClient instance;
    private Retrofit retrofit;

    public static synchronized RetrofitClient getInstance() {
        if (instance == null)
            instance = new RetrofitClient();
        return instance;
    }

    public RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://rickandmortyapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public API getApi(){
       return retrofit.create(API.class);
    }
}

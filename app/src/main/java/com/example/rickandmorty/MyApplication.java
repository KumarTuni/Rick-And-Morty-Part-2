package com.example.rickandmorty;

import android.app.Application;

import com.example.rickandmorty.model.repositories.db.MyDatabase;

/**
 * Created by Rohin on 8/24/2019 @10:22 AM.
 */
public class MyApplication extends Application {

    private static MyApplication instance;

    public static MyApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}

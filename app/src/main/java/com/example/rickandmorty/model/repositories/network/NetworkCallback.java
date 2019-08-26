package com.example.rickandmorty.model.repositories.network;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;

/**
 * Created by Rohin on 8/24/2019 @7:14 PM.
 */
public abstract class NetworkCallback<T> {

    public abstract void loading();

    public abstract void success(@NonNull T item);

    public abstract void faild(String errMsg);

    public abstract LiveData<T> loadFromDb();

    public abstract Call<T> createCall();



}

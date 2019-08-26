package com.example.rickandmorty.model.repositories.network.paging;

import com.example.rickandmorty.model.repositories.network.NetworkCallback;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

/**
 * Created by Rohin on 5/25/2019 @1:00 PM.
 */
public class ItemDataSourceFactory<Value> extends DataSource.Factory<Integer, Value> {

    private NetworkCallback<ListAPIResponse<Value>> networkCallback;
    private MutableLiveData<ItemDataSource<Value>> sourceMutableLiveData;

    public ItemDataSourceFactory(NetworkCallback<ListAPIResponse<Value>> networkCallback) {
        this.networkCallback = networkCallback;
        this.sourceMutableLiveData = new MediatorLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer, Value> create() {
        ItemDataSource<Value> itemDataSource = new ItemDataSource<>(networkCallback);
        sourceMutableLiveData.postValue(itemDataSource);

        return itemDataSource;
    }

    public MutableLiveData<ItemDataSource<Value>> getSourceMutableLiveData() {
        return sourceMutableLiveData;
    }
}

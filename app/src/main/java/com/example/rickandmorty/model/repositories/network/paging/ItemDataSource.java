package com.example.rickandmorty.model.repositories.network.paging;

import android.text.TextUtils;

import com.example.rickandmorty.model.repositories.network.NetworkCallback;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rohin on 5/25/2019 @1:00 PM.
 */
public class ItemDataSource<Value> extends PageKeyedDataSource<Integer, Value> {

    private NetworkCallback<ListAPIResponse<Value>> networkCallback;

    private LoadParams<Integer> loadParams;
    private LoadInitialCallback<Integer, Value> initialCallback;
    private LoadCallback<Integer, Value> afterCallback;
    private LoadInitialParams<Integer> integerLoadInitialParams;

    public ItemDataSource(NetworkCallback<ListAPIResponse<Value>> networkCallback) {
        this.networkCallback = networkCallback;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Value> callback) {
        this.integerLoadInitialParams = params;
        this.initialCallback = callback;
        networkCallback.loading();
        Call<ListAPIResponse<Value>> call = networkCallback.createCall();
        if (call != null)
            call.enqueue(new Callback<ListAPIResponse<Value>>() {
                @Override
                public void onResponse(Call<ListAPIResponse<Value>> call, Response<ListAPIResponse<Value>> response) {
                    if (response.body() == null || !response.isSuccessful()) {
                        networkCallback.faild("Error " + response.code() + " " + response.message());
                    } else {

                        if (response.body().getResults() == null)
                            response.body().setResults(new ArrayList<>());
                        Integer nextK = 1;
                        if (TextUtils.isEmpty(response.body().getInfo().getNext()))
                            nextK = null;

                        callback.onResult(response.body().getResults(), 0, response.body().getInfo().getCount(), null, nextK);
                        networkCallback.success(response.body());

                    }
                }

                @Override
                public void onFailure(Call<ListAPIResponse<Value>> call, Throwable t) {
                    networkCallback.faild(t.getLocalizedMessage());
                    networkCallback.loadFromDb();
                }
            });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Value> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Value> callback) {
        this.loadParams = params;
        this.afterCallback = callback;
        networkCallback.loading();

        Call<ListAPIResponse<Value>> call = networkCallback.createCall();
        if (call != null)
            call.enqueue(new Callback<ListAPIResponse<Value>>() {
                @Override
                public void onResponse(Call<ListAPIResponse<Value>> call, Response<ListAPIResponse<Value>> response) {
                    if (response.body() == null || !response.isSuccessful()) {
                        networkCallback.faild("Error " + response.code() + " " + response.message());
                    } else {

                        if (response.body().getResults() == null)
                            response.body().setResults(new ArrayList<>());
                        Integer nextK = params.key + 1;
                        if (TextUtils.isEmpty(response.body().getInfo().getNext()))
                            nextK = null;
                        callback.onResult(response.body().getResults(), nextK);
                        networkCallback.success(response.body());

                    }
                }

                @Override
                public void onFailure(Call<ListAPIResponse<Value>> call, Throwable t) {
                    networkCallback.faild(t.getLocalizedMessage());
                }
            });
    }

    public void reloadInitial() {
        loadInitial(integerLoadInitialParams, initialCallback);
    }

    public void reloadAfter() {
        loadAfter(loadParams, afterCallback);

    }
}

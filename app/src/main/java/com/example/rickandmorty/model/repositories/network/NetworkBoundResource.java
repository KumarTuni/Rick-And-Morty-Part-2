package com.example.rickandmorty.model.repositories.network;

import android.os.AsyncTask;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rohin on 5/17/2019 @1:50 AM.
 */
public abstract class NetworkBoundResource<ResultType> {

    private final MediatorLiveData<APIResponse<ResultType>> result = new MediatorLiveData<>();
    public final boolean shouldFetchFromNetwork;
    public final boolean shouldSaveToDB;
    public final boolean shouldFetchFromDB;


    public NetworkBoundResource(boolean shouldFetch, boolean shouldSaveToDB, boolean shouldFetchDB) {
        this.shouldFetchFromNetwork = shouldFetch;
        this.shouldSaveToDB = shouldSaveToDB;
        this.shouldFetchFromDB = shouldFetchDB;
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        if (this.shouldFetchFromDB)
            result.addSource(dbSource, newData -> result.setValue(APIResponse.loading(newData)));
        else
            result.setValue(APIResponse.loading(null));
        Call<ResultType> call = createCall();
        if (call != null)
            call.enqueue(new Callback<ResultType>() {
                @Override
                public void onResponse(Call<ResultType> call, Response<ResultType> response) {
                    if (shouldFetchFromDB)
                        result.removeSource(dbSource);
                    if (response.body() == null || !response.isSuccessful()) {
                        if (shouldFetchFromDB)
                            result.addSource(dbSource, newData -> result.setValue(APIResponse.error("Error " + response.code() + " " + response.message(), newData)));
                        else
                            result.setValue(APIResponse.error("Error " + response.code() + " " + response.message(), null));
                    } else {
                        if (shouldSaveToDB) {
                            saveResultAndReInit(response.body());
                            if (!shouldFetchFromDB)
                                result.setValue(APIResponse.success(response.body()));

                        } else {
                            if (shouldFetchFromDB)
                                result.addSource(dbSource, newData -> result.setValue(APIResponse.success(newData)));
                            else
                                result.setValue(APIResponse.success(response.body()));

                        }
                    }
                }

                @Override
                public void onFailure(Call<ResultType> call, Throwable t) {
                    result.removeSource(dbSource);
                    if (shouldFetchFromDB)
                        result.addSource(dbSource, newData -> result.setValue(APIResponse.error(t.getMessage(), newData)));
                    else {
                        onFetchFailed(t.getLocalizedMessage());
                    }

                }
            });

    }

    public void saveResultAndReInit(ResultType response) {
        SaveDataTask task = new SaveDataTask();
        SaveDataTaskWithoutLoadDB taskWithoutLoadDB = new SaveDataTaskWithoutLoadDB();
        if (shouldFetchFromDB) {
            //noinspection unchecked
            task.execute(response);
        } else {
            //noinspection unchecked
            taskWithoutLoadDB.execute(response);
        }
    }


    protected boolean shouldFetch(@Nullable ResultType data) {
        return true;
    }


    public abstract void saveCallResult(@NonNull ResultType item);

    public abstract void onFetchFailed(String errMsg);

    public abstract LiveData<ResultType> loadFromDb();

    public abstract Call<ResultType> createCall();

    public final MutableLiveData<APIResponse<ResultType>> getAsLiveData() {
        return result;
    }


    private class SaveDataTask extends AsyncTask<ResultType, Void, Void> {
        @SafeVarargs
        @Override
        protected final Void doInBackground(ResultType... response) {
            saveCallResult(response[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            result.addSource(loadFromDb(), newData -> result.setValue(APIResponse.success(newData)));
        }
    }

    private class SaveDataTaskWithoutLoadDB extends AsyncTask<ResultType, Void, Void> {

        @SafeVarargs
        @Override
        protected final Void doInBackground(ResultType... response) {
            saveCallResult(response[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
        }
    }

    public void runRequest() {
        if (this.shouldFetchFromDB) {
            LiveData<ResultType> dbSource = loadFromDb();
            result.addSource(dbSource, data -> {
                result.removeSource(dbSource);
                if (this.shouldFetchFromNetwork) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, newData -> result.setValue(APIResponse.success(newData)));
                }
            });
        } else {
            fetchFromNetwork(null);
        }
    }

}

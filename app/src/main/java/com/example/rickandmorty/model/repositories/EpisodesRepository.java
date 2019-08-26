package com.example.rickandmorty.model.repositories;

import com.example.rickandmorty.MyApplication;
import com.example.rickandmorty.model.data.Episode;
import com.example.rickandmorty.model.repositories.db.MyDatabase;
import com.example.rickandmorty.model.repositories.network.APIResponse;
import com.example.rickandmorty.model.repositories.network.NetworkCallback;
import com.example.rickandmorty.model.repositories.network.RetrofitClient;
import com.example.rickandmorty.model.repositories.network.paging.ItemDataSource;
import com.example.rickandmorty.model.repositories.network.paging.ItemDataSourceFactory;
import com.example.rickandmorty.model.repositories.network.paging.ListAPIResponse;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import retrofit2.Call;

/**
 * Created by Rohin on 8/24/2019 @7:36 PM.
 */
public class EpisodesRepository {

    private final MyDatabase database;
    private PagedList<Episode> episodePagedList;
    private LiveData<PagedList<Episode>> epesodePagedListLive;
    private LiveData<ItemDataSource<Episode>> itemDataSourceLiveData;
    private MutableLiveData<APIResponse<ListAPIResponse<Episode>>> networkStatusLiveData;

    private ItemDataSourceFactory<Episode> factory;
    private DataSource.Factory<Integer, Episode> factoryLocale;
    private PagedList.Config config;
    private MediatorLiveData<PagedList<Episode>> liveDataMerger;

    private NetworkCallback<ListAPIResponse<Episode>> networkCallback;

    private int page = 1;

    public EpisodesRepository() {
        this.database = MyDatabase.getInstance(MyApplication.getInstance());

        liveDataMerger = new MediatorLiveData<>();
        networkStatusLiveData = new MutableLiveData<>();

        networkCallback = new NetworkCallback<ListAPIResponse<Episode>>() {
            @Override
            public void loading() {
                networkStatusLiveData.postValue(APIResponse.loading(new ListAPIResponse<>(page)));
            }

            @Override
            public void success(@NonNull ListAPIResponse<Episode> item) {
                networkStatusLiveData.setValue(APIResponse.success(item));
                page++;
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() ->
                database.episodeDao().insertEpisodes(item.getResults()));
            }

            @Override
            public void faild(String errMsg) {
                networkStatusLiveData.postValue(APIResponse.error(errMsg, new ListAPIResponse<>(page)));
            }

            @Override
            public LiveData<ListAPIResponse<Episode>> loadFromDb() {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    factoryLocale = database.episodeDao().loadAllPaged();
                    episodePagedList = new PagedList.Builder<>(factoryLocale.create(), config)
                            .setInitialKey(0)
                            .setFetchExecutor(Executors.newSingleThreadExecutor())
                            .setNotifyExecutor(com.bumptech.glide.util.Executors.mainThreadExecutor())
                            .build();
                    liveDataMerger.postValue(episodePagedList);

                });
                return null;
            }

            @Override
            public Call<ListAPIResponse<Episode>> createCall() {
                return RetrofitClient.getInstance().getApi().getEpisodes(page);
            }
        };

        factory = new ItemDataSourceFactory<>(networkCallback);
        itemDataSourceLiveData = factory.getSourceMutableLiveData();
        config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPrefetchDistance(1)
                .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build();
        Executor executor = Executors.newFixedThreadPool(5);
        epesodePagedListLive = new LivePagedListBuilder<>(factory, config)
                .setFetchExecutor(executor)
                .setInitialLoadKey(0)
                .setBoundaryCallback(new PagedList.BoundaryCallback<Episode>() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        networkStatusLiveData.setValue(APIResponse.empty(new ListAPIResponse<>()));
                    }
                }).build();

        liveDataMerger.addSource(epesodePagedListLive, episodes ->
                liveDataMerger.setValue(episodes));

    }

    public void loadInitialPage() {
        reloadInitialPage();
    }

    private void reloadInitialPage() {
        page = 1;
        epesodePagedListLive.getValue().getDataSource().invalidate();
    }

    private void reloaodPage() {
        Objects.requireNonNull(itemDataSourceLiveData.getValue()).reloadAfter();
    }

    public void retry() {
        if (page == 1)
            reloadInitialPage();
        else if (page > 1)
            reloaodPage();
    }

    public PagedList<Episode> getEpisodePagedList() {
        return episodePagedList;
    }

    public LiveData<PagedList<Episode>> getEpesodePagedListLive() {
        return epesodePagedListLive;
    }

    public LiveData<ItemDataSource<Episode>> getItemDataSourceLiveData() {
        return itemDataSourceLiveData;
    }

    public MutableLiveData<APIResponse<ListAPIResponse<Episode>>> getNetworkStatusLiveData() {
        return networkStatusLiveData;
    }

    public ItemDataSourceFactory<Episode> getFactory() {
        return factory;
    }

    public DataSource.Factory<Integer, Episode> getFactoryLocale() {
        return factoryLocale;
    }

    public PagedList.Config getConfig() {
        return config;
    }

    public MediatorLiveData<PagedList<Episode>> getLiveDataMerger() {
        return liveDataMerger;
    }

    public NetworkCallback<ListAPIResponse<Episode>> getNetworkCallback() {
        return networkCallback;
    }
}

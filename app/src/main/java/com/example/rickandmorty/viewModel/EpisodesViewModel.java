package com.example.rickandmorty.viewModel;

import com.example.rickandmorty.model.data.Episode;
import com.example.rickandmorty.model.repositories.EpisodesRepository;
import com.example.rickandmorty.model.repositories.network.APIResponse;
import com.example.rickandmorty.model.repositories.network.paging.ListAPIResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

/**
 * Created by Rohin on 8/24/2019 @8:00 PM.
 */
public class EpisodesViewModel extends ViewModel {
    private EpisodesRepository repository;

    private LiveData<PagedList<Episode>> episodesListLiveData;
    private LiveData<APIResponse<ListAPIResponse<Episode>>> networkLiveData;

    public EpisodesViewModel() {
        repository = new EpisodesRepository();
        episodesListLiveData = repository.getEpesodePagedListLive();
        networkLiveData = repository.getNetworkStatusLiveData();

    }

    public LiveData<PagedList<Episode>> getEpisodesListLiveData() {
        return episodesListLiveData;
    }

    public LiveData<APIResponse<ListAPIResponse<Episode>>> getNetworkLiveData() {
        return networkLiveData;
    }

    public void loadInitialPage(){
        repository.loadInitialPage();
    }

    public void retry(){
        repository.retry();
    }

}

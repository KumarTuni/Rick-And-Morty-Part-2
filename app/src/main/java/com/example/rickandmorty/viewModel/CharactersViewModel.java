package com.example.rickandmorty.viewModel;

import com.example.rickandmorty.model.data.Character;
import com.example.rickandmorty.model.repositories.EpisodeDetailsRepository;
import com.example.rickandmorty.model.repositories.network.APIResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/**
 * Created by Rohin on 8/25/2019 @5:09 AM.
 */
public class CharactersViewModel extends ViewModel {

    private EpisodeDetailsRepository repository;
    private LiveData<APIResponse<List<Character>>> networkResponse;

    public CharactersViewModel(){
        repository = new EpisodeDetailsRepository();
        networkResponse = repository.getNetworkResponse();
    }

    public LiveData<APIResponse<List<Character>>> getNetworkResponse(){
        return networkResponse;
    }

    public void loadCharacters(String ids,int episodeId){
        repository.getCharacters(ids,episodeId);
    }
}

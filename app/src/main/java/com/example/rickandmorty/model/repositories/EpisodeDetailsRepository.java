package com.example.rickandmorty.model.repositories;

import com.example.rickandmorty.MyApplication;
import com.example.rickandmorty.model.data.Character;
import com.example.rickandmorty.model.repositories.db.MyDatabase;
import com.example.rickandmorty.model.repositories.network.APIResponse;
import com.example.rickandmorty.model.repositories.network.NetworkBoundResource;
import com.example.rickandmorty.model.repositories.network.RetrofitClient;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import retrofit2.Call;

/**
 * Created by Rohin on 8/25/2019 @5:01 AM.
 */
public class EpisodeDetailsRepository {

    private final MyDatabase database;
    private NetworkBoundResource<List<Character>> charactersNBR;

    private String ids;
    private int episodeId;

    public EpisodeDetailsRepository() {
        database = MyDatabase.getInstance(MyApplication.getInstance());
        charactersNBR = new NetworkBoundResource<List<Character>>(true,true,true) {
            @Override
            public void saveCallResult(@NonNull List<Character> item) {
                database.characterDao().insertCharaters(item);
            }

            @Override
            public void onFetchFailed(String errMsg) {

            }

            @Override
            public LiveData<List<Character>> loadFromDb() {
                return database.characterDao().loadAllLive(episodeId);
            }

            @Override
            public Call<List<Character>> createCall() {
                return RetrofitClient.getInstance()
                        .getApi()
                        .getEpisodeCharacters(ids);
            }
        };


    }

    public void getCharacters(String ids,int episodeId){
        this.ids = ids;
        this.episodeId = episodeId;
        charactersNBR.runRequest();
    }

    public LiveData<APIResponse<List<Character>>> getNetworkResponse(){
        return charactersNBR.getAsLiveData();
    }
}

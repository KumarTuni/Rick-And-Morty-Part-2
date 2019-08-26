package com.example.rickandmorty.model.repositories.network;

import com.example.rickandmorty.model.data.Character;
import com.example.rickandmorty.model.data.Episode;
import com.example.rickandmorty.model.repositories.network.paging.ListAPIResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Rohin on 5/11/2019 @8:06 AM.
 */
public interface API {


    @GET("api/episode")
    Call<ListAPIResponse<Episode>> getEpisodes(
            @Query("page") int page
    );

    @GET("api/episode/{ids}")
    Call<ListAPIResponse<Episode>> getEpidodesByIds(
            @Path("ids") String ids,
            @Query("page") int page
    );


    @GET("api/character/{ids}")
    Call<List<Character>> getEpisodeCharacters(
            @Path("ids") String ids);

}

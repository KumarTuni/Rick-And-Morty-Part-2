package com.example.rickandmorty.model.repositories.db;

import com.example.rickandmorty.MyApplication;
import com.example.rickandmorty.model.data.Episode;
import com.example.rickandmorty.model.data.EpisodeCharacter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Query;

/**
 * Created by Rohin on 8/24/2019 @4:19 PM.
 */

@Dao
public abstract class EpisodeDao implements BaseDao<Episode> {

    @Query("SELECT * FROM episodes ORDER BY id ASC")
    public abstract List<Episode> loadAll();

    @Query("SELECT * FROM episodes ORDER BY id ASC")
    public abstract LiveData<List<Episode>> loadAllLive();

    @Query("SELECT * FROM episodes ORDER BY id ASC")
    public abstract DataSource.Factory<Integer, Episode> loadAllPaged();

    public void insertEpisodes(List<Episode> episodes) {
        List<EpisodeCharacter> episodeCharacters = new ArrayList<>();
        for (Episode episode : episodes)
            for (String characterUrl : episode.getCharacters())
                episodeCharacters.add(new EpisodeCharacter(episode.getUrl(), characterUrl));
        insert(episodes);
        MyDatabase.getInstance(MyApplication.getInstance())
                .episodeCharacterDao()
                .insert(episodeCharacters);
    }

}

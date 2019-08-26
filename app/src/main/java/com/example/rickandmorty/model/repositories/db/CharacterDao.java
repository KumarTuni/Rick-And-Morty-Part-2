package com.example.rickandmorty.model.repositories.db;

import com.example.rickandmorty.MyApplication;
import com.example.rickandmorty.model.data.Character;
import com.example.rickandmorty.model.data.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 * Created by Rohin on 8/24/2019 @4:19 PM.
 */

@Dao
public abstract class CharacterDao implements BaseDao<Character> {

    @Query("SELECT * FROM characters ORDER BY id ASC")
    public abstract List<Character> loadAll();


    @Query("SELECT * FROM characters ORDER BY id ASC")
    public abstract DataSource.Factory<Integer, Character> loadAllPaged();

    @Query("SELECT * FROM " +
            "characters INNER JOIN episode_characters ON characters.id = episode_characters.characterId " +
            "WHERE episode_characters.episodeId = :episodeId")
    public abstract List<Character> loadAllForEpisode(int episodeId);

    public Character load(Character character) {

        character.setOrigin(
                MyDatabase.getInstance(MyApplication.getInstance())
                        .locationDao()
                        .load(character.getOriginId()));

        character.setLocation(
                MyDatabase.getInstance(MyApplication.getInstance())
                        .locationDao()
                        .load(character.getLocationId()));

        return character;
    }

    public LiveData<List<Character>> loadAllLive(int episodeId) {
        MutableLiveData<List<Character>> res = new MutableLiveData<>();
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Character> characters = loadAllForEpisode(episodeId);
            for (Character character : characters) {
                character.setOrigin(MyDatabase.getInstance(MyApplication.getInstance()).locationDao().load(character.getOriginId()));
                character.setLocation(MyDatabase.getInstance(MyApplication.getInstance()).locationDao().load(character.getLocationId()));
            }
            res.postValue(characters);
        });

        return res;
    }

    public void insertCharacter(Character character) {
        character.setLocationId(character.getLocation().getUrl());
        character.setOriginId(character.getOrigin().getUrl());

        MyDatabase.getInstance(MyApplication.getInstance())
                .locationDao()
                .insert(character.getLocation());

        MyDatabase.getInstance(MyApplication.getInstance())
                .locationDao()
                .insert(character.getOrigin());

        insert(character);
    }

    public void insertCharaters(List<Character> characters) {
        List<Location> locations = new ArrayList<>();
        for (Character character : characters) {
            character.setOriginId(character.getOrigin().getUrl());
            character.setLocationId(character.getLocation().getUrl());
            character.getOrigin().setId(character.getOriginId());
            character.getLocation().setId(character.getLocationId());
            locations.add(character.getOrigin());
            locations.add(character.getLocation());
        }
        MyDatabase.getInstance(MyApplication.getInstance())
                .locationDao()
                .insert(locations);
        insert(characters);
    }
}

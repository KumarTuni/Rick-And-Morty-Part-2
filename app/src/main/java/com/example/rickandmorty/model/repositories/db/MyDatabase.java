package com.example.rickandmorty.model.repositories.db;

import android.app.Application;

import com.example.rickandmorty.model.data.Character;
import com.example.rickandmorty.model.data.Episode;
import com.example.rickandmorty.model.data.EpisodeCharacter;
import com.example.rickandmorty.model.data.Location;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Created by Rohin on 8/24/2019 @4:11 PM.
 */
@Database(exportSchema = false,
        entities = {
                Character.class,
                Episode.class,
                EpisodeCharacter.class,
                Location.class
        }, version = 3)
public abstract class MyDatabase extends RoomDatabase {

    private static MyDatabase instance;

    public static synchronized MyDatabase getInstance(Application application) {

        if (instance == null)
            instance = Room.databaseBuilder(application,
                    MyDatabase.class, "rick_and_morty_db")
                    .fallbackToDestructiveMigration()
                    .build();
        return instance;
    }

    public abstract LocationDao locationDao();
    public abstract CharacterDao characterDao();
    public abstract EpisodeDao episodeDao();
    public abstract EpisodeCharacterDao episodeCharacterDao();
}

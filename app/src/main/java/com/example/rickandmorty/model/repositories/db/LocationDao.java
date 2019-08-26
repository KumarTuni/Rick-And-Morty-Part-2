package com.example.rickandmorty.model.repositories.db;

import com.example.rickandmorty.model.data.Location;

import androidx.room.Dao;
import androidx.room.Query;

/**
 * Created by Rohin on 8/24/2019 @4:38 PM.
 */
@Dao
public abstract class LocationDao implements BaseDao<Location> {

    @Query("SELECT * FROM locations WHERE id = :locationId")
    public abstract Location load(int locationId);
}

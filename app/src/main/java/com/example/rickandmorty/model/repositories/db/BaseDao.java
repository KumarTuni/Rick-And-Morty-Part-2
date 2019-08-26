package com.example.rickandmorty.model.repositories.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

/**
 * Created by Rohin on 8/24/2019 @4:20 PM.
 */

public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T data);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<T> data);


}

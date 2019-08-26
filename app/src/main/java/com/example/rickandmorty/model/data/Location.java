package com.example.rickandmorty.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.Index;

/**
 * Created by Rohin on 8/24/2019 @3:54 PM.
 */
@Entity(tableName = "locations",
        primaryKeys = "id",
        indices = {
                @Index(value = "id", unique = true)
        })
public class Location {

    @Expose(deserialize = false,serialize = false)
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

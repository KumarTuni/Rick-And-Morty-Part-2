package com.example.rickandmorty.model.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

/**
 * Created by Rohin on 8/24/2019 @3:29 PM.
 */
@Entity(tableName = "episodes",
        primaryKeys = {"id"},
        indices = {
                @Index(value = "url", unique = true),
                @Index(value = "id",unique = true)
        })
public class Episode {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("air_date")
    private String airDate;
    @SerializedName("episode")
    private String episode;
    @Ignore
    @SerializedName("characters")
    private List<String> characters;
    @SerializedName("url")
    private String url;
    @SerializedName("created")
    private String created;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getEpisode() {
        return episode;
    }

    public void setEpisode(String episode) {
        this.episode = episode;
    }

    public List<String> getCharacters() {
        return characters;
    }

    public void setCharacters(List<String> characters) {
        this.characters = characters;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.getId() == ((Episode)obj).getId();
    }
}

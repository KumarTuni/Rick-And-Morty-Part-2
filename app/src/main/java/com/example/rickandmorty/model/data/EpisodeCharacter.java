package com.example.rickandmorty.model.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

/**
 * Created by Rohin on 8/24/2019 @4:04 PM.
 */

@Entity(tableName = "episode_characters",
        primaryKeys = {"episodeId", "characterId"},

        indices = {
                @Index(value = "episodeId"),
                @Index(value = "characterId")
        })
public class EpisodeCharacter {

    private int episodeId;
    private int characterId;

    public EpisodeCharacter() {
    }

    @Ignore
    public EpisodeCharacter(String episodeUrl, String characterUrl) {
        this.episodeId = Integer.valueOf(episodeUrl.substring(episodeUrl.lastIndexOf("/")+1));
        this.characterId = Integer.valueOf(characterUrl.substring(characterUrl.lastIndexOf("/")+1));
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(int episodeId) {
        this.episodeId = episodeId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }
}

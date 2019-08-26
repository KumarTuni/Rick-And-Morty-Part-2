package com.example.rickandmorty.model.data;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.Nullable;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

/**
 * Created by Rohin on 8/24/2019 @3:50 PM.
 */

@Entity(tableName = "characters",
        primaryKeys = "id",
        foreignKeys = {
                @ForeignKey(
                        entity = Location.class,
                        parentColumns = "id",
                        childColumns = "originId",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Location.class,
                        parentColumns = "id",
                        childColumns = "locationId",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "url", unique = true),
                @Index(value = "id", unique = true)
        })
public class Character {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;
    @SerializedName("species")
    private String special;
    @SerializedName("type")
    private String type;
    @SerializedName("gender")
    private String gender;
    @SerializedName("image")
    private String image;
    @SerializedName("url")
    private String url;
    @SerializedName("created")
    private String created;

    @Expose(serialize = false, deserialize = false)
    private int originId;
    @Expose(serialize = false, deserialize = false)
    private int locationId;

    @Ignore
    @SerializedName("origin")
    private Location origin;

    @Ignore
    @SerializedName("location")
    private Location location;


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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Location getOrigin() {
        return origin;
    }

    public void setOrigin(Location origin) {
        this.origin = origin;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getOriginId() {
        return originId;
    }

    public void setOriginId(int originId) {
        this.originId = originId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public void setLocationId(String url) {
        if (!TextUtils.isEmpty(url))
            this.locationId = Integer.valueOf(url.substring(url.lastIndexOf("/") + 1));
    }

    public void setOriginId(String url) {
        if (!TextUtils.isEmpty(url))
            this.originId = Integer.valueOf(url.substring(url.lastIndexOf("/") + 1));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return this.getId() == ((Character) obj).getId();
    }
}

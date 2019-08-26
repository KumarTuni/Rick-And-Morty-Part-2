package com.example.rickandmorty.model.repositories.network.paging;

import com.example.rickandmorty.model.data.Info;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rohin on 8/24/2019 @6:51 PM.
 */
public class ListAPIResponse<T> {

    @SerializedName("info")
    private Info info;

    @SerializedName("results")
    private List<T> results;

    public Info getInfo() {
        return info;
    }

    public ListAPIResponse() {
        this.info = new Info();
        this.results = new ArrayList<>();
    }

    public ListAPIResponse(int count) {
        this();
        info.setPageIdx(count);
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}

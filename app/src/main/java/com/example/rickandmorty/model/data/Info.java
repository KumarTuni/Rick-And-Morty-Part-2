package com.example.rickandmorty.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rohin on 8/24/2019 @6:52 PM.
 */
public class Info {

    @SerializedName("count")
    private int count;
    @SerializedName("pages")
    private int pages;
    @SerializedName("next")
    private String next;
    @SerializedName("prev")
    private String prev;
    @Expose(serialize = false, deserialize = false)
    private int pageIdx;

    public Info(){

    }

    public Info(int pageIdx){
        this.pageIdx = pageIdx;
    }

    public Info(int count, int pages, String next, String prev, int pageIdx) {
        this.count = count;
        this.pages = pages;
        this.next = next;
        this.prev = prev;
        this.pageIdx = pageIdx;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public int getPageIdx() {
        return pageIdx;
    }

    public void setPageIdx(int pageIdx) {
        this.pageIdx = pageIdx;
    }
}

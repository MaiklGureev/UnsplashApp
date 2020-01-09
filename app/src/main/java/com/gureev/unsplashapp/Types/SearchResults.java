package com.gureev.unsplashapp.Types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gureev.unsplashapp.Types.Photo;

import java.util.List;

public class SearchResults {

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("results")
    @Expose
    private List<Photo> results = null;

    public List<Photo> getResults() {
        return results;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

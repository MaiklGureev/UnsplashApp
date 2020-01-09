package com.gureev.unsplashapp.Types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Collection implements Serializable {

    @SerializedName("id")
    @Expose
    private String id = "";

    @SerializedName("title")
    @Expose
    private String title= "";

    @SerializedName("description")
    @Expose
    private String description= "";

    @SerializedName("total_photos")
    @Expose
    private String total_photos= "";

    public Collection(String id, String title, String description, String total_photos) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.total_photos = total_photos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        if(description==null)
            return "empty";
        else
            return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal_photos() {
        return total_photos;
    }

    public void setTotal_photos(String total_photos) {
        this.total_photos = total_photos;
    }
}


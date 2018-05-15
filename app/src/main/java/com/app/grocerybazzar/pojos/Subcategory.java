package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gaurav.garg on 02-11-2017.
 */

public class Subcategory implements Serializable {
    @SerializedName("subcat_id")
    @Expose
    private String subcatId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subcat_icon")
    @Expose
    private String subcatIcon;

    /**
     * No args constructor for use in serialization
     *
     */
    public Subcategory() {
    }

    /**
     *
     * @param title
     * @param subcatId
     * @param subcatIcon
     */
    public Subcategory(String subcatId, String title, String subcatIcon) {
        super();
        this.subcatId = subcatId;
        this.title = title;
        this.subcatIcon = subcatIcon;
    }

    public String getSubcatId() {
        return subcatId;
    }

    public void setSubcatId(String subcatId) {
        this.subcatId = subcatId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubcatIcon() {
        return subcatIcon;
    }

    public void setSubcatIcon(String subcatIcon) {
        this.subcatIcon = subcatIcon;
    }
}

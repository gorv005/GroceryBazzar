package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gaurav.garg on 02-11-2017.
 */

public class Description implements Serializable {
    @SerializedName("subcat_id")
    @Expose
    private String subcatId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("subcat_icon")
    @Expose
    private String subcatIcon;
    @SerializedName("subcat_icon_1")
    @Expose
    private Integer subcatIcon1;

    @SerializedName("desc")
    @Expose
    private String desc;
    /**
     * No args constructor for use in serialization
     *
     */
    public Description() {
    }

    /**
     *
     * @param title
     * @param subcatId
     * @param subcatIcon
     */
    public Description(String subcatId, String title, String subcatIcon) {
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

    public Integer getSubcatIcon1() {
        return subcatIcon1;
    }

    public void setSubcatIcon1(Integer subcatIcon1) {
        this.subcatIcon1 = subcatIcon1;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

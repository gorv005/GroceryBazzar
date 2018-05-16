package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaurav.garg on 02-11-2017.
 */

public class Category implements Serializable {

    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("category_icon")
    @Expose
    private String categoryIcon;
    @SerializedName("category_drawable")
    @Expose
    private Integer categoryDrawable;
    @SerializedName("subcategories")
    @Expose
    private List<Subcategory> subcategories = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Category() {
    }

    /**
     *
     * @param name
     * @param categoryIcon
     * @param catId
     * @param subcategories
     */
    public Category(String catId, String name, String categoryIcon, List<Subcategory> subcategories) {
        super();
        this.catId = catId;
        this.name = name;
        this.categoryIcon = categoryIcon;
        this.subcategories = subcategories;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(String categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    public Integer getCategoryDrawable() {
        return categoryDrawable;
    }

    public void setCategoryDrawable(Integer categoryDrawable) {
        this.categoryDrawable = categoryDrawable;
    }
}

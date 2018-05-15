package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaurav.garg on 02-11-2017.
 */

public class CategoryInfo implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;
    @SerializedName("error")
    @Expose
    private String error;
    /**
     * No args constructor for use in serialization
     *
     */
    public CategoryInfo() {
    }

    /**
     *
     * @param message
     * @param status
     * @param categories
     */
    public CategoryInfo(String status, String message, List<Category> categories) {
        super();
        this.status = status;
        this.message = message;
        this.categories = categories;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

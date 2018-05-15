package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaurav.garg on 03-11-2017.
 */

public class SubCategoryInfo implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("product")
    @Expose
    private List<Product> product = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public SubCategoryInfo() {
    }

    /**
     *
     * @param product
     * @param message
     * @param status
     */
    public SubCategoryInfo(String status, String message, List<Product> product) {
        super();
        this.status = status;
        this.message = message;
        this.product = product;
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

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }
}

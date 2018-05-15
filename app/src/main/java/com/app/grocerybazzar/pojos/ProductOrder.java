package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GAURAV on 10/17/2017.
 */

public class ProductOrder implements Serializable{
    @SerializedName("offer_code")
    @Expose
    private String offerCode;
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("product_quantity")
    @Expose
    private String productQuantity;

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}

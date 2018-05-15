package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by GAURAV on 10/17/2017.
 */

public class ProductOrderConfirmation implements Serializable{
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("address_id")
    @Expose
    private String addressId;
    @SerializedName("product")
    @Expose
    private List<ProductOrder> product = null;
    @SerializedName("total_amount")
    @Expose
    private String totalAmount;
    @SerializedName("offer_code")
    @Expose
    private String offerCode;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public List<ProductOrder> getProduct() {
        return product;
    }

    public void setProduct(List<ProductOrder> product) {
        this.product = product;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}

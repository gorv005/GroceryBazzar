package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by AbhinavT on 02-02-2018.
 */

class OrderProduct {


    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("after_discount_product_price")
    @Expose
    private String afterDiscountProductPrice;
    @SerializedName("product_quantity")
    @Expose
    private String productQuantity;
    @SerializedName("discount_amount")
    @Expose
    private String discountAmount;
    @SerializedName("product_price")
    @Expose
    private String productPrice;

    /**
     * No args constructor for use in serialization
     */
    public OrderProduct() {
    }

    /**
     * @param afterDiscountProductPrice
     * @param orderNumber
     * @param productQuantity
     * @param productPrice
     * @param discountAmount
     * @param productName
     */
    public OrderProduct(String orderNumber, String productName, String afterDiscountProductPrice, String productQuantity, String discountAmount, String productPrice) {
        super();
        this.orderNumber = orderNumber;
        this.productName = productName;
        this.afterDiscountProductPrice = afterDiscountProductPrice;
        this.productQuantity = productQuantity;
        this.discountAmount = discountAmount;
        this.productPrice = productPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAfterDiscountProductPrice() {
        return afterDiscountProductPrice;
    }

    public void setAfterDiscountProductPrice(String afterDiscountProductPrice) {
        this.afterDiscountProductPrice = afterDiscountProductPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

}

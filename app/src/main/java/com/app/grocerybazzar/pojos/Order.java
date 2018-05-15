package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AbhinavT on 02-02-2018.
 */

public class Order {


    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("order_number")
    @Expose
    private String orderNumber;
    @SerializedName("order_products")
    @Expose
    private List<OrderProduct> orderProducts = null;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("offer_code")
    @Expose
    private String offerCode;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("shipping_address")
    @Expose
    private List<ShippingAddress> shippingAddress = null;

    /**
     * No args constructor for use in serialization
     */
    public Order() {
    }

    /**
     * @param orderDate
     * @param paymentType
     * @param orderNumber
     * @param orderProducts
     * @param shippingAddress
     * @param offerCode
     * @param orderId
     */
    public Order(String orderId, String orderNumber, List<OrderProduct> orderProducts, String paymentType, String offerCode, String orderDate, List<ShippingAddress> shippingAddress) {
        super();
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.orderProducts = orderProducts;
        this.paymentType = paymentType;
        this.offerCode = offerCode;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<ShippingAddress> getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(List<ShippingAddress> shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}

package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AbhinavT on 02-02-2018.
 */

public class OrdersResponse {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("order")
    @Expose
    private List<Order> order = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public OrdersResponse() {
    }

    /**
     *
     * @param message
     * @param order
     * @param status
     */
    public OrdersResponse(String status, String message, List<Order> order) {
        super();
        this.status = status;
        this.message = message;
        this.order = order;
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

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }
}

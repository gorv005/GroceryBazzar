package com.app.grocerybazzar.pojos;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gaurav.garg on 03-11-2017.
 */

public class AddressInfo implements Serializable {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("addresses")
    @Expose
    private List<Address> addresses = null;
    @SerializedName("error")
    @Expose
    private String error;
    /**
     * No args constructor for use in serialization
     *
     */
    public AddressInfo() {
    }

    /**
     *
     * @param message
     * @param status
     * @param addresses
     */
    public AddressInfo(String status, String message, List<Address> addresses) {
        super();
        this.status = status;
        this.message = message;
        this.addresses = addresses;
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

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

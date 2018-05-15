package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gaurav.garg on 03-11-2017.
 */

public class PostOffice implements Serializable{
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("BranchType")
    @Expose
    private String branchType;
    @SerializedName("DeliveryStatus")
    @Expose
    private String deliveryStatus;
    @SerializedName("Taluk")
    @Expose
    private String taluk;
    @SerializedName("Circle")
    @Expose
    private String circle;
    @SerializedName("District")
    @Expose
    private String district;
    @SerializedName("Division")
    @Expose
    private String division;
    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("State")
    @Expose
    private String state;
    @SerializedName("Country")
    @Expose
    private String country;

    /**
     * No args constructor for use in serialization
     *
     */
    public PostOffice() {
    }

    /**
     *
     * @param region
     * @param division
     * @param description
     * @param branchType
     * @param name
     * @param state
     * @param circle
     * @param district
     * @param taluk
     * @param deliveryStatus
     * @param country
     */
    public PostOffice(String name, String description, String branchType, String deliveryStatus, String taluk, String circle, String district, String division, String region, String state, String country) {
        super();
        this.name = name;
        this.description = description;
        this.branchType = branchType;
        this.deliveryStatus = deliveryStatus;
        this.taluk = taluk;
        this.circle = circle;
        this.district = district;
        this.division = division;
        this.region = region;
        this.state = state;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBranchType() {
        return branchType;
    }

    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getTaluk() {
        return taluk;
    }

    public void setTaluk(String taluk) {
        this.taluk = taluk;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}

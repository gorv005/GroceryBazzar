package com.app.grocerybazzar.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by gaurav.garg on 03-11-2017.
 */

public class Product implements Serializable{
    @SerializedName("product_id")
    @Expose
    private String productId;
    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("subcat_id")
    @Expose
    private String subcatId;
    @SerializedName("variant_id")
    @Expose
    private String variant_id;
    @SerializedName("product_name")
    @Expose
    private String productName;
    @SerializedName("product_thum")
    @Expose
    private String productThum;
    @SerializedName("product_image")
    @Expose
    private String productImage;
    @SerializedName("product_short_desc")
    @Expose
    private String productShortDesc;
    @SerializedName("product_long_desc")
    @Expose
    private String productLongDesc;
    @SerializedName("price")
    @Expose
    private String productPrice;
    @SerializedName("brand_name")
    @Expose
    private String brandName;
    @SerializedName("offer_code")
    @Expose
    private String offerCode;
    @SerializedName("stock")
    @Expose
    private String stock;
    @SerializedName("publish_time")
    @Expose
    private String publishTime;
    @SerializedName("weight")
    @Expose
    private String weight;
    private boolean addedToCart;


    /**
     * No args constructor for use in serialization
     *
     */
    public Product() {
    }

    /**
     *
     * @param stock
     * @param subcatId
     * @param publishTime
     * @param productShortDesc
     * @param productImage
     * @param catId
     * @param brandName
     * @param productPrice
     * @param productName
     * @param productThum
     * @param productLongDesc
     * @param productId
     */
    public Product(String productId, String catId, String subcatId, String productName, String productThum, String productImage, String productShortDesc, String productLongDesc, String productPrice, String brandName, String stock, String publishTime) {
        super();
        this.productId = productId;
        this.catId = catId;
        this.subcatId = subcatId;
        this.productName = productName;
        this.productThum = productThum;
        this.productImage = productImage;
        this.productShortDesc = productShortDesc;
        this.productLongDesc = productLongDesc;
        this.productPrice = productPrice;
        this.brandName = brandName;
        this.stock = stock;
        this.publishTime = publishTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getSubcatId() {
        return subcatId;
    }

    public void setSubcatId(String subcatId) {
        this.subcatId = subcatId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductThum() {
        return productThum;
    }

    public void setProductThum(String productThum) {
        this.productThum = productThum;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductShortDesc() {
        return productShortDesc;
    }

    public void setProductShortDesc(String productShortDesc) {
        this.productShortDesc = productShortDesc;
    }

    public String getProductLongDesc() {
        return productLongDesc;
    }

    public void setProductLongDesc(String productLongDesc) {
        this.productLongDesc = productLongDesc;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }


    public void setAddedToCart(boolean addedToCart) {
        this.addedToCart = addedToCart;
    }

    public boolean isAddedToCart() {
        return addedToCart;
    }

    public String getVariant_id() {
        return variant_id;
    }

    public void setVariant_id(String variant_id) {
        this.variant_id = variant_id;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}

package com.bku.appbooking.common;



public class Product {
    private int id;
    private String title = "";
    private String price = "";
    private String imageUrl = "";
    private String shortDescription = "";
    private String longDescription = "";

    public Product(int id, String title, String price, String imageUrl, String shortDescription, String longDescription) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
    }

    public Product(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
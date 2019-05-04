package com.bku.appbooking.common;

public class Product {
    private long id;
    private String title;
    private String price;
    private String imageUrl;
    private String shortDescription;
    private String longDescription;

    public Product(long id, String title, String price, String imageUrl, String shortDescription, String longDescription) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
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

    public long getId() {
        return id;
    }
}
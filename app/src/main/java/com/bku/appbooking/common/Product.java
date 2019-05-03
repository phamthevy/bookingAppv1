package com.bku.appbooking.common;

public class Product {
    private long id;
    private String title;
    private String price;
    private String imageUrl;
    private String description;

    public Product(long id, String title, String price, String imageUrl, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public long getId() {
        return id;
    }
}
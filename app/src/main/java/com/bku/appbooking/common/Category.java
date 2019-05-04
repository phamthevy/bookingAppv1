package com.bku.appbooking.common;

public class Category {
    private long id;
    private String title;
    private String imageUrl;
    private String shortDescription;

    public Category(long id, String title, String imageUrl, String shortDescription) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.shortDescription = shortDescription;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}

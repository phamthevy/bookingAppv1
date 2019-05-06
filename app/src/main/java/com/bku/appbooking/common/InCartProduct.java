package com.bku.appbooking.common;

public class InCartProduct {
    private Product product;
    private int num;

    public InCartProduct(Product product) {
        this(product, 1);
    }

    public InCartProduct(Product product, int num) {
        this.product = product;
        this.num = num;
    }

    public Product getProduct() {
        return product;
    }

    public int getNum() {
        return num;
    }
}

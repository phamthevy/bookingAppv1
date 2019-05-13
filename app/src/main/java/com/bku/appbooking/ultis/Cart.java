package com.bku.appbooking.ultis;

import com.bku.appbooking.common.InCartProduct;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static final Cart ourInstance = new Cart();
    private List<InCartProduct> products;

    public static Cart getInstance() {
        return ourInstance;
    }

    private Cart() {
        products = new ArrayList<>();
    }

    public void addProduct(InCartProduct product){
        products.add(product);
    }

    public void clear(){
        products.clear();
    }

    public List<InCartProduct> getProducts(){
        return products;
    }
}

package com.bku.appbooking.ultis;

import com.bku.appbooking.common.CartIconContainer;
import com.bku.appbooking.common.InCartProduct;
import com.bku.appbooking.common.Product;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Cart {
    private static final Cart ourInstance = new Cart();
    private List<InCartProduct> products;
    private List<CartIconContainer> cartIconContainers;

    public static Cart getInstance() {
        return ourInstance;
    }

    private Cart() {
        products = new ArrayList<>();
        cartIconContainers = new LinkedList<>();
    }

    public void addProduct(InCartProduct product){
        products.add(product);
    }

    public List<InCartProduct> getProducts(){
        return products;
    }

    private void notiChange(){
        for (CartIconContainer cartIconContainer: cartIconContainers){
            cartIconContainer.onCartChange();
        }
    }
}

package com.bku.appbooking.common;

public class InCartProduct {
    private Product product;
    private int num;
    private boolean checked;

    public InCartProduct(Product product) {
        this(product, 1,false);
    }

    public InCartProduct(Product product, int num, boolean checked) {
        this.product = product;
        this.num = num;
        this.checked = checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public boolean isChecked() {
        return checked;
    }
    
    public Product getProduct() {
        return product;
    }

    public int getNum() {
        return num;
    }
}

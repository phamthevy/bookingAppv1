package com.bku.appbooking.common;

public class HistoryItem {
    private int id;
    private Product product;
    private String createDate;
    private String description;
    private String status;
    private static int count = 0;

    public HistoryItem(Product product, String createDate, String description, String status) {
        this.id = count++;
        this.product = product;
        this.createDate = createDate;
        this.description = description;
        switch (status) {
            case "0":
                this.status = "Đang xử lý";
                break;
            case "1":
                this.status = "Đã xử lý";
                break;
            case "2":
                this.status = "Đã huỷ";
                break;
            default:
                this.status = "Lỗi hệ thống";
                break;
        }
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }
}

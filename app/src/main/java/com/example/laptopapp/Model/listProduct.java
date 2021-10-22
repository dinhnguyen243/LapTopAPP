package com.example.laptopapp.Model;

public class listProduct {
    private String customer_id;
    private String voucher_id;
    private String product_id;
    private int quantity;
    private float price;

    public listProduct(String customer_id, String voucher_id, String product_id, int quantity, float price) {
        this.customer_id = customer_id;
        this.voucher_id = voucher_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(String voucher_id) {
        this.voucher_id = voucher_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuanlity() {
        return quantity;
    }

    public void setQuanlity(int quanlity) {
        this.quantity = quanlity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}

package com.example.laptopapp.Model;

public class BillDetail {
    private String voucher_id;
    private boolean status;
    private String created_at;
    private String updated_at;
    private String _id;
    private String customer_id;
    private float price;
    private String product_id;
    private String quantity;
    private float last_price;
    private String bill_id;

    public BillDetail(String voucher_id, boolean status, String created_at, String updated_at, String _id, String customer_id, float price, String product_id, String quantity, float last_price, String bill_id) {
        this.voucher_id = voucher_id;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this._id = _id;
        this.customer_id = customer_id;
        this.price = price;
        this.product_id = product_id;
        this.quantity = quantity;
        this.last_price = last_price;
        this.bill_id = bill_id;
    }

    public String getVoucher_id() {
        return voucher_id;
    }

    public void setVoucher_id(String voucher_id) {
        this.voucher_id = voucher_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public float getLast_price() {
        return last_price;
    }

    public void setLast_price(float last_price) {
        this.last_price = last_price;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }
}

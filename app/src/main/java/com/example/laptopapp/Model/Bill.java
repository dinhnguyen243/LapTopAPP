package com.example.laptopapp.Model;

public class Bill {
    private String voucher_id;
    private boolean status;
    private String created_at;
    private String _id;
    private String bill_id;
    private String customer_id;
    private int quantity;
    private float total_price;

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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotal_price() {
        return total_price;
    }

    public void setTotal_price(float total_price) {
        this.total_price = total_price;
    }

    public Bill(String voucher_id, boolean status, String created_at, String _id, String bill_id, String customer_id, int quantity, float total_price) {
        this.voucher_id = voucher_id;
        this.status = status;
        this.created_at = created_at;
        this._id = _id;
        this.bill_id = bill_id;
        this.customer_id = customer_id;
        this.quantity = quantity;
        this.total_price = total_price;
    }
}

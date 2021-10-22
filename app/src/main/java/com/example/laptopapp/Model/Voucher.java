package com.example.laptopapp.Model;

public class Voucher {
    private boolean status;
    private String created_at;
    private String _id;
    private String voucher_code;
    private float discount;
    private String description;
    private String image;

    public Voucher() {
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

    public String getVoucher_code() {
        return voucher_code;
    }

    public void setVoucher_code(String voucher_code) {
        this.voucher_code = voucher_code;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Voucher(boolean status, String created_at, String _id, String voucher_code, float discount, String description, String image) {
        this.status = status;
        this.created_at = created_at;
        this._id = _id;
        this.voucher_code = voucher_code;
        this.discount = discount;
        this.description = description;
        this.image = image;
    }
}

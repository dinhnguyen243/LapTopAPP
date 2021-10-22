package com.example.laptopapp.Model;

public class Cart {
    private String User_id;
    private String product_id;
    private String product_name;
    private String quality;
    private String img;
    private String price;
    private int Cart_id;

    public Cart() {
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getUser_id() {
        return User_id;
    }

    public void setUser_id(String user_id) {
        User_id = user_id;
    }

    public int getCart_id() {
        return Cart_id;
    }

    public void setCart_id(int cart_id) {
        Cart_id = cart_id;
    }

    public Cart(String user_id, String product_id, String product_name, String quality, String img, String price, int cart_id) {
        User_id = user_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.quality = quality;
        this.img = img;
        this.price = price;
        Cart_id = cart_id;
    }
}


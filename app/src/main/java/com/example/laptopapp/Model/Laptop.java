package com.example.laptopapp.Model;

import java.util.ArrayList;
import java.util.Date;

public class Laptop {
    private String created_at;
    private String updated_at;

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

    private String _id;
    private String product_id;
    private String product_name;
    private String brand;
    private String ram;
    private String hard_disk;
    private String cpu;
    private String core;
    private String color;
    private String monitor;
    private String size;
    private String vga;
    private String os;
    private ArrayList<String> link_review;
    private ArrayList<String> img;
    private float price_income;
    private float price_outcome;
    private int quantity;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getHard_disk() {
        return hard_disk;
    }

    public void setHard_disk(String hard_disk) {
        this.hard_disk = hard_disk;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getCore() {
        return core;
    }

    public void setCore(String core) {
        this.core = core;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public ArrayList<String> getLink_review() {
        return link_review;
    }

    public void setLink_review(ArrayList<String> link_review) {
        this.link_review = link_review;
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public float getPrice_income() {
        return price_income;
    }

    public void setPrice_income(float price_income) {
        this.price_income = price_income;
    }

    public float getPrice_outcome() {
        return price_outcome;
    }

    public void setPrice_outcome(float price_outcome) {
        this.price_outcome = price_outcome;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Laptop() {
    }

    public Laptop(String created_at, String updated_at, String _id, String product_id, String product_name, String brand, String ram, String hard_disk, String cpu, String core, String color, String monitor, String size, String vga, String os, ArrayList<String> link_review, ArrayList<String> img, float price_income, float price_outcome, int quantity) {
        this.created_at = created_at;
        this.updated_at = updated_at;
        this._id = _id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.brand = brand;
        this.ram = ram;
        this.hard_disk = hard_disk;
        this.cpu = cpu;
        this.core = core;
        this.color = color;
        this.monitor = monitor;
        this.size = size;
        this.vga = vga;
        this.os = os;
        this.link_review = link_review;
        this.img = img;
        this.price_income = price_income;
        this.price_outcome = price_outcome;
        this.quantity = quantity;
    }
}

package com.example.laptopapp.Model;

import android.service.quicksettings.Tile;

public class Event {
    private boolean status;
    private String created_at;
    private String _id;
    private String title;
    private String link_img;
    private String description;

    public Event() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink_img() {
        return link_img;
    }

    public void setLink_img(String link_img) {
        this.link_img = link_img;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Event(boolean status, String created_at, String _id, String title, String link_img, String description) {
        this.status = status;
        this.created_at = created_at;
        this._id = _id;
        this.title = title;
        this.link_img = link_img;
        this.description = description;
    }
}

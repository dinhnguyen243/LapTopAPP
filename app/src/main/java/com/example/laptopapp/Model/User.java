package com.example.laptopapp.Model;

public class User {
private String password;
private String address;
private String birthday;
private String phone;
private boolean status;
private String created_at;
private String update_at;
private String _id;
private String user;
private String fullname;
private String email;

    public User(String password, String address, String birthday, String phone, boolean status, String created_at, String update_at, String _id, String user, String fullname, String email) {
        this.password = password;
        this.address = address;
        this.birthday = birthday;
        this.phone = phone;
        this.status = status;
        this.created_at = created_at;
        this.update_at = update_at;
        this._id = _id;
        this.user = user;
        this.fullname = fullname;
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

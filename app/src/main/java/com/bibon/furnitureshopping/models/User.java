package com.bibon.furnitureshopping.models;

public class User {

    private String _id;
    private String email;
    private String fullname;

    public User() {
    }

    public User(String _id, String email, String fullname) {
        this._id = _id;
        this.email = email;
        this.fullname = fullname;
    }

    public User(String email, String fullname) {
        this.email = email;
        this.fullname = fullname;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}

package com.bibon.furnitureshopping.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {

    private String _id;
    private String user;
    private ArrayList<CartItem> items;

    public Cart(String _id, String user, ArrayList<CartItem> items) {
        this._id = _id;
        this.user = user;
        this.items = items;
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

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }
}

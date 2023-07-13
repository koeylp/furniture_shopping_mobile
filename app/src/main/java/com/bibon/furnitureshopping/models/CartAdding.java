package com.bibon.furnitureshopping.models;

import java.util.ArrayList;

public class CartAdding {
    private String user;
    private ArrayList<CartItemAdding> items;

    public CartAdding(String user, ArrayList<CartItemAdding> items) {
        this.user = user;
        this.items = items;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public ArrayList<CartItemAdding> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItemAdding> items) {
        this.items = items;
    }
}

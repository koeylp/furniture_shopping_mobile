package com.bibon.furnitureshopping.models;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Product product;
    private int cartQuantity;
    private String _id;

    public CartItem(Product product, int cartQuantity, String _id) {
        this.product = product;
        this.cartQuantity = cartQuantity;
        this._id = _id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}

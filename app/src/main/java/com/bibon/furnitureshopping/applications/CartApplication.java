package com.bibon.furnitureshopping.applications;

import android.app.Application;

import com.bibon.furnitureshopping.models.CartList;

public class CartApplication extends Application {
    private CartList cartList = new CartList();

    public CartApplication() {
        this.cartList = new CartList();
    }

    public CartApplication(CartList cartList) {
        this.cartList = cartList;
    }

    public CartList getCartList() {
        return cartList;
    }

    public void setCartList(CartList cartList) {
        this.cartList = cartList;
    }
}

package com.bibon.furnitureshopping.models;

import java.util.ArrayList;
import java.util.List;

public class CartList  {
    List<Cart> cartList;

    public CartList() {
        this.cartList = new ArrayList<>();
    }

    public CartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }
}

package com.bibon.furnitureshopping.models;

public class CartItemAdding {
    private String product;
    private int cartQuantity;

    public CartItemAdding(String product, int cartQuantity) {
        this.product = product;
        this.cartQuantity = cartQuantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }
}

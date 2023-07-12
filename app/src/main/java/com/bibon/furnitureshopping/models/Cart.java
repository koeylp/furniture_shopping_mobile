package com.bibon.furnitureshopping.models;

import java.io.Serializable;

public class Cart implements Serializable {
    private String productId;
    private String productName;
    private double price;
    private int cartQuantity;
    private String img;

    public Cart() {
    }

    public Cart(String productName, double price, int cartQuantity, String img) {
        this.productName = productName;
        this.price = price;
        this.cartQuantity = cartQuantity;
        this.img = img;
    }

    public Cart(String productId, String productName, double price, int cartQuantity, String img) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.cartQuantity = cartQuantity;
        this.img = img;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCartQuantity() {
        return cartQuantity;
    }

    public void setCartQuantity(int cartQuantity) {
        this.cartQuantity = cartQuantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

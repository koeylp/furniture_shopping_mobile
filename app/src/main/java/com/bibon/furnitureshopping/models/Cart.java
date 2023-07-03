package com.bibon.furnitureshopping.models;

public class Cart {
    private String productName;
    private double price;
    private int cartQuantity;
    private String img;

    public Cart(String productName, double price, int cartQuantity, String img) {
        this.productName = productName;
        this.price = price;
        this.cartQuantity = cartQuantity;
        this.img = img;
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

package com.bibon.furnitureshopping.models;

import java.io.Serializable;

public class Product implements Serializable {
    private String _id;
    private String productName;
    private String category;
    private double price;
    private int quantity;
    private String description;
    private String img;

    public Product() {
    }

    public Product(String _id, String productName, String category, double price, int quantity, String description, String img) {
        this._id = _id;
        this.productName = productName;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.img = img;
    }

    public Product(String _id, String productName, double price, int quantity, String description, String img) {
        this._id = _id;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.img = img;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}

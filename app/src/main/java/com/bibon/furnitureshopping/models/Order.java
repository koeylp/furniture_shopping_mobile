package com.bibon.furnitureshopping.models;

import java.util.ArrayList;

public class Order {
    private String _id;
    private String user;
    private String address;
    private String orderDate;
    private String paymentMethod;
    private double totalPrice;
    private String status;
    private ArrayList<OrderDetail> orderDetails;

    public Order(String _id, String user, String address, String orderDate, String paymentMethod, double totalPrice, String status, ArrayList<OrderDetail> orderDetails) {
        this._id = _id;
        this.user = user;
        this.address = address;
        this.orderDate = orderDate;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDetails = orderDetails;
    }

    public Order(String user, String address, String paymentMethod, double totalPrice, ArrayList<OrderDetail> orderDetails) {
        this.user = user;
        this.address = address;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.orderDetails = orderDetails;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}

package com.bibon.furnitureshopping.models;

public class Payment {
    private String paymentName;
    private int paymentImage;

    public Payment(String paymentName, int paymentImage) {
        this.paymentName = paymentName;
        this.paymentImage = paymentImage;
    }

    public String getName() {
        return paymentName;
    }

    public int getPaymentImage() {
        return paymentImage;
    }
}

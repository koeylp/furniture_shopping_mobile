package com.bibon.furnitureshopping.models;

import com.google.firebase.Timestamp;

public class UserChat {
    private String phone;
    private String username;
    private Timestamp createdTimestamps;

    private String userId;

    public UserChat() {
    }

    public UserChat(String phone, String username, Timestamp createdTimestamps, String userId) {
        this.phone = phone;
        this.username = username;
        this.createdTimestamps = createdTimestamps;
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getCreatedTimestamps() {
        return createdTimestamps;
    }

    public void setCreatedTimestamps(Timestamp createdTimestamps) {
        this.createdTimestamps = createdTimestamps;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

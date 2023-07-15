package com.bibon.furnitureshopping.models;

import java.sql.Timestamp;

public class UserChat {
    private String phone;
    private String username;
    private Timestamp createdTimestamps;

    public UserChat() {
    }

    public UserChat(String phone, String username, Timestamp createdTimestamps) {
        this.phone = phone;
        this.username = username;
        this.createdTimestamps = createdTimestamps;
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
}

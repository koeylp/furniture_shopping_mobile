package com.bibon.furnitureshopping.utils;

import android.content.Intent;

import com.bibon.furnitureshopping.models.UserChat;

public class AndroidUtils {

    public static void passAdminModelAsIntent(Intent intent, UserChat model){
        intent.putExtra("username", model.getUsername());
        intent.putExtra("phone", model.getPhone());
        intent.putExtra("userId", model.getUserId());
    }

    public static UserChat getAdminModelFromIntent(Intent intent){
        UserChat adminModel = new UserChat();
        adminModel.setUsername(intent.getStringExtra("username"));
        adminModel.setPhone(intent.getStringExtra("phone"));
        adminModel.setUserId(intent.getStringExtra("userId"));
        return adminModel;
    }
}

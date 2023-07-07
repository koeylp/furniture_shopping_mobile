package com.bibon.furnitureshopping.services;

import com.bibon.furnitureshopping.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    String AUTH = "auth";

    @POST(AUTH)
    Call<User> signup(@Body User user);
}

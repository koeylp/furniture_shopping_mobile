package com.bibon.furnitureshopping.services;

import com.bibon.furnitureshopping.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    String AUTH = "auth";
    String USER = "user";

    @POST(AUTH + "/signup")
    Call<User> signup(@Body User user);

    @GET(USER + "/{email}")
    Call<User> getUserByEmail(@Path("email") Object email);


}

package com.bibon.furnitureshopping.services;

import com.bibon.furnitureshopping.models.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderService {
    String ORDERS = "orders";

    @POST(ORDERS + "/create")
    Call<Order> createOrder(@Body Order order);

    @GET(ORDERS + "/{user}/{status}")
    Call<Order[]> getOrdersByUserAndStatus(@Path("user") Object user, @Path("status") Object status);

}

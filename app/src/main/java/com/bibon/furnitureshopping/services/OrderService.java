package com.bibon.furnitureshopping.services;

import com.bibon.furnitureshopping.models.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OrderService {
    String ORDERS = "orders";

    @POST(ORDERS + "/create")
    Call<Order> createOrder(@Body Order order);

}

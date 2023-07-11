package com.bibon.furnitureshopping.repositories;

import com.bibon.furnitureshopping.API.APIClient;
import com.bibon.furnitureshopping.services.OrderService;

public class OrderRepository {

    public static OrderService getOrderService() {
        return APIClient.getClient().create(OrderService.class);
    }
}

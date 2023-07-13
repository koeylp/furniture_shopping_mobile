package com.bibon.furnitureshopping.repositories;

import com.bibon.furnitureshopping.API.APIClient;
import com.bibon.furnitureshopping.services.CartService;

public class CartRepository {
    public static CartService getCartService() {
        return APIClient.getClient().create(CartService.class);
    }
}

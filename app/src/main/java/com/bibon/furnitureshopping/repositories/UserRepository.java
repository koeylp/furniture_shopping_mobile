package com.bibon.furnitureshopping.repositories;

import com.bibon.furnitureshopping.API.APIClient;
import com.bibon.furnitureshopping.services.UserService;

public class UserRepository {
    public static UserService getUserService() {
        return APIClient.getClient().create(UserService.class);
    }
}

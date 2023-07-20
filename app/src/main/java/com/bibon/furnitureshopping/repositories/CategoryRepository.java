package com.bibon.furnitureshopping.repositories;

import com.bibon.furnitureshopping.API.APIClient;
import com.bibon.furnitureshopping.services.CategoryService;

public class CategoryRepository {
    public static CategoryService getCategoryService() {
        return APIClient.getClient().create(CategoryService.class);
    }
}

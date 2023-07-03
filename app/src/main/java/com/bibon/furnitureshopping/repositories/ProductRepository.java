package com.bibon.furnitureshopping.repositories;

import com.bibon.furnitureshopping.API.APIClient;
import com.bibon.furnitureshopping.services.ProductService;

public class ProductRepository {

    public static ProductService getProductService() {
        return APIClient.getClient().create(ProductService.class);
    }
}

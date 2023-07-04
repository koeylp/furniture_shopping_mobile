package com.bibon.furnitureshopping.services;

import com.bibon.furnitureshopping.models.Product;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductService {
    String PRODUCTS = "products";

    @GET(PRODUCTS)
    Call<Product[]> getAllProducts();

}

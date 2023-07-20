package com.bibon.furnitureshopping.services;

import com.bibon.furnitureshopping.models.Category;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    String CATEGORIES = "categories";

    @GET(CATEGORIES)
    Call<Category[]> getAllCategories();
}

package com.bibon.furnitureshopping.utils;

import com.bibon.furnitureshopping.models.Product;

import java.util.ArrayList;

public interface UpdateProductListRecyclerView {
    public void callback(int position, ArrayList<Product> items);

}

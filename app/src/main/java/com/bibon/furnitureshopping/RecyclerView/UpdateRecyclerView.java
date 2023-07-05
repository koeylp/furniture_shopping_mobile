package com.bibon.furnitureshopping.RecyclerView;

import com.bibon.furnitureshopping.models.Product;

import java.util.ArrayList;

public interface UpdateRecyclerView {
    public void callback(int position, ArrayList<Product> items);
}

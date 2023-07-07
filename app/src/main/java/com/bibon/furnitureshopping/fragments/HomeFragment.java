package com.bibon.furnitureshopping.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.RecyclerView.UpdateProductListRecyclerView;
import com.bibon.furnitureshopping.activities.ProductDetailActivity;
import com.bibon.furnitureshopping.adapters.CategoryRVAdapter;
import com.bibon.furnitureshopping.adapters.ProductRVAdapter;
import com.bibon.furnitureshopping.applications.CartApplication;
import com.bibon.furnitureshopping.models.Cart;
import com.bibon.furnitureshopping.models.CartList;
import com.bibon.furnitureshopping.models.Category;
import com.bibon.furnitureshopping.models.Product;
import com.bibon.furnitureshopping.repositories.CategoryRepository;
import com.bibon.furnitureshopping.repositories.ProductRepository;
import com.bibon.furnitureshopping.services.CategoryService;
import com.bibon.furnitureshopping.services.ProductService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements UpdateProductListRecyclerView {

    ProductService productService;
    CategoryService categoryService;
    private RecyclerView recyclerViewProduct, recyclerViewCategory;
    ArrayList<Product> productList;
    ArrayList<Category> categoryList;
    CategoryRVAdapter categoryRVAdapter;
    ProductRVAdapter productRVAdapter;
    CartList cartList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productService = ProductRepository.getProductService();
        categoryService = CategoryRepository.getCategoryService();


        cartList = new CartList();

        cartList = ((CartApplication) this.getActivity().getApplication()).getCartList();

        // Category
        recyclerViewCategory = getView().findViewById(R.id.rv_category);
        getALlCategories();


        // Product
        recyclerViewProduct = getView().findViewById(R.id.rv_product);
        getAllProducts();

//        getProductsBycategory("64a40e16be7269158c2533c5");

    }


    private void getALlCategories() {
        this.categoryList = new ArrayList<>();
        try {
            Call<Category[]> call = categoryService.getAllCategories();
            System.out.println(call + " category");
            call.enqueue(new Callback<Category[]>() {
                @Override
                public void onResponse(Call<Category[]> call, Response<Category[]> response) {
                    Category[] categories = response.body();
                    if (categories == null) {
                        return;
                    }
                    for (Category category : categories) {
                        categoryList.add(new Category(category.get_id(), category.getCategoryName(), category.getImg()));
                    }
                    System.out.println(categoryList.size());
                    categoryRVAdapter = new CategoryRVAdapter(categoryList, productList, getActivity(), HomeFragment.this);
                    recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerViewCategory.setAdapter(categoryRVAdapter);
                }

                @Override
                public void onFailure(Call<Category[]> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getAllProducts() {
        this.productList = new ArrayList<>();
        try {
            Call<Product[]> call = productService.getAllProducts();
            System.out.println(call);
            call.enqueue(new Callback<Product[]>() {
                @Override
                public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                    Product[] products = response.body();
                    if (products == null) {
                        return;
                    }
                    for (Product product : products) {
                        System.out.println(product.getCategory());
                        productList.add(new Product(product.get_id(), product.getProductName(), product.getCategory(), product.getPrice(), product.getQuantity(), product.getDescription(), product.getImg()));
                    }
                    productRVAdapter = new ProductRVAdapter(productList, cartList, HomeFragment.this);
                    recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recyclerViewProduct.setAdapter(productRVAdapter);
                }

                @Override
                public void onFailure(Call<Product[]> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    public void toProductDetail(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("Product", product);
        intent.putExtra("BUNDLE", bundle);
        startActivity(intent);
    }

    private void getProductsBycategory(String categoryId) {
        this.productList = new ArrayList<>();
        try {
            Call<Product[]> call = productService.getProductsByCategory(categoryId);
            System.out.println("asdfoooo");
            call.enqueue(new Callback<Product[]>() {
                @Override
                public void onResponse(Call<Product[]> call, Response<Product[]> response) {
                    Product[] products = response.body();
                    if (products == null) {
                        return;
                    }
                    for (Product product : products) {
                        System.out.println(product.getCategory() + " " + product.getProductName());
                        productList.add(new Product(product.get_id(), product.getProductName(), product.getCategory(), product.getPrice(), product.getQuantity(), product.getDescription(), product.getImg()));
                    }
                    categoryRVAdapter = new CategoryRVAdapter(categoryList, productList, getActivity(), HomeFragment.this);
                    recyclerViewCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerViewCategory.setAdapter(categoryRVAdapter);
                }

                @Override
                public void onFailure(Call<Product[]> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    @Override
    public void callback(int position, ArrayList<Product> items) {
        if (items.isEmpty()) {
            items = productList;
        }
        productRVAdapter = new ProductRVAdapter(items, cartList, HomeFragment.this);
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewProduct.setAdapter(productRVAdapter);
    }
}
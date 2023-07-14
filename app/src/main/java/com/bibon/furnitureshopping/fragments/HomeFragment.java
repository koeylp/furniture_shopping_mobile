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
import com.bibon.furnitureshopping.activities.MainActivity;
import com.bibon.furnitureshopping.activities.ProductDetailActivity;
import com.bibon.furnitureshopping.adapters.CategoryRVAdapter;
import com.bibon.furnitureshopping.adapters.ProductRVAdapter;
import com.bibon.furnitureshopping.models.CartAdding;
import com.bibon.furnitureshopping.models.CartList;
import com.bibon.furnitureshopping.models.Category;
import com.bibon.furnitureshopping.models.Product;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.CartRepository;
import com.bibon.furnitureshopping.repositories.CategoryRepository;
import com.bibon.furnitureshopping.repositories.ProductRepository;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.CartService;
import com.bibon.furnitureshopping.services.CategoryService;
import com.bibon.furnitureshopping.services.ProductService;
import com.bibon.furnitureshopping.services.UserService;
import com.bibon.furnitureshopping.utils.UpdateProductListRecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements UpdateProductListRecyclerView {

    ProductService productService;
    CategoryService categoryService;
    CartService cartService;
    UserService userService;
    private RecyclerView recyclerViewProduct, recyclerViewCategory;
    ArrayList<Product> productList;
    ArrayList<Category> categoryList;
    CategoryRVAdapter categoryRVAdapter;
    ProductRVAdapter productRVAdapter;
    CartList cartList;
    String email;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Service Calling
        productService = ProductRepository.getProductService();
        categoryService = CategoryRepository.getCategoryService();
        cartService = CartRepository.getCartService();
        userService = UserRepository.getUserService();

        // Get email
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
        }

        // Category
        recyclerViewCategory = view.findViewById(R.id.rv_category);

        // Product
        recyclerViewProduct = view.findViewById(R.id.rv_product);

        // Get id and call get all products and category
        getUserByEmail(email);


    }


    private void getALlCategories(String user) {
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
                    categoryRVAdapter = new CategoryRVAdapter(categoryList, productList, getActivity(), HomeFragment.this, user);
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

    private void getAllProducts(String user) {
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
                        productList.add(new Product(product.get_id(), product.getProductName(), product.getCategory(), product.getPrice(), product.getQuantity(), product.getDescription(), product.getImg()));
                    }
                    productRVAdapter = new ProductRVAdapter(productList, cartList, HomeFragment.this, user,(MainActivity) getActivity());
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

    public void addToCart(CartAdding cart){
        try {
            Call<CartAdding> call = cartService.addToCart(cart);
            call.enqueue(new Callback<CartAdding>() {
                @Override
                public void onResponse(Call<CartAdding> call, Response<CartAdding> response) {
                }

                @Override
                public void onFailure(Call<CartAdding> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getUserByEmail(String email) {
        try {
            Call<User> call = userService.getUserByEmail(email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user == null) {
                        return;
                    }
                    user = new User(user.get_id(), user.getEmail(), user.getFullname());
                    getAllProducts(user.get_id());
                    getALlCategories(user.get_id());
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println("error: " + t);
                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    @Override
    public void callback(int position, ArrayList<Product> items, String user) {
        if (items.isEmpty()) {
            items = productList;
        }
        productRVAdapter = new ProductRVAdapter(items, cartList, HomeFragment.this, user, (MainActivity) getActivity());
        recyclerViewProduct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerViewProduct.setAdapter(productRVAdapter);
    }
}
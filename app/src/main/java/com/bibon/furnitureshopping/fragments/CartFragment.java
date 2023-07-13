package com.bibon.furnitureshopping.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.activities.CheckoutActivity;
import com.bibon.furnitureshopping.adapters.CartRVAdapter;
import com.bibon.furnitureshopping.models.Cart;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.CartRepository;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.CartService;
import com.bibon.furnitureshopping.services.UserService;
import com.bibon.furnitureshopping.utils.UpdateCartRecycleView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment implements UpdateCartRecycleView {

    CartService cartService;
    UserService userService;
    CartRVAdapter cartRVAdapter;
    TextView tv_total, tv_total_label, tv_currency;
    private RecyclerView cartRecycleView;
    Button btn_checkout;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Service Calling
        cartService = CartRepository.getCartService();
        userService = UserRepository.getUserService();


        // View calling
        cartRecycleView = view.findViewById(R.id.rv_cart_item);
        tv_currency = view.findViewById(R.id.tv_currency);
        tv_total_label = view.findViewById(R.id.tv_total_label);
        tv_total = view.findViewById(R.id.tv_total);

        double[] total = new double[1];
//        for (Cart cart : cartList.getCartList()) {
//            total[0] += 0;
//        }
        tv_total.setText(String.valueOf(total[0]));

        btn_checkout = view.findViewById(R.id.btn_checkout);
        if (total[0] == 0) {
            btn_checkout.setVisibility(View.GONE);
            tv_total.setVisibility(View.GONE);
            tv_total_label.setVisibility(View.GONE);
            tv_currency.setVisibility(View.GONE);
        }

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("Total", total[0]);
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });

        // Get email
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
        }

        getUserByEmail(email);

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
                    getCartByUser(user.get_id(), email);
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

    private void getCartByUser(String user, String email) {
        try {
            Call<Cart> call = cartService.getCartByUser(user);
            call.enqueue(new Callback<Cart>() {
                @Override
                public void onResponse(Call<Cart> call, Response<Cart> response) {
                    Cart cart = response.body();
                    if (cart == null) {
                        return;
                    }
                    Collections.reverse(cart.getItems());
                    cartRVAdapter = new CartRVAdapter(cart, email, CartFragment.this);
                    cartRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    cartRecycleView.setAdapter(cartRVAdapter);
                }

                @Override
                public void onFailure(Call<Cart> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    public void passEmailToDeleteItem(String email, String productId) {
        try {
            Call<User> call = userService.getUserByEmail(email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    System.out.println(productId + "kkkk");
                    User user = response.body();
                    if (user == null) {
                        return;
                    }
                    deleteItemById(user.get_id(), productId, email);
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

    public void deleteItemById(String user, String productId, String email) {
        try {
            Call<Cart> call = cartService.deleteCartItemById(user, productId);
            call.enqueue(new Callback<Cart>() {
                @Override
                public void onResponse(Call<Cart> call, Response<Cart> response) {

                }

                @Override
                public void onFailure(Call<Cart> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    @Override
    public void callback(int position, Cart cart) {
        cartRVAdapter = new CartRVAdapter(cart, email, CartFragment.this);
        cartRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cartRecycleView.setAdapter(cartRVAdapter);
    }
}
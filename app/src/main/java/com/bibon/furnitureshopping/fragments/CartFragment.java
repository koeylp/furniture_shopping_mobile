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

import com.airbnb.lottie.LottieAnimationView;
import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.activities.CheckoutActivity;
import com.bibon.furnitureshopping.activities.MainActivity;
import com.bibon.furnitureshopping.adapters.CartRVAdapter;
import com.bibon.furnitureshopping.models.Cart;
import com.bibon.furnitureshopping.models.CartItem;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.CartRepository;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.CartService;
import com.bibon.furnitureshopping.services.UserService;
import com.bibon.furnitureshopping.utils.UpdateCartRecycleView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment implements UpdateCartRecycleView {

    CartService cartService;
    UserService userService;
    CartRVAdapter cartRVAdapter;
    TextView tv_total, tv_total_label;
    private RecyclerView cartRecycleView;
    Button btn_checkout;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String email;
    double total = 0;
    LottieAnimationView lottieAnimationView;


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
        tv_total_label = view.findViewById(R.id.tv_total_label);
        tv_total = view.findViewById(R.id.tv_total);
        btn_checkout = view.findViewById(R.id.btn_checkout);
        lottieAnimationView = view.findViewById(R.id.lottieAnimationView);


        if (total == 0) {
            btn_checkout.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);
        } else {
            lottieAnimationView.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
        }

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
                    getCartByUser(user.get_id(), email);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Reason: ", t.getMessage());
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
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.setCountProductToCart(cart.getItems().size());
                    Collections.reverse(cart.getItems());
                    total = 0;
                    ArrayList<CartItem> cartItemsList = new ArrayList<>();
                    for (CartItem item : cart.getItems()) {
                        cartItemsList.add(item);
                        total += item.getCartQuantity() * item.getProduct().getPrice();
                    }
                    cartRVAdapter = new CartRVAdapter(cart, email, CartFragment.this, total);
                    cartRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    cartRecycleView.setAdapter(cartRVAdapter);
                    Locale localeVN = new Locale("vi", "VN");
                    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                    String price = currencyVN.format(total);
                    tv_total.setText(price);

                    if (total == 0) {
                        btn_checkout.setVisibility(View.GONE);
                        lottieAnimationView.setVisibility(View.VISIBLE);
                    } else {
                        lottieAnimationView.setVisibility(View.GONE);
                        btn_checkout.setVisibility(View.VISIBLE);
                    }

                    btn_checkout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), CheckoutActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putDouble("Total", total);
                            bundle.putSerializable("CartItems", cartItemsList);
                            intent.putExtra("BUNDLE", bundle);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
                }

                @Override
                public void onFailure(Call<Cart> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    public void passEmailToDeleteItem(String email, String productId, double total) {
        try {
            Call<User> call = userService.getUserByEmail(email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user == null) {
                        return;
                    }
                    deleteItemById(user.get_id(), productId);
                    Locale localeVN = new Locale("vi", "VN");
                    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                    String price = currencyVN.format(total);
                    tv_total.setText(price);
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.setCountProductToCart(mainActivity.getCountProduct() - 1);
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

    public void deleteItemById(String user, String productId) {
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

    public void passEmailToUpdateQuantity(int quantity, String email, String productId, double total) {
        try {
            Call<User> call = userService.getUserByEmail(email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user == null) {
                        return;
                    }
                    updateCartQuantity(quantity, user.get_id(), productId);
                    Locale localeVN = new Locale("vi", "VN");
                    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
                    String price = currencyVN.format(total);
                    tv_total.setText(price);
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.setCountProductToCart(mainActivity.getCountProduct() - 1);
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

    private void updateCartQuantity(int quantity, String user, String product) {
        try {
            Call<Cart> call = cartService.updateCartQuantity(quantity, user, product);
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
    public void callback(int position, Cart cart, double total) {
        cartRVAdapter = new CartRVAdapter(cart, email, CartFragment.this, total);
        cartRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cartRecycleView.setAdapter(cartRVAdapter);
        if (total == 0) {
            btn_checkout.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);
        } else {
            lottieAnimationView.setVisibility(View.GONE);
            btn_checkout.setVisibility(View.VISIBLE);
        }

        ArrayList<CartItem> cartItemsList = new ArrayList<>(cart.getItems());
//
//        Locale localeVN = new Locale("vi", "VN");
//        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
//        String price = currencyVN.format(total);
//        tv_total.setText(price);

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CheckoutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putDouble("Total", total);
                bundle.putSerializable("CartItems", cartItemsList);
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }
}
package com.bibon.furnitureshopping.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.adapters.OrderRVAdapter;
import com.bibon.furnitureshopping.models.Order;
import com.bibon.furnitureshopping.models.OrderDetail;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.OrderRepository;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.OrderService;
import com.bibon.furnitureshopping.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PendingFragment extends Fragment {

    OrderService orderService;
    UserService userService;
    OrderRVAdapter orderRVAdapter;
    RecyclerView recyclerViewOrder;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String email;
    ArrayList<Order> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Service Calling
        orderService = OrderRepository.getOrderService();
        userService = UserRepository.getUserService();

        // RecyclerView calling
        recyclerViewOrder = view.findViewById(R.id.rv_order_pending);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
        }

        getUserByEmail(email, "Pending");
    }

    private void getOrderByUserStatus(String user, String status) {
        this.orderList = new ArrayList<>();
        try {
            Call<Order[]> call = orderService.getOrdersByUserAndStatus(user, status);
            call.enqueue(new Callback<Order[]>() {
                @Override
                public void onResponse(Call<Order[]> call, Response<Order[]> response) {
                    Order[] orders = response.body();
                    if (orders == null) {
                        return;
                    }
                    for (Order order : orders) {
                        orderList.add(new Order(order.get_id(), order.getUser(), order.getAddress(), order.getOrderDate(), order.getPaymentMethod(), order.getTotalPrice(), order.getStatus(), order.getOrderDetails()));
                    }
                    Collections.reverse(orderList);
                    orderRVAdapter = new OrderRVAdapter(orderList);
                    recyclerViewOrder.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recyclerViewOrder.setAdapter(orderRVAdapter);
                }

                @Override
                public void onFailure(Call<Order[]> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getUserByEmail(String email, String status) {
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
                    getOrderByUserStatus(user.get_id(), status);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}
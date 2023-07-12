package com.bibon.furnitureshopping.fragments;

import android.content.Intent;
import android.os.Bundle;
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
import com.bibon.furnitureshopping.utils.UpdateCartRecycleView;
import com.bibon.furnitureshopping.activities.CheckoutActivity;
import com.bibon.furnitureshopping.adapters.CartRVAdapter;
import com.bibon.furnitureshopping.applications.CartApplication;
import com.bibon.furnitureshopping.models.Cart;
import com.bibon.furnitureshopping.models.CartList;

import java.util.Collections;


public class CartFragment extends Fragment implements UpdateCartRecycleView {

    CartList cartList;
    CartRVAdapter cartRVAdapter;
    TextView tv_total, tv_total_label, tv_currency;
    private RecyclerView cartRecycleView;
    Button btn_checkout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (cartList == null) {
            cartList = new CartList();
        }
        cartList = ((CartApplication) this.getActivity().getApplication()).getCartList();
        Collections.reverse(cartList.getCartList());

        cartRecycleView = getView().findViewById(R.id.rv_cart_item);
        cartRVAdapter = new CartRVAdapter(cartList, CartFragment.this);
        cartRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cartRecycleView.setAdapter(cartRVAdapter);

        tv_currency = getView().findViewById(R.id.tv_currency);
        tv_total_label = getView().findViewById(R.id.tv_total_label);
        tv_total = getView().findViewById(R.id.tv_total);
        double[] total = new double[1];
        for(Cart cart : cartList.getCartList()) {
            total[0] += cart.getCartQuantity() * cart.getPrice();
        }
        tv_total.setText(String.valueOf(total[0]));

        btn_checkout = getView().findViewById(R.id.btn_checkout);
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

    }


    @Override
    public void callback(int position, CartList items) {
        if (items.getCartList().isEmpty()) {
            items = cartList;
        }
        cartRVAdapter = new CartRVAdapter(items, CartFragment.this);
        cartRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        cartRecycleView.setAdapter(cartRVAdapter);
        tv_total = getView().findViewById(R.id.tv_total);
        double total = 0;
        for(Cart cart : cartList.getCartList()) {
            total += cart.getCartQuantity() * cart.getPrice();
        }
        tv_total.setText(total + "");
    }
}
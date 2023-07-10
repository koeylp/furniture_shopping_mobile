package com.bibon.furnitureshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.Address;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.AddressRepository;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.AddressService;
import com.bibon.furnitureshopping.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    UserService userService;
    AddressService addressService;
    Address address;
    String email;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView tv_name, tv_phone, tv_address_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        userService = UserRepository.geUserService();
        addressService = AddressRepository.getAddressService();

        ImageView img_back = (ImageView) findViewById(R.id.img_back);
        TextView tv_order_price = findViewById(R.id.tv_order_price);
        TextView tv_total = findViewById(R.id.tv_total);
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);
        tv_address_detail = findViewById(R.id.tv_address_detail);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        double total = args.getDouble("Total");

        tv_order_price.setText("$ " + String.valueOf(total));
        tv_total.setText("$ " + String.valueOf(total + 5));


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
        }

        getUserByEmail(email);

    }

    private void getAddressByUser(String user) {
        try {
            Call<Address[]> call = addressService.getAddressByUser(user);
            call.enqueue(new Callback<Address[]>() {
                @Override
                public void onResponse(Call<Address[]> call, Response<Address[]> response) {
                    Address[] addresses = response.body();
                    if (addresses == null) {
                        return;
                    }
                    for (Address address : addresses) {
                        if (address.getStatus() == 1 || addresses.length == 1) {
                            tv_name.setText(address.getFullname());
                            tv_phone.setText(address.getPhone());
                            tv_address_detail.setText(address.getAddress() + ", " + address.getWard() + ", "  + address.getDistrict() + ", " + address.getProvince());
                        }
                    }

                }

                @Override
                public void onFailure(Call<Address[]> call, Throwable t) {

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
                    getAddressByUser(user.get_id());
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
}
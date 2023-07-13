package com.bibon.furnitureshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.adapters.AddressRVAdapter;
import com.bibon.furnitureshopping.models.Address;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.AddressRepository;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.AddressService;
import com.bibon.furnitureshopping.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressShippingActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    AddressService addressService;
    ArrayList<Address> addressList;
    AddressRVAdapter addressRVAdapter;
    RecyclerView recyclerViewAddress;
    UserService userService;
    String email;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        try {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                email = currentUser.getDisplayName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_shipping);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        ImageView img_back = (ImageView) findViewById(R.id.img_back);
        Button btn_add_address = findViewById(R.id.btn_add_address);
        recyclerViewAddress = findViewById(R.id.rv_addresses);
        addressService = AddressRepository.getAddressService();
        userService = UserRepository.getUserService();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Fragment", "profile");
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });

        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddAddressShippingActivity.class);
                startActivity(intent);
            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
        }

        getUserByEmail(email);
    }

    private void getAddressByUser(String user) {
        this.addressList = new ArrayList<>();
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
                        addressList.add(new Address(address.get_id(), address.getUser(), address.getFullname(), address.getPhone(), address.getAddress(), address.getWard(), address.getDistrict(), address.getProvince(), address.getStatus()));
                    }
                    Collections.sort(addressList, Comparator.comparing(Address::getStatus));
                    Collections.reverse(addressList);
                    addressRVAdapter = new AddressRVAdapter(addressList, AddressShippingActivity.this);
                    recyclerViewAddress.setLayoutManager(new LinearLayoutManager(AddressShippingActivity.this, LinearLayoutManager.VERTICAL, false));
                    recyclerViewAddress.setAdapter(addressRVAdapter);
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

    public void setDefaultAddress(String id, String user, int position) {
        try {
            Call<Address>  call = addressService.setDefaultAddress(id, user);
            call.enqueue(new Callback<Address>() {
                @Override
                public void onResponse(Call<Address> call, Response<Address> response) {
                    Toast.makeText(AddressShippingActivity.this, "Done setting default address", Toast.LENGTH_SHORT).show();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        email = currentUser.getEmail();
                    }
                    getUserByEmail(email);
                }

                @Override
                public void onFailure(Call<Address> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}
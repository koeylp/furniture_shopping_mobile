package com.bibon.furnitureshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.adapters.AddressRVAdapter;
import com.bibon.furnitureshopping.adapters.CategoryRVAdapter;
import com.bibon.furnitureshopping.fragments.HomeFragment;
import com.bibon.furnitureshopping.models.Address;
import com.bibon.furnitureshopping.models.Category;
import com.bibon.furnitureshopping.repositories.AddressRepository;
import com.bibon.furnitureshopping.services.AddressService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressShippingActivity extends AppCompatActivity {

    AddressService addressService;
    ArrayList<Address> addressList;
    AddressRVAdapter addressRVAdapter;
    RecyclerView recyclerViewAddress;

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

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddAddressShippingActivity.class);
                startActivity(intent);
            }
        });

        getAddressByEmail("asd");
    }

    private void getAddressByEmail(String email) {
        this.addressList = new ArrayList<>();
        try {
            Call<Address[]> call = addressService.getAddressByEmail();
            call.enqueue(new Callback<Address[]>() {
                @Override
                public void onResponse(Call<Address[]> call, Response<Address[]> response) {
                    Address[] addresses = response.body();
                    if (addresses == null) {
                        return;
                    }
                    for (Address address : addresses) {
                        addressList.add(new Address(address.getFullname(), address.getAddress(), address.getWard(), address.getDistrict(), address.getProvince()));
                    }
                    System.out.println(addressList.size());
                    addressRVAdapter = new AddressRVAdapter(addressList);
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


}
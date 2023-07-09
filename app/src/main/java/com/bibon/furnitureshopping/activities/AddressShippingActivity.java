package com.bibon.furnitureshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.Address;
import com.bibon.furnitureshopping.models.District;
import com.bibon.furnitureshopping.models.Province;
import com.bibon.furnitureshopping.models.Ward;
import com.bibon.furnitureshopping.repositories.AddressRepository;
import com.bibon.furnitureshopping.services.AddressService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressShippingActivity extends AppCompatActivity {


    ArrayList<Address> addressList;

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



    }



}
package com.bibon.furnitureshopping.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bibon.furnitureshopping.R;
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

    AddressService addressService;
    ArrayList<Province> provinceList;
    ArrayList<District> districtList;
    ArrayList<Ward> wardList;

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

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        addressService = AddressRepository.getAddressService();
        getAllProvinces();

    }


    private void getAllProvinces() {
        this.districtList = new ArrayList<>();
        try {
            Call<Province[]> call = addressService.getAllProvinces();
            call.enqueue(new Callback<Province[]>() {
                @Override
                public void onResponse(Call<Province[]> call, Response<Province[]> response) {
                    Province[] provinces = response.body();
                    if (provinces == null) {
                        return;
                    }
                    for (Province province : provinces) {
                        provinceList.add(new Province(province.getName(), province.getCode(), province.getDivision_type(), province.getCodename(), province.getPhone_code(), province.getDistricts()));
                    }

                }

                @Override
                public void onFailure(Call<Province[]> call, Throwable t) {
                    System.out.println("kkk");
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getDistrictByProvinceCode(int code) {
        this.districtList = new ArrayList<>();
        try {
            Call<District[]> call = addressService.getAllDistricts();
            call.enqueue(new Callback<District[]>() {
                @Override
                public void onResponse(Call<District[]> call, Response<District[]> response) {
                    District[] districts = response.body();
                    if (districts == null) {
                        return;
                    }
                    for (District district : districts) {
                        if (code == district.getProvince_code()) {
                            districtList.add(new District(district.getName(), district.getCode(), district.getDivision_type(), district.getCodename(), district.getProvince_code(), district.getWards()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<District[]> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getWardByDistrictCode(int code) {
        this.wardList = new ArrayList<>();
        try {
            Call<Ward[]> call = addressService.getAllWards();
            call.enqueue(new Callback<Ward[]>() {
                @Override
                public void onResponse(Call<Ward[]> call, Response<Ward[]> response) {
                    Ward[] wards = response.body();
                    if (wards == null) {
                        return;
                    }
                    for (Ward ward : wards) {
                        if (code == ward.getDistrict_code()) {
                            wardList.add(new Ward(ward.getName(), ward.getCode(), ward.getDivision_type(), ward.getCodename(), ward.getDistrict_code()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<Ward[]> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}
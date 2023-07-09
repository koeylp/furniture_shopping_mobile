package com.bibon.furnitureshopping.services;

import com.bibon.furnitureshopping.models.District;
import com.bibon.furnitureshopping.models.Province;
import com.bibon.furnitureshopping.models.Ward;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AddressService {

    String ADDRESS = "address";
    @GET(ADDRESS + "/provinces")
    Call<Province[]> getAllProvinces();

    @GET(ADDRESS + "/districts")
    Call<District[]> getAllDistricts();

    @GET(ADDRESS + "/wards")
    Call<Ward[]> getAllWards();

}

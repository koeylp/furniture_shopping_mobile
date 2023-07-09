package com.bibon.furnitureshopping.services;

import com.bibon.furnitureshopping.models.Address;
import com.bibon.furnitureshopping.models.District;
import com.bibon.furnitureshopping.models.Province;
import com.bibon.furnitureshopping.models.Ward;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AddressService {

    String ADDRESS = "address";
    @GET(ADDRESS + "/provinces")
    Call<Province[]> getAllProvinces();

    @GET(ADDRESS + "/districts")
    Call<District[]> getAllDistricts();

    @GET(ADDRESS + "/wards")
    Call<Ward[]> getAllWards();

    @GET(ADDRESS + "/{email}")
    Call<Address[]> getAddressByEmail();

    @POST(ADDRESS)
    Call<Address> addAddress(@Body Address address);

}

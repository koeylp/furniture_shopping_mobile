package com.bibon.furnitureshopping.services;

import com.bibon.furnitureshopping.models.Address;
import com.bibon.furnitureshopping.models.District;
import com.bibon.furnitureshopping.models.Province;
import com.bibon.furnitureshopping.models.Ward;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AddressService {

    String ADDRESS = "address";
    @GET(ADDRESS + "/provinces")
    Call<Province[]> getAllProvinces();

    @GET(ADDRESS + "/districts")
    Call<District[]> getAllDistricts();

    @GET(ADDRESS + "/wards")
    Call<Ward[]> getAllWards();

    @GET(ADDRESS + "/{user}")
    Call<Address[]> getAddressByUser(@Path("user") Object user);

    @POST(ADDRESS)
    Call<Address> addAddress(@Body Address address);

    @PUT(ADDRESS + "/{id}/{userId}")
    Call<Address> setDefaultAddress(@Path("id") Object id, @Path("userId") Object userId);

}

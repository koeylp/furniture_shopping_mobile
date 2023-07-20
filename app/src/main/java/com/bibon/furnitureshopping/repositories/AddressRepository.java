package com.bibon.furnitureshopping.repositories;

import com.bibon.furnitureshopping.API.APIClient;
import com.bibon.furnitureshopping.services.AddressService;

public class AddressRepository {
    public static AddressService getAddressService() {
        return APIClient.getClient().create(AddressService.class);
    }
}

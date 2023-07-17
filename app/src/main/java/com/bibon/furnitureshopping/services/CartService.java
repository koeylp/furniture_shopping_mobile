package com.bibon.furnitureshopping.services;

import com.bibon.furnitureshopping.models.Cart;
import com.bibon.furnitureshopping.models.CartAdding;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CartService {
    String CART = "cart";

    @POST(CART)
    Call<CartAdding> addToCart(@Body CartAdding cart);

    @GET(CART + "/{user}")
    Call<Cart> getCartByUser(@Path("user") Object user);

    @PUT(CART + "/{user}/{product}")
    Call<Cart> deleteCartItemById(@Path("user") Object user, @Path("product") Object product);

    @PUT(CART + "/quantity/{user}/{product}/{func}")
    Call<Cart> editCartQuantity(@Path("user") Object user, @Path("product") Object product, @Path("func") Object func);

}

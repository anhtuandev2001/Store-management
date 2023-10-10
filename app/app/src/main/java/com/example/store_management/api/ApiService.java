package com.example.store_management.api;

import com.example.store_management.address.AddressInsertRequest;
import com.example.store_management.address.AddressInsertResponse;
import com.example.store_management.address.AddressResponse;
import com.example.store_management.address.AddressUpdateRequest;
import com.example.store_management.cart.CartInsertRequest;
import com.example.store_management.cart.CartInsertResponse;
import com.example.store_management.cart.CartRequest;
import com.example.store_management.cart.CartResponse;
import com.example.store_management.category.CategoryResponse;
import com.example.store_management.login.LoginRequest;
import com.example.store_management.login.LoginResponse;
import com.example.store_management.order.OrderInsertRequest;
import com.example.store_management.order.OrderInsertResponse;
import com.example.store_management.order.OrderResponse;
import com.example.store_management.product.ProductResponse;
import com.example.store_management.login.RegisterRequest;
import com.example.store_management.login.RegisterResponse;
import com.example.store_management.user.UserAddressRequest;
import com.example.store_management.user.UserFavoriteRequest;
import com.example.store_management.user.UserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("users/login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);

    @POST("users/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

    @GET("users/{userId}")
    Call<UserResponse> getUser(@Path("userId") String userId);

    @GET("products")
    Call<ProductResponse> getProducts();

    @GET("category")
    Call<CategoryResponse> getCategory();

    @GET("cart/{userId}")
    Call<CartResponse> getCart(@Path("userId") String userId);

    @PATCH("users")
    Call<UserResponse> updateAddress(@Body UserAddressRequest userAddressRequest);

    @PATCH("users/favorite")
    Call<UserResponse> updateFavoriteUser(@Body UserFavoriteRequest userFavoriteRequest);

    @PATCH("cart")
    Call<CartResponse> updateCart(@Body CartRequest cartRequest);

    @POST("cart")
    Call<CartInsertResponse> insertCart(@Body CartInsertRequest cartInsertRequest);

    @DELETE("cart/{cartId}")
    Call<CartInsertResponse> deleteCart(@Path("cartId") String cartId);

    @GET("order/{userId}")
    Call<OrderResponse> getOrder(@Path("userId") String userId);

    @POST("order")
    Call<OrderInsertResponse> insertOrder(@Body OrderInsertRequest orderRequest);

    @GET("address/{userId}")
    Call<AddressResponse> getAddress(@Path("userId") String userId);

    @GET("address/id/{id}")
    Call<AddressInsertResponse> getAddressById(@Path("id") String id);

    @POST("address")
    Call<AddressInsertResponse> inserAddress(@Body AddressInsertRequest addressInsertRequest);

    @PATCH("address")
    Call<AddressInsertResponse> updateAddress(@Body AddressUpdateRequest addressUpdateRequest);

    @DELETE("address/{id}")
    Call<AddressInsertResponse> deleteAddress(@Path("id") String id);
}

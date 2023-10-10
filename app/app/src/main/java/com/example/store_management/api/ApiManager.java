package com.example.store_management.api;

import android.util.Log;

import com.example.store_management.address.AddressInsertRequest;
import com.example.store_management.address.AddressInsertResponse;
import com.example.store_management.address.AddressResponse;
import com.example.store_management.address.AddressUpdateRequest;
import com.example.store_management.cart.CartInsertRequest;
import com.example.store_management.cart.CartInsertResponse;
import com.example.store_management.cart.CartRequest;
import com.example.store_management.cart.CartResponse;
import com.example.store_management.category.CategoryResponse;
import com.example.store_management.common.DataManager;
import com.example.store_management.order.OrderInsertRequest;
import com.example.store_management.order.OrderInsertResponse;
import com.example.store_management.order.OrderResponse;
import com.example.store_management.product.ProductResponse;
import com.example.store_management.user.UserAddressRequest;
import com.example.store_management.user.UserFavoriteRequest;
import com.example.store_management.user.UserResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private final ApiService apiService;

    public ApiManager(String baseUrl, String token) {
        OkHttpClient httpClient = createHttpClient(token);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private OkHttpClient createHttpClient(String token) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new TokenInterceptor(token));
        return httpClient.build();
    }

    public void getUserById(Callback<UserResponse> callback) {
        String id = DataManager.getInstance().getUserData().getId();
        Log.d("akjfjkash", "onCreate: " + id);
        Call<UserResponse> call = apiService.getUser(id);
        call.enqueue(callback);
    }

    public void updateAddressDefautl(Callback<UserResponse> callback) {
        UserAddressRequest userAddressRequest = DataManager.getInstance().getUserAddressRequest();
        Call<UserResponse> call = apiService.updateAddress(userAddressRequest);
        call.enqueue(callback);
    }

    public void updateFavoriteUser(Callback<UserResponse> callback) {
        UserFavoriteRequest userFavoriteRequest = DataManager.getInstance().getUserFavoriteRequest();
        Log.d("klajflkasjd", "updateFavoriteUser: "+userFavoriteRequest);
        Call<UserResponse> call = apiService.updateFavoriteUser(userFavoriteRequest);
        call.enqueue(callback);
    }

    public void getProducts(Callback<ProductResponse> callback) {
        Call<ProductResponse> call = apiService.getProducts();
        call.enqueue(callback);
    }

    public void getCategory(Callback<CategoryResponse> callback) {
        Call<CategoryResponse> call = apiService.getCategory();
        call.enqueue(callback);
    }

    public void getCart(Callback<CartResponse> callback) {
        String userId = DataManager.getInstance().getUserData().getId();
        Call<CartResponse> call = apiService.getCart(userId);
        call.enqueue(callback);
    }

    public void updateCart(Callback<CartResponse> callback) {
        CartRequest cartRequest = DataManager.getInstance().getCartRequest();
        Call<CartResponse> call = apiService.updateCart(cartRequest);
        call.enqueue(callback);
    }

    public void deleteCart(Callback<CartInsertResponse> callback) {
        String cartId = DataManager.getInstance().getCartId();
        Call<CartInsertResponse> call = apiService.deleteCart(cartId);
        call.enqueue(callback);
    }

    public void insertCart(Callback<CartInsertResponse> callback) {
        CartInsertRequest cartInsertRequest = DataManager.getInstance().getCartInsertRequest();
        Call<CartInsertResponse> call = apiService.insertCart(cartInsertRequest);
        call.enqueue(callback);
    }

    public void getOrder(Callback<OrderResponse> callback) {
        String userId = DataManager.getInstance().getUserData().getId();
        Call<OrderResponse> call = apiService.getOrder(userId);
        call.enqueue(callback);
    }

    public void insertOrder(Callback<OrderInsertResponse> callback) {
        OrderInsertRequest orderInsertRequest = DataManager.getInstance().getOrderInsertRequest();
        Call<OrderInsertResponse> call = apiService.insertOrder(orderInsertRequest);
        call.enqueue(callback);
    }

    public void getAddress(Callback<AddressResponse> callback) {
        String userId = DataManager.getInstance().getUserData().getId();
        Call<AddressResponse> call = apiService.getAddress(userId);
        call.enqueue(callback);
    }

    public void getAddressById(Callback<AddressInsertResponse> callback) {
        String id = DataManager.getInstance().getUserData().getDefault_shipping_id();
        Call<AddressInsertResponse> call = apiService.getAddressById(id);
        call.enqueue(callback);
    }

    public void insertAddress(Callback<AddressInsertResponse> callback) {
        AddressInsertRequest addressInsertRequest = DataManager.getInstance().getAddressInsertRequest();
        Call<AddressInsertResponse> call = apiService.inserAddress(addressInsertRequest);
        call.enqueue(callback);
    }

    public void updateAddress(Callback<AddressInsertResponse> callback) {
        AddressUpdateRequest addressUpdateRequest = DataManager.getInstance().getAddressUpdateRequest();
        Call<AddressInsertResponse> call = apiService.updateAddress(addressUpdateRequest);
        call.enqueue(callback);
    }

    public void deleteAddress(Callback<AddressInsertResponse> callback) {
        String addressId = DataManager.getInstance().getAddressId();
        Call<AddressInsertResponse> call = apiService.deleteAddress(addressId);
        call.enqueue(callback);
    }
}

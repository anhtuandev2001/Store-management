package com.example.store_management.product;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Product> productList;

    public String getMessage() {
        return message;
    }

    public List<Product> getProductList() {
        return productList;
    }
}

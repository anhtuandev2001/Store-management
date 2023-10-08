package com.example.store_management.order;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private List<Order> orderList;

    public String getMessage() {
        return message;
    }

    public List<Order> getOrderList() {
        return orderList;
    }
}

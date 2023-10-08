package com.example.store_management.order;

import com.google.gson.annotations.SerializedName;

public class OrderInsertResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private Order data; // Sửa thành một đối tượng Cart thay vì danh sách

    public String getMessage() {
        return message;
    }

    public Order getData() {
        return data;
    }
}

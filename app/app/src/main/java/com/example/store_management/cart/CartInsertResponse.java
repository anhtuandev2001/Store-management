package com.example.store_management.cart;

import com.example.store_management.cart.Cart;
import com.google.gson.annotations.SerializedName;

public class CartInsertResponse {
    // Thuộc tính đại diện cho thông báo từ phản hồi
    @SerializedName("message")
    private String message;

    // Thuộc tính đại diện cho dữ liệu của giỏ hàng đã được chèn
    @SerializedName("data")
    private Cart data;

    // Phương thức trả về thông báo từ phản hồi
    public String getMessage() {
        return message;
    }

    // Phương thức trả về dữ liệu của giỏ hàng đã được chèn
    public Cart getData() {
        return data;
    }
}

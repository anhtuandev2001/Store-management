package com.example.store_management.cart;

import com.example.store_management.cart.Cart;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CartResponse {
    // Thuộc tính đại diện cho thông báo từ phản hồi
    @SerializedName("message")
    private String message;

    // Thuộc tính đại diện cho danh sách các mục trong giỏ hàng
    @SerializedName("data")
    private List<Cart> cartList;

    // Phương thức trả về thông báo từ phản hồi
    public String getMessage() {
        return message;
    }

    // Phương thức trả về danh sách các mục trong giỏ hàng
    public List<Cart> getCartList() {
        return cartList;
    }
}

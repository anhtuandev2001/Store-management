package com.example.store_management.user;

import com.example.store_management.address.Address;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse {
    // Thuộc tính đại diện cho thông báo từ phản hồi
    @SerializedName("message")
    private String message;

    // Thuộc tính đại diện cho danh sách các mục trong giỏ hàng
    @SerializedName("data")
    private UserData userData;

    // Phương thức trả về thông báo từ phản hồi
    public String getMessage() {
        return message;
    }

    // Phương thức trả về danh sách các mục trong giỏ hàng
    public UserData getUserData() {
        return userData;
    }
}

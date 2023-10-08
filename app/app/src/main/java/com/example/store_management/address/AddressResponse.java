package com.example.store_management.address;

import com.example.store_management.cart.Cart;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressResponse {
    // Thuộc tính đại diện cho thông báo từ phản hồi
    @SerializedName("message")
    private String message;

    // Thuộc tính đại diện cho danh sách các mục trong giỏ hàng
    @SerializedName("data")
    private List<Address> addressList;

    // Phương thức trả về thông báo từ phản hồi
    public String getMessage() {
        return message;
    }

    // Phương thức trả về danh sách các mục trong giỏ hàng
    public List<Address> getAddressList() {
        return addressList;
    }
}

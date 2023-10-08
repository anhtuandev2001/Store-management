package com.example.store_management.user;

public class UserAddressRequest {
    private String id;
    private String default_shipping_id;

    public UserAddressRequest(String id, String default_shipping_id) {
        this.id = id;
        this.default_shipping_id = default_shipping_id;
    }

    public String toString() {
        return "cart{" +
                "id='" + id + '\'' +
                ", default_shipping_id='" + default_shipping_id + '\'' +
                '}';
    }
}

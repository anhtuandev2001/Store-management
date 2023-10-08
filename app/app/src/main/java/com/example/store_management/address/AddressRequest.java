package com.example.store_management.address;

public class AddressRequest {
    private String id;
    private String quantity;

    public AddressRequest(String id, String quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public String toString() {
        return "cart{" +
                "id='" + id + '\'' +
                ", quantity='" + quantity + '\'' +
                '}';
    }
}

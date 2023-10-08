package com.example.store_management.cart;

public class CartRequest {
    private String id;
    private String quantity;

    public CartRequest(String id, String quantity) {
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

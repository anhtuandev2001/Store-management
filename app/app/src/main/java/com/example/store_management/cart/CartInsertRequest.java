package com.example.store_management.cart;

public class CartInsertRequest {
    private String productId;
    private String userId;
    private String quantity;
    private String color;

    public CartInsertRequest(String productId, String userId, String quantity, String color) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
        this.color = color;
    }

    public String getProductId() {
        return productId;
    }

    public String getUserId() {
        return userId;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "CartInsertRequest{" +
                "productId='" + productId + '\'' +
                ", userId='" + userId + '\'' +
                ", quantity=" + quantity +
                ", color='" + color + '\'' +
                '}';
    }
}

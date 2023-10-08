package com.example.store_management.cart;

import java.io.Serializable;

public class Cart implements Serializable {
    private String productId;

    private String _id;
    private int quantity;

    private String color;

    private String userId;
    public Cart(String _id, String productId, int quantity, String color, String userId) {
        this.productId = productId;
        this._id = _id;
        this.quantity = quantity;
        this.color = color;
        this.userId = userId;
    }
    public String getUserId() {
        return userId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setCartId(String _id) {
        this._id = _id;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductId() {
        return productId;
    }

    public String getCartId() {
        return _id;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getColor() {
        return color;
    }
}


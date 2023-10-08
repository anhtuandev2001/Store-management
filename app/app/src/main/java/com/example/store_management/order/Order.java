package com.example.store_management.order;

import java.io.Serializable;

public class Order implements Serializable {

    private String _id;
    private String orderDate;
    private int quantity;
    private double total;
    private String status;
    private String userId;
    private String cartList;
    private String addressId;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCart() {
        return cartList;
    }

    public void setCart(String cartList) {
        this.cartList = cartList;
    }

    public String getCartList() {
        return cartList;
    }

    public void setCartList(String cartList) {
        this.cartList = cartList;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Order(String _id, String orderDate, int quantity, double total, String status, String userId, String cartList, String addressId) {
        this._id = _id;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
        this.userId = userId;
        this.cartList = cartList;
        this.addressId = addressId;
    }

    public String toString() {
        return "Order{" +
                "id='" + _id + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", quantity='" + quantity + '\'' +
                ", total=" + total + '\'' +
                ", status='" + status + '\'' +
                ", userId='" + userId + '\'' +
                ", cartList='" + cartList + '\'' +
                // Các thông tin khác
                '}';
    }

}


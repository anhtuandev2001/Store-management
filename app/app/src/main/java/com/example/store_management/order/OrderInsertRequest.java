package com.example.store_management.order;

public class OrderInsertRequest {
    private String orderDate;
    private String quantity;
    private String total;
    private String status;
    private String userId;
    private String cartList;
    private String addressId;

        public OrderInsertRequest(String orderDate, String quantity, String total, String status, String userId, String cartList, String addressId) {
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.total = total;
        this.status = status;
        this.userId = userId;
        this.cartList = cartList;
        this.addressId = addressId;
    }

    public String toString() {
        return "Product{" +
                "orderDate='" + orderDate + '\'' +
                ", quantity='" + quantity + '\'' +
                ", total=" + total +
                ", costatuslor='" + status + '\'' +
                ", userId='" + userId + '\'' +
                ", cartList='" + cartList + '\'' +
                ", addressId='" + addressId + '\'' +
                // Các thông tin khác
                '}';
    }
}

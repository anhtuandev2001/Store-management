package com.example.store_management.address;

public class AddressInsertRequest {
    private String fullName;
    private String userId;
    private String address;

    private String phoneNumber;

    public AddressInsertRequest(String fullName, String userId,String address, String phoneNumber) {
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
    }

    public String toString() {
        return "cart{" +
                "fullName='" + fullName + '\'' +
                ", userId='" + userId + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

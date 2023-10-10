package com.example.store_management.address;

public class AddressUpdateRequest {
    private String fullName;
    private String id;
    private String address;
    private String phoneNumber;

    public AddressUpdateRequest(String fullName, String id, String address, String phoneNumber) {
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public String toString() {
        return "cart{" +
                "fullName='" + fullName + '\'' +
                ", id='" + id + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}

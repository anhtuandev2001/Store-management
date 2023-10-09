package com.example.store_management.address;

public class AddressUpdateRequest {
    private String fullName;
    private String id;
    private String address;

    public AddressUpdateRequest(String fullName, String id, String address) {
        this.fullName = fullName;
        this.address = address;
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

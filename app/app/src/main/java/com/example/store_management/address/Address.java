package com.example.store_management.address;

import java.io.Serializable;

public class Address implements Serializable {
    private String _id;

    private String fullName;

    private String address;

    private String userId;

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public Address(String _id, String fullName, String address, String userId) {
        this._id = _id;
        this.fullName = fullName;
        this.address = address;
        this.userId = userId;
    }

}


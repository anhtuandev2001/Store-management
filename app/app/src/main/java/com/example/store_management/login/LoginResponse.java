package com.example.store_management.login;

import com.example.store_management.user.UserData;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    private String message;
    @SerializedName("data")
    private UserData userData;

    public String getMessage() {
        return message;
    }

    public UserData getUserData() {
        return userData;
    }
}

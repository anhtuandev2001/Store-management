package com.example.store_management.user;

import com.google.gson.annotations.SerializedName;

public class UserData {
    @SerializedName("_id")
    private String _id;
    private String name;
    private String email;
    private String password;
    private String default_shipping_id;
    private String favoritesList;

    public UserData(String _id, String name, String email, String password, String default_shipping_id, String favoritesList) {
        this._id = _id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.default_shipping_id = default_shipping_id;
        this.favoritesList = favoritesList;
    }

    public String getDefault_shipping_id() {
        return default_shipping_id;
    }

    public void setDefault_shipping_id(String default_shipping_id) {
        this.default_shipping_id = default_shipping_id;
    }

    public String getFavoritesList() {
        return favoritesList;
    }

    public void setFavoritesList(String favoritesList) {
        this.favoritesList = favoritesList;
    }

    public String getId() {
        return _id;
    }

    public void setId(String id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;



    // Getter methods for UserData properties
}

package com.example.store_management.user;

public class UserFavoriteRequest {
    private String id;
    private String favoritesList;

    public UserFavoriteRequest(String id, String favoritesList) {
        this.id = id;
        this.favoritesList = favoritesList;
    }

    public String toString() {
        return "cart{" +
                "id='" + id + '\'' +
                ", favoritesList='" + favoritesList + '\'' +
                '}';
    }
}

package com.example.store_management.category;

import java.io.Serializable;

public class Category implements Serializable {
    private  String image;
    private  String name;
    private  String _id;


    public Category(String _id, String image, String name) {
        this.image = image;
        this._id = _id;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }

    public String getImageList() {
        return image;
    }

    public String getName() {
        return name;
    }

}

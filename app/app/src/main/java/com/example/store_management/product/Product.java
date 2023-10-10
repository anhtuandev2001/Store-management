package com.example.store_management.product;

import java.io.Serializable;

public class Product implements Serializable {
    private final String _id;
    private final String image;
    private final String name;
    private final double price;
    private final String description;
    private final String categoryId;
    private final String colorList;

    public String get_id() {
        return _id;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getId() {
        return _id;
    }

    public String getImagesList() {
        return image;
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getColorList() {
        return colorList;
    }

    public Product(String _id, String name, double price, String description, String categoryId, String colorList, String image) {
        this._id = _id;
        this.price = price;
        this.image = image;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.colorList = colorList;
    }

    public String toString() {
        return "Product{" +
                "id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", color='" + colorList + '\'' +
                ", image='" + image + '\'' +
                // Các thông tin khác
                '}';
    }

}

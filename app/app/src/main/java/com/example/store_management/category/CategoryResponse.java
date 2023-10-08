package com.example.store_management.category;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CategoryResponse {
    // Thuộc tính đại diện cho thông báo từ phản hồi
    @SerializedName("message")
    private String message;

    // Thuộc tính đại diện cho danh sách các danh mục sản phẩm
    @SerializedName("data")
    private List<Category> categoryList;

    // Phương thức trả về thông báo từ phản hồi
    public String getMessage() {
        return message;
    }

    // Phương thức trả về danh sách các danh mục sản phẩm
    public List<Category> getCategoryList() {
        return categoryList;
    }
}

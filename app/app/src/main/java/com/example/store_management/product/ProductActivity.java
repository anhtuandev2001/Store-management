package com.example.store_management.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_management.R;
import com.example.store_management.user.UserData;
import com.example.store_management.user.UserActivity;
import com.example.store_management.cart.CartActivity;
import com.example.store_management.api.ApiManager;
import com.example.store_management.category.Category;
import com.example.store_management.category.CategoryAdapter;
import com.example.store_management.category.CategoryResponse;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.order.OrderActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends Activity {
    private RecyclerView categoryRecyclerView;
    private RecyclerView productRecyclerView;
    private List<Category> categoryList;
    private List<Product> productList;
    private ProgressDialog progressDialog;
    private DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_activity);

        categoryRecyclerView = findViewById(R.id.categoryRecyclerView);
        productRecyclerView = findViewById(R.id.addressRecyclerView);
        ImageView iconCart = findViewById(R.id.iconCart);
        EditText txtSearch = findViewById(R.id.txtSearch);
        ImageView btnClose = findViewById(R.id.btnClose);
        CardView searchContainer = findViewById(R.id.searchContainer);
        ImageView btnSearch = findViewById(R.id.btnSearch);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        iconCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng đến màn hình "My Cart" (MyCartActivity)
                Intent intent = new Intent(ProductActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContainer.setVisibility(View.VISIBLE);
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchContainer.setVisibility(View.INVISIBLE);

                // Close the keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(txtSearch.getWindowToken(), 0);
            }
        });


        // Add a TextWatcher to capture text input events
        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called before the text is changed.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String userInput = charSequence.toString().toLowerCase(); // Convert user input to lowercase for case-insensitive search
                Log.d("onTextChanged", "onTextChanged: " + userInput);

                String productFilterQuantityId = dataManager.getProductFilterCategoryId();
                dataManager.setProductFilterSearchInput(userInput);
                List<Product> filteredList = new ArrayList<>();

                for (Product product : productList) {
                    String productName = product.getName().toLowerCase();
                    if (productName.contains(userInput) && productFilterQuantityId.equals(product.getCategoryId())) {
                        filteredList.add(product);
                    }
                }
                setRecycleViewProduct(filteredList);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called after the text has changed.
                Log.d("onTextChangedonTextChanged", "enter: ");
            }
        });

        // Khởi tạo ProgressDialog và cấu hình
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        productList = dataManager.getProductList() != null ? dataManager.getProductList() : new ArrayList<>();
        categoryList = dataManager.getCategoryList() != null ? dataManager.getCategoryList() : new ArrayList<>();

        if (productList.size() > 0 && categoryList.size() > 0) {
            setRecycleViewProduct(productList);
            setRecycleViewCategory();
        } else {
            // Gọi hàm fetchCategoryAndProductData() để lấy dữ liệu
            fetchCategoryAndProductData();
        }

        bottomNavigationView.setSelectedItemId(R.id.navigation_home);

        // Thiết lập lắng nghe sự kiện khi item được chọn
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    // Chuyển đến ProductActivity khi item "navigation_home" được chọn
                    Intent intent = new Intent(ProductActivity.this, ProductActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_favourite) {
                    // Xử lý khi item "navigation_favourite" được chọn
                    return true;
                } else if (item.getItemId() == R.id.navigation_order) {
                    // Xử lý khi item "navigation_order" được chọn
                    Intent intent = new Intent(ProductActivity.this, OrderActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_person) {
                    Intent intent = new Intent(ProductActivity.this, UserActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void fetchCategoryAndProductData() {
        UserData userData = dataManager.getUserData();
        ApiManager apiManager = new ApiManager(Constants.BASE_URL, userData.getToken());
        // Hiển thị ProgressDialog khi bắt đầu gọi API
        progressDialog.show();

        fetchProductData(apiManager);
        fetchCategoryData(apiManager);
    }

    private void fetchProductData(ApiManager apiManager) {
        apiManager.getProducts(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                // Ẩn ProgressDialog khi có kết quả hoặc xảy ra lỗi
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    ProductResponse productResponse = response.body();
                    if (productResponse != null) {
                        productList = productResponse.getProductList();
                        // Lưu vào DataManager
                        dataManager.setProductList(productList);
                        setRecycleViewProduct(productList);
                    } else {
                        // Xử lý lỗi khi dữ liệu rỗng
                        showErrorMessage("No products available");
                    }
                } else {
                    // Xử lý lỗi khi gọi API không thành công
                    showErrorMessage("Failed to fetch product data");
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                // Ẩn ProgressDialog khi có lỗi
                progressDialog.dismiss();
                // Xử lý lỗi khi gọi API thất bại
                showErrorMessage("Network error. Please try again later.");
            }
        });
    }

    private void fetchCategoryData(ApiManager apiManager) {
        apiManager.getCategory(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful()) {
                    CategoryResponse categoryResponse = response.body();
                    if (categoryResponse != null) {
                        categoryList = categoryResponse.getCategoryList();
                        // Lưu vào DataManager
                        dataManager.setCategoryList(categoryList);
                        setRecycleViewCategory();
                    } else {
                        // Xử lý lỗi khi dữ liệu rỗng
                        showErrorMessage("No categories available");
                    }
                } else {
                    // Xử lý lỗi khi gọi API không thành công
                    showErrorMessage("Failed to fetch category data");
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                // Xử lý lỗi khi gọi API thất bại
                showErrorMessage("Network error. Please try again later.");
            }
        });
    }

    public void setRecycleViewProduct(List<Product> productList) {
        ProductAdapter productAdapter = new ProductAdapter(ProductActivity.this, productList);
        GridLayoutManager layoutManager = new GridLayoutManager(ProductActivity.this, 2);
        productRecyclerView.setLayoutManager(layoutManager);
        productRecyclerView.setAdapter(productAdapter);
    }

    private void setRecycleViewCategory() {
        CategoryAdapter categoryAdapter = new CategoryAdapter(ProductActivity.this, categoryList, ProductActivity.this);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(ProductActivity.this, LinearLayoutManager.HORIZONTAL, false));
        categoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void showErrorMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Error")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý khi người dùng nhấn nút OK
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}

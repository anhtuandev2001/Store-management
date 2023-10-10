package com.example.store_management.favourite;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_management.R;
import com.example.store_management.api.ApiManager;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.order.OrderActivity;
import com.example.store_management.product.Product;
import com.example.store_management.product.ProductActivity;
import com.example.store_management.user.UserActivity;
import com.example.store_management.user.UserData;
import com.example.store_management.user.UserResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteActivity extends AppCompatActivity {
    private RecyclerView favouritesRecyclerView;
    private List<Product> filteredProductList = new ArrayList<>();
    private DataManager dataManager = DataManager.getInstance();
    private ProgressDialog progressDialog;
    private UserData userData = dataManager.getUserData();
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favoutite_activity);

        favouritesRecyclerView = findViewById(R.id.favouritesRecyclerView);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        progressDialog.show();
        fetchDataFromApi();

        bottomNavigationView.setSelectedItemId(R.id.navigation_favourite);
        // Thiết lập lắng nghe sự kiện khi item được chọn
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    // Chuyển đến ProductActivity khi item "navigation_home" được chọn
                    Intent intent = new Intent(FavouriteActivity.this, ProductActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_favourite) {
                    // Xử lý khi item "navigation_favourite" được chọn
                    Intent intent = new Intent(FavouriteActivity.this, FavouriteActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_order) {
                    // Xử lý khi item "navigation_order" được chọn
                    Intent intent = new Intent(FavouriteActivity.this, OrderActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_person) {
                    Intent intent = new Intent(FavouriteActivity.this, UserActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

    }

    private void setRecyclerViewFavourite() {
        favouritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        FavouriteAdapter favouriteAdapter = new FavouriteAdapter(this, filteredProductList);
        favouritesRecyclerView.setAdapter(favouriteAdapter);
    }

    public void fetchDataFromApi() {
        ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());
        apiManager.getUserById(new Callback<UserResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.d("akjfjkash", "onCreate: " + response);

                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        UserData userData = userResponse.getUserData();
                        progressDialog.dismiss();
                        dataManager.setUserData(userData);

                        String favouriteListUser = userData.getFavoritesList();
                        Log.d("akjfjkash", "onCreate: " + favouriteListUser);

                        if (favouriteListUser != null) {
                            String[] favouriteList = userData.getFavoritesList().split(",");
                            Log.d("akjfjkash", "onCreate: " + favouriteList);
                            List<Product> productList = dataManager.getProductList();

                            for (Product product : productList) {
                                if (Arrays.asList(favouriteList).contains(product.getId())) {
                                    filteredProductList.add(product);
                                }
                            }
                        }
                        setRecyclerViewFavourite();
                    }
                } else {
                    // Xử lý lỗi khi API request không thành công
                    progressDialog.dismiss();

                    showErrorMessageDialog();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("akjfjkash", "onCreate: " + t);

                progressDialog.dismiss();
                showErrorMessageDialog();
            }
        });
    }


    private void showErrorMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage("Unable to fetch order list. Please try again later.")
                .setPositiveButton("OK", null) // Bạn có thể thêm sự kiện xử lý khi người dùng bấm OK ở đây
                .show();
    }
}

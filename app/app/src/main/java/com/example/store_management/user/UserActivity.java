package com.example.store_management.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.store_management.R;
import com.example.store_management.address.Address;
import com.example.store_management.address.AddressActivity;
import com.example.store_management.address.AddressResponse;
import com.example.store_management.api.ApiManager;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.login.LoginActivity;
import com.example.store_management.order.OrderActivity;
import com.example.store_management.product.ProductActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    private List<Address> addressList;
    private TextView txtAddress;
    private DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        ConstraintLayout navigateAddress = findViewById(R.id.navigateAddress);
        ImageView btnLogout = findViewById(R.id.btnLogout);
        txtAddress = findViewById(R.id.txtAddress);

        // Khởi tạo ProgressDialog và cấu hình
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        fetchApiAddress();

        navigateAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, AddressActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Display a confirmation dialog
                new AlertDialog.Builder(UserActivity.this)
                        .setTitle("Confirm Logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // The user has confirmed the logout, start the logout process
                                performLogout();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });


        bottomNavigationView.setSelectedItemId(R.id.navigation_person);
        // Thiết lập lắng nghe sự kiện khi item được chọn
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    // Chuyển đến ProductActivity khi item "navigation_home" được chọn
                    Intent intent = new Intent(UserActivity.this, ProductActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_favourite) {
                    // Xử lý khi item "navigation_favourite" được chọn
                    return true;
                } else if (item.getItemId() == R.id.navigation_order) {
                    // Xử lý khi item "navigation_order" được chọn
                    Intent intent = new Intent(UserActivity.this, OrderActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_person) {
                    Intent intent = new Intent(UserActivity.this, UserActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });
    }

    private void fetchApiAddress(){
        UserData userData = dataManager.getUserData();
        ApiManager apiManager = new ApiManager(Constants.BASE_URL, userData.getToken());
        // Hiển thị ProgressDialog khi bắt đầu gọi API
        progressDialog.show();

        apiManager.getAddress(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                // Ẩn ProgressDialog khi có kết quả hoặc xảy ra lỗi
                progressDialog.dismiss();
                Log.d("responseresponseresponse", "response: "+response);
                if (response.isSuccessful()) {
                    AddressResponse addressResponse = response.body();
                    if (addressResponse != null) {
                        addressList = addressResponse.getAddressList();
                        txtAddress.setText("0"+String.valueOf(addressList.size())+" Addresses");
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
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                Log.d("responseresponseresponse", "t: "+t);
                // Ẩn ProgressDialog khi có lỗi
                progressDialog.dismiss();
                // Xử lý lỗi khi gọi API thất bại
                showErrorMessage("Network error. Please try again later.");
            }
        });

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

    private void performLogout() {
        // Hiển thị ProgressDialog trong 3 giây
        ProgressDialog progressDialog = new ProgressDialog(UserActivity.this);
        progressDialog.setMessage("Logging out...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Sử dụng Handler để chờ 3 giây trước khi chuyển đến màn hình đăng nhập
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Tắt ProgressDialog
                progressDialog.dismiss();

                // Chuyển đến màn hình đăng nhập
                Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Kết thúc màn hình hiện tại
            }
        }, 3000); // Đợi 3 giây (3000 milliseconds) trước khi thực hiện chuyển đến màn hình đăng nhập
    }

}
package com.example.store_management.order;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_management.favourite.FavouriteActivity;
import com.example.store_management.user.UserData;
import com.example.store_management.product.ProductActivity;
import com.example.store_management.R;
import com.example.store_management.user.UserActivity;
import com.example.store_management.api.ApiManager;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private RecyclerView orderRecyclerView;
    private List<Order> orderList;
    private ProgressDialog progressDialog;
    private DataManager dataManager = DataManager.getInstance();
    private String statusActive = "0";

    private TextView btnDelivered;
    private TextView btnProcessing;
    private TextView btnCanceled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity);

        orderRecyclerView = findViewById(R.id.orderRecyclerView);
        btnDelivered = findViewById(R.id.btnDelivered);
        btnProcessing = findViewById(R.id.btnProcessing);
        btnCanceled = findViewById(R.id.btnCanceled);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        bottomNavigationView.setSelectedItemId(R.id.navigation_order);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navigation_home) {
                    Intent intent = new Intent(OrderActivity.this, ProductActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_favourite) {
                    // Xử lý khi item "navigation_favourite" được chọn
                    Intent intent = new Intent(OrderActivity.this, FavouriteActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_order) {
                    Intent intent = new Intent(OrderActivity.this, OrderActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.navigation_person) {
                    Intent intent = new Intent(OrderActivity.this, UserActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        btnDelivered.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                statusActive = "1";
                orderList = dataManager.getOrderList();
                handleActiveStatus();
                handleFilterStatus();
                setRecyclerViewOrder();
            }
        });

        btnProcessing.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                statusActive = "0";
                orderList = dataManager.getOrderList();
                handleActiveStatus();
                handleFilterStatus();
                setRecyclerViewOrder();
            }
        });

        btnCanceled.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                statusActive = "2";
                orderList = dataManager.getOrderList();
                handleActiveStatus();
                handleFilterStatus();
                setRecyclerViewOrder();
            }
        });

        progressDialog.show();
        fetchDataFromApi();

        orderList = dataManager.getOrderList();
    }

    public void setRecyclerViewOrder() {
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderAdapter orderAdapter = new OrderAdapter(this, orderList);
        orderRecyclerView.setAdapter(orderAdapter);
    }

    public void fetchDataFromApi() {
        UserData userData = dataManager.getUserData();
        ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());
        apiManager.getOrder(new Callback<OrderResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if (response.isSuccessful()) {
                    OrderResponse orderResponse = response.body();
                    if (orderResponse != null) {
                        orderList = orderResponse.getOrderList();
                        progressDialog.dismiss();
                        dataManager.setOrderList(orderList);
                        handleActiveStatus();
                        handleFilterStatus();
                        setRecyclerViewOrder();
                    }
                } else {
                    // Xử lý lỗi khi API request không thành công
                    showErrorMessageDialog();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                progressDialog.dismiss();
                showErrorMessageDialog();
            }
        });
    }

    private void handleFilterStatus() {
        orderList = orderList.stream()
                .filter(order -> statusActive.equals(order.getStatus()))
                .collect(Collectors.toList());
    }

    private void showErrorMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage("Unable to fetch order list. Please try again later.")
                .setPositiveButton("OK", null) // Bạn có thể thêm sự kiện xử lý khi người dùng bấm OK ở đây
                .show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void handleActiveStatus() {
        Typeface customFontNormal = getResources().getFont(R.font.nunito_400);
        Typeface customFontActive = getResources().getFont(R.font.nunito_700);
        List<TextView> statusList = new ArrayList<>();
        statusList.add(btnDelivered);
        statusList.add(btnProcessing);
        statusList.add(btnCanceled);

        for (TextView status : statusList) {
            status.setTextColor(0xFF909090);
            status.setTypeface(customFontNormal);
        }

        switch (statusActive) {
            case "1": {
                btnDelivered.setTextColor(0xFF303030);
                btnDelivered.setTypeface(customFontActive);
                break;
            }
            case "0": {
                btnProcessing.setTextColor(0xFF303030);
                btnProcessing.setTypeface(customFontActive);
                break;
            }
            case "2": {
                btnCanceled.setTextColor(0xFF303030);
                btnCanceled.setTypeface(customFontActive);
                break;
            }
            default: {
                break;
            }
        }
    }
}

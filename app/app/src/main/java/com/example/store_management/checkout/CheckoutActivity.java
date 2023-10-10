package com.example.store_management.checkout;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_management.R;
import com.example.store_management.address.Address;
import com.example.store_management.address.AddressActivity;
import com.example.store_management.address.AddressInsertResponse;
import com.example.store_management.api.ApiManager;
import com.example.store_management.cart.Cart;
import com.example.store_management.cart.CartActivity;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.order.OrderInsertRequest;
import com.example.store_management.order.OrderInsertResponse;
import com.example.store_management.order.OrderSuccessActivity;
import com.example.store_management.user.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private List<Cart> cartList;
    private ProgressDialog progressDialog;
    private DataManager dataManager = DataManager.getInstance();
    private UserData userData = dataManager.getUserData();
    private double deliveryPrice = 5.00;
    private double totalPrice;
    private ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());
    private Address address;
    private TextView txtNameUser;
    private TextView txtAddress;
    private boolean isAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_activity);

        cartRecyclerView = findViewById(R.id.addressRecyclerView);
        TextView txtOrderPrice = findViewById(R.id.txtOderPrice);
        TextView totalPriceTextView = findViewById(R.id.txtTotalPrice);
        ImageView backButton = findViewById(R.id.btnBack);
        txtNameUser = findViewById(R.id.txtNameUser);
        txtAddress = findViewById(R.id.txtAddress);
        ImageView imageView = findViewById(R.id.imageView);
        TextView txtDeliveryPrice = findViewById(R.id.txtDeliveryPrice);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        // Khởi tạo ProgressDialog và cấu hình
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        cartList = dataManager.getCartList() != null ? dataManager.getCartList() : new ArrayList<>();

        setRecyclerViewCart();
        double orderPrice = dataManager.getCartTotalPrice();
        totalPrice = orderPrice + deliveryPrice;
        txtOrderPrice.setText("$ " + String.format("%.2f", orderPrice));
        totalPriceTextView.setText("$ " + String.format("%.2f", totalPrice));
        txtDeliveryPrice.setText("$ " + String.format("%.2f", deliveryPrice));

        progressDialog.show();
        petchApiAddress();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataManager.setCheckout(true);
                Intent intent = new Intent(CheckoutActivity.this, AddressActivity.class);
                startActivity(intent);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Show the confirmation dialog when the "Submit" button is clicked
                if (isAddress) {
                    showConfirmationDialog();
                } else {
                    showNoDefaultAddressDialog();
                }
            }
        });
    }

    private void setRecyclerViewCart() {
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CheckoutAdapter checkoutAdapter = new CheckoutAdapter(this, cartList);
        cartRecyclerView.setAdapter(checkoutAdapter);
    }

    private void showConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Submission")
                .setMessage("Are you sure you want to submit the order?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // The user has confirmed the submission, perform the submission
                        performOrderSubmission();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void performOrderSubmission() {
        progressDialog.show();

        // Lấy ngày tháng năm hiện tại và định dạng
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String orderDate = dateFormat.format(new Date());
        int quantity = dataManager.getCartQuantity();
        String userId = userData.getId();
        String cartIdList = dataManager.getCartId();
        String addressId = "651e3a8ab5b70c89d571c";

        OrderInsertRequest orderInsertRequest = new OrderInsertRequest(orderDate, String.valueOf(quantity), String.valueOf(totalPrice), "0", userId, cartIdList, addressId);

        dataManager.setOrderInsertRequest(orderInsertRequest);

        apiManager.insertOrder(new Callback<OrderInsertResponse>() {
            @Override
            public void onResponse(Call<OrderInsertResponse> call, Response<OrderInsertResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    Intent intent = new Intent(CheckoutActivity.this, OrderSuccessActivity.class);
                    startActivity(intent);
                } else {
                    // Handle the case where order submission fails
                    showSubmissionErrorDialog();
                }
            }

            @Override
            public void onFailure(Call<OrderInsertResponse> call, Throwable t) {
                progressDialog.dismiss();
                // Handle the case where the API call fails
                showSubmissionErrorDialog();
            }
        });
    }

    private void showSubmissionErrorDialog() {
        String errorMessage = "Checkout failed. Please try again later.";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error")
                .setMessage(errorMessage)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle the error dialog's button click if needed
                    }
                })
                .show();
    }

    private void petchApiAddress() {
        apiManager.getAddressById(new Callback<AddressInsertResponse>() {
            @Override
            public void onResponse(Call<AddressInsertResponse> call, Response<AddressInsertResponse> response) {
                progressDialog.dismiss();
                Log.d("jaskdjalskd", "onResponse: " + response);

                if (response.isSuccessful()) {
                    isAddress = true;
                    AddressInsertResponse addressInsertResponse = response.body();
                    address = addressInsertResponse.getAddress();
                    txtNameUser.setText(address.getFullName());
                    txtAddress.setText(address.getAddress());
                } else {
                    // Handle the case where the API call fails
                    isAddress = false;
                }
            }

            @Override
            public void onFailure(Call<AddressInsertResponse> call, Throwable t) {
                progressDialog.dismiss();
                // Handle the case where the API call fails
                showSubmissionErrorDialog();
            }
        });
    }

    private void showNoDefaultAddressDialog() {
        String message = "You don't have a default address yet. Please add default address.";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Người dùng đã xem cảnh báo
                        dataManager.setCheckout(true);
                        Intent intent = new Intent(CheckoutActivity.this, AddressActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Người dùng hủy bỏ cảnh báo
                    }
                })
                .show();
    }

}

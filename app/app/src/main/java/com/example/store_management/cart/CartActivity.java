package com.example.store_management.cart;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_management.user.UserData;
import com.example.store_management.checkout.CheckoutActivity;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.product.ProductActivity;
import com.example.store_management.R;
import com.example.store_management.api.ApiManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {
    private RecyclerView cartRecyclerView;
    private List<Cart> cartList;
    private ProgressDialog progressDialog;
    private DataManager dataManager = DataManager.getInstance();
    private UserData userData = dataManager.getUserData();
    private ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        ImageView backButton = findViewById(R.id.btnBack);
        TextView totalPriceTextView = findViewById(R.id.totalTextView);
        Button checkoutButton = findViewById(R.id.btnSubmit);

        // Khởi tạo ProgressDialog và cấu hình
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        cartList = dataManager.getCartList() != null ? dataManager.getCartList() : new ArrayList<>();
        progressDialog.show();
        fetchCartDataFromApi();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProductActivity();
            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToCheckoutActivity();
            }
        });

        totalPriceTextView.setText("$ 0.0"); // Khởi tạo tổng giá tiền ban đầu
    }

    private void setRecyclerViewCart() {
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        CartAdapter cartAdapter = new CartAdapter(this, cartList);
        cartRecyclerView.setAdapter(cartAdapter);

        // Cập nhật tổng giá tiền
        double totalPrice = cartAdapter.calculateTotalPrice();
        TextView totalPriceTextView = findViewById(R.id.totalTextView);
        totalPriceTextView.setText("$ " + String.format("%.2f", totalPrice));

        int checkoutQuantity = cartAdapter.getTotalCartQuantity();
        String cartListId = cartAdapter.getCartListIds();
        dataManager.setCartQuantity(checkoutQuantity);
        dataManager.setCartTotalPrice(totalPrice);
        dataManager.setCartId(cartListId);
    }

    private void fetchCartDataFromApi() {
        apiManager.getCart(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                // Ẩn ProgressDialog khi có kết quả hoặc xảy ra lỗi
                if (response.isSuccessful()) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse != null) {
                        cartList = cartResponse.getCartList();
                        progressDialog.dismiss();

                        if (cartList.size() == 0) {
                            navigateToCartEmptyActivity();
                        } else {
                            // Lưu vào DataManager
                            dataManager.setCartList(cartList);
                            setRecyclerViewCart();
                        }
                    }
                } else {
                    // Xử lý lỗi khi gọi API không thành công
                    showAlertAndNavigateBack();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                // Ẩn ProgressDialog khi có lỗi
                progressDialog.dismiss();
                showAlertAndNavigateBack();
            }
        });
    }

    private void navigateToProductActivity() {
        Intent intent = new Intent(CartActivity.this, ProductActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToCartEmptyActivity() {
        Intent intent = new Intent(CartActivity.this, CartEmptyActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToCheckoutActivity() {
        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
        startActivity(intent);
        finish();
    }

    private void showAlertAndNavigateBack() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage("An error occurred while loading the cart.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Chuyển lại màn hình hoạt động trước đó
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}

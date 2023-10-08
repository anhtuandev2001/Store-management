package com.example.store_management.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.store_management.R;
import com.example.store_management.user.UserData;
import com.example.store_management.cart.CartActivity;
import com.example.store_management.cart.CartInsertRequest;
import com.example.store_management.cart.CartInsertResponse;
import com.example.store_management.api.ApiManager;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends Activity {
    private String currentProductId;
    private String selectedColor;
    private Button addToCartButton;
    private Button colorButton;
    private DataManager dataManager = DataManager.getInstance();
    private List<Product> productList;
    private Product product;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        // Ánh xạ các thành phần trong layout
        ImageView productImageView = findViewById(R.id.productImage);
        TextView productNameTextView = findViewById(R.id.productName);
        TextView productPriceTextView = findViewById(R.id.productPrice);
        TextView productDescription = findViewById(R.id.productDescription);
        TextView productQuantity = findViewById(R.id.productQuantity);
        ImageView backButton = findViewById(R.id.btnBack);
        ImageView minusButton = findViewById(R.id.minusButton);
        ImageView plusButton = findViewById(R.id.plusButton);
        colorButton = findViewById(R.id.colorButton);
        addToCartButton = findViewById(R.id.addToCartButton);

        // Khởi tạo ProgressDialog và cấu hình
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        // Lấy ID của sản phẩm từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("productId")) {
            currentProductId = intent.getStringExtra("productId");
            productList = dataManager.getProductList();
            if (productList != null) {
                // Tìm sản phẩm với productId tương ứng
                for (Product p : productList) {
                    if (p.getId().equals(currentProductId)) {
                        product = p;
                        break;
                    }
                }
            }

            if (product != null) {
                // Hiển thị thông tin sản phẩm trên màn hình chi tiết
                Glide.with(this)
                        .load(product.getImagesList())
                        .transform(new RoundedCorners(20))
                        .into(productImageView);

                productNameTextView.setText(product.getName());
                productDescription.setText(product.getDescription());
                productPriceTextView.setText("$ " + String.valueOf(product.getPrice()));
            }
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, ProductActivity.class);
                startActivity(intent);
                finish();
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseQuantity();
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseQuantity();
            }
        });

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showColorSelectionDialog();
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedColor == null) {
                    showColorNotSelectedAlert();
                } else {
                    progressDialog.show();
                    String productId = currentProductId;
                    int quantity = Integer.parseInt(productQuantity.getText().toString());
                    String color = selectedColor;
                    UserData userData = dataManager.getUserData();

                    CartInsertRequest cartInsertRequest = new CartInsertRequest(productId, userData.getId(), String.valueOf(quantity), color);
                    dataManager.setCartInsertRequest(cartInsertRequest);
                    ApiManager apiManager = new ApiManager(Constants.BASE_URL, userData.getToken());

                    apiManager.insertCart(new Callback<CartInsertResponse>() {
                        @Override
                        public void onResponse(Call<CartInsertResponse> call, Response<CartInsertResponse> response) {
                            showSuccessDialog();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<CartInsertResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            showErrorMessage("Network error. Please try again later.");
                        }
                    });
                }
            }
        });
    }

    private void decreaseQuantity() {
        TextView productQuantity = findViewById(R.id.productQuantity);
        int currentQuantity = Integer.parseInt(productQuantity.getText().toString());
        if (currentQuantity > 1) {
            currentQuantity--;
            productQuantity.setText(String.valueOf(currentQuantity));
        }
    }

    private void increaseQuantity() {
        TextView productQuantity = findViewById(R.id.productQuantity);
        int currentQuantity = Integer.parseInt(productQuantity.getText().toString());
        currentQuantity++;
        productQuantity.setText(String.valueOf(currentQuantity));
    }

    private void showColorSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Color");

        String colorList = product.getColorList();
        String[] colors = colorList.split(",");

        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedColor = colors[which];
                colorButton.setText(colors[which]);
                checkAddToCartButtonEnabled();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showColorNotSelectedAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        builder.setMessage("Please select a color before adding to cart.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkAddToCartButtonEnabled() {
        if (selectedColor != null) {
            addToCartButton.setEnabled(true);
        } else {
            addToCartButton.setEnabled(false);
        }
    }

    private void showSuccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Success");
        builder.setMessage("Product added to cart successfully!");

        builder.setPositiveButton("Go to Cart", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Chuyển đến CartActivity khi người dùng nhấn nút "Go to Cart"
                Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showErrorMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error");
        builder.setMessage(message);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

package com.example.store_management.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.store_management.R;
import com.example.store_management.api.ApiManager;
import com.example.store_management.cart.CartActivity;
import com.example.store_management.cart.CartInsertRequest;
import com.example.store_management.cart.CartInsertResponse;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.user.UserData;
import com.example.store_management.user.UserFavoriteRequest;
import com.example.store_management.user.UserResponse;

import java.util.ArrayList;
import java.util.Arrays;
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
    private boolean isFavorite = false;
    private ImageView btnFavorite;

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
        btnFavorite = findViewById(R.id.favouriteButton);
        colorButton = findViewById(R.id.colorButton);
        addToCartButton = findViewById(R.id.addToCartButton);

        // Khởi tạo ProgressDialog và cấu hình
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        progressDialog.show();

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
                Glide.with(this).load(product.getImagesList()).transform(new RoundedCorners(20)).into(productImageView);

                productNameTextView.setText(product.getName());
                productDescription.setText(product.getDescription());
                productPriceTextView.setText("$ " + String.valueOf(product.getPrice()));
            }
        }

        getUserFromApi();
        UserData userData = dataManager.getUserData();
        String favoriteList = userData.getFavoritesList();

        if (favoriteList != null) {
            String[] favoriteListArray = favoriteList.split(",");
            List<String> favoriteListItems = new ArrayList<>(Arrays.asList(favoriteListArray));

            // Kiểm tra xem sản phẩm có trong danh sách yêu thích không
            if (favoriteListItems.contains(product.getId())) {
                // Nếu sản phẩm có trong danh sách yêu thích, loại bỏ nó
                isFavorite = true;
            }
        }
        setActiveFavorite();

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

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = dataManager.getUserData();

                String userId = userData.getId();
                String favoriteList = userData.getFavoritesList();
                String newFavoriteList = "";

                if (favoriteList == null) {
                    // Nếu danh sách yêu thích trống, thêm sản phẩm vào danh sách yêu thích mới
                    newFavoriteList = product.get_id();
                    isFavorite = true;
                    setActiveFavorite();
                } else {
                    String[] favoriteListArray = favoriteList.split(",");
                    List<String> favoriteListItems = new ArrayList<>(Arrays.asList(favoriteListArray));

                    // Kiểm tra xem sản phẩm có trong danh sách yêu thích không
                    if (favoriteListItems.contains(product.getId())) {
                        // Nếu sản phẩm có trong danh sách yêu thích, loại bỏ nó
                        favoriteListItems.remove(product.getId());
                        isFavorite = false;
                        setActiveFavorite();
                        Toast.makeText(ProductDetailActivity.this, "Remove product to favorite successfully", Toast.LENGTH_SHORT).show();
                        // Chuyển danh sách đã chỉnh sửa thành một chuỗi mới
                        newFavoriteList = TextUtils.join(",", favoriteListItems);
                    } else {
                        // Nếu sản phẩm không có trong danh sách yêu thích, thêm nó vào danh sách yêu thích
                        newFavoriteList = favoriteList + "," + product.getId();
                        isFavorite = true;
                        setActiveFavorite();
                        Toast.makeText(ProductDetailActivity.this, "Add product to favorite successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                UserFavoriteRequest userFavoriteRequest = new UserFavoriteRequest(userId, newFavoriteList);
                dataManager.setUserFavoriteRequest(userFavoriteRequest);
                Log.d("klajflkasjd", "onClick: " + userFavoriteRequest);

                fetchDataFromApi();
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
                    ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());

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

    public void fetchDataFromApi() {
        ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());
        apiManager.updateFavoriteUser(new Callback<UserResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("klajflkasjd", "onResponse: " + response);

                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        UserData userData = userResponse.getUserData();
                        dataManager.setUserData(userData);
                    }
                } else {
                    // Xử lý lỗi khi API request không thành công
                    Log.d("klajflkasjd", "onResponse: " + response);
                    showErrorMessageDialog();
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("klajflkasjd", "onFailure: " + t);
                showErrorMessageDialog();
            }
        });
    }

    private void showErrorMessageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Error").setMessage("Unable to fetch favorite list. Please try again later.").setPositiveButton("OK", null) // Bạn có thể thêm sự kiện xử lý khi người dùng bấm OK ở đây
                .show();
    }

    public void getUserFromApi() {
        ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());
        apiManager.getUserById(new Callback<UserResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        UserData userData = userResponse.getUserData();
                        progressDialog.dismiss();
                        dataManager.setUserData(userData);
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

    private void setActiveFavorite() {
        if (isFavorite) {
            btnFavorite.setImageResource(R.drawable.favourite_checked);
        } else {
            btnFavorite.setImageResource(R.drawable.favourite_detail);
        }
    }
}

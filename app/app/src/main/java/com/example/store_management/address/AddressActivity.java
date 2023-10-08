package com.example.store_management.address;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.store_management.R;
import com.example.store_management.api.ApiManager;
import com.example.store_management.checkout.CheckoutActivity;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.product.ProductResponse;
import com.example.store_management.user.UserActivity;
import com.example.store_management.user.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    private List<Address> addressList;
    RecyclerView addressRecyclerView;
    private ProgressDialog progressDialog;
    private DataManager dataManager = DataManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_activity);

        ImageView btnBack = findViewById(R.id.btnBack);
        addressRecyclerView = findViewById(R.id.addressRecyclerView);
        ImageView btnAdd = findViewById(R.id.btnAdd);

        // Khởi tạo ProgressDialog và cấu hình
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = dataManager.isCheckout();
                if(!isChecked){
                    Intent intent = new Intent(AddressActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    dataManager.setCheckout(false);
                    Intent intent = new Intent(AddressActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        fetchApi();
    }

    private void setRecycleViewAddress() {
        AddressAdapter addressAdapter = new AddressAdapter(AddressActivity.this, addressList);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressRecyclerView.setAdapter(addressAdapter);
    }

    private void fetchApi(){
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
                        // Lưu vào DataManager
                        setRecycleViewAddress();
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
}
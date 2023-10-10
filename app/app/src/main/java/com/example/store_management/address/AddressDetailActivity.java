package com.example.store_management.address;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.store_management.R;
import com.example.store_management.api.ApiManager;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.user.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressDetailActivity extends AppCompatActivity {

    private DataManager dataManager = DataManager.getInstance();
    private ProgressDialog progressDialog;

    private EditText editTextFullName;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private Button btnSubmit;
    private ImageView btnBack;
    private String addressId;
    private Address address = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_detail_activity);

        // Khởi tạo ProgressDialog và cấu hình
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        editTextFullName = findViewById(R.id.txtEmail);
        editTextPhone = findViewById(R.id.txtPhone);
        editTextAddress = findViewById(R.id.txtAddress);
        btnSubmit = findViewById(R.id.btnSave);
        btnBack = findViewById(R.id.btnBack2);


        // Lấy ID của sản phẩm từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("addressId")) {
            addressId = intent.getStringExtra("addressId");
            Log.d("ajlskdfjla", "onCreate: " + addressId);
            List<Address> addRessList = dataManager.getAddressList();
            Log.d("ajlskdfjla", "list: " + addRessList);

            if (addRessList != null) {
                // Tìm sản phẩm với addressId tương ứng
                for (Address p : addRessList) {
                    if (p.getId().equals(addressId)) {
                        address = p;
                        break;
                    }
                }
            }
            Log.d("ajlskdfjla", "onCreate: " + address);

            if (address != null) {
                // Hiển thị thông tin sản phẩm trên màn hình chi tiết
                editTextFullName.setText(address.getFullName());
                editTextAddress.setText(address.getAddress());
                editTextPhone.setText(address.getPhoneNumber());
            }
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ấn vào btnEdit, hiển thị modal chỉnh sửa với dữ liệu đầy đủ
                progressDialog.show();
                if (intent != null && intent.hasExtra("addressId")) {
                    submitAddress(address);
                } else {
                    insertAddress(address);
                }

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ấn vào btnEdit, hiển thị modal chỉnh sửa với dữ liệu đầy đủ
                Intent intent = new Intent(AddressDetailActivity.this, AddressActivity.class);
                startActivity(intent);
            }
        });

    }

    private void submitAddress(Address address) {
        String fullName = editTextFullName.getText().toString();
        String addressText = editTextAddress.getText().toString();
        String phoneNumber = editTextPhone.getText().toString();

        // Kiểm tra xem fullName và address có dữ liệu không
        if (!fullName.isEmpty() && !addressText.isEmpty() && !phoneNumber.isEmpty()) {
            // Xử lý chỉnh sửa dữ liệu ở đây (có thể gọi API để lưu dữ liệu)
            AddressUpdateRequest addressUpdateRequest = new AddressUpdateRequest(fullName, address.getId(), addressText, phoneNumber);
            dataManager.setAddressUpdateRequest(addressUpdateRequest);
            ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());
            // Hiển thị ProgressDialog khi bắt đầu gọi API
            progressDialog.show();
            Log.d("asdfasdf", "onClick: " + addressUpdateRequest);
            apiManager.updateAddress(new Callback<AddressInsertResponse>() {
                @Override
                public void onResponse(Call<AddressInsertResponse> call, Response<AddressInsertResponse> response) {
                    Log.d("asdfasdf", "onClick: " + response);

                    // Ẩn ProgressDialog khi có kết quả hoặc xảy ra lỗi
                    progressDialog.dismiss();
                    Log.d("responseresponseresponse", "response: " + response);
                    if (response.isSuccessful()) {
                        Toast.makeText(AddressDetailActivity.this, "Update addree successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddressDetailActivity.this, AddressActivity.class);
                        startActivity(intent);
                    } else {
                        // Xử lý lỗi khi gọi API không thành công
                        showErrorMessage("Failed to fetch product data");
                    }
                }

                @Override
                public void onFailure(Call<AddressInsertResponse> call, Throwable t) {
                    Log.d("responseresponseresponse", "t: " + t);
                    // Ẩn ProgressDialog khi có lỗi
                    progressDialog.dismiss();
                    // Xử lý lỗi khi gọi API thất bại
                    showErrorMessage("Network error. Please try again later.");
                }
                // Đóng dialog
            });
        } else {
            // Hiển thị thông báo lỗi nếu fullName hoặc address trống
            showErrorMessage("Full Name/ Address/ Phone Number are required.");
        }
    }

    private void insertAddress(Address address) {
        String fullName = editTextFullName.getText().toString();
        String addressText = editTextAddress.getText().toString();
        String phoneNumber = editTextPhone.getText().toString();

        // Kiểm tra xem fullName và address có dữ liệu không
        if (!fullName.isEmpty() && !addressText.isEmpty() && !phoneNumber.isEmpty()) {
            // Xử lý chỉnh sửa dữ liệu ở đây (có thể gọi API để lưu dữ liệu)
            UserData userData = dataManager.getUserData();
            AddressInsertRequest addressInsertRequest = new AddressInsertRequest(fullName, userData.getId(), addressText, phoneNumber);
            dataManager.setAddressInsertRequest(addressInsertRequest);
            ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());
            // Hiển thị ProgressDialog khi bắt đầu gọi API
            progressDialog.show();
            Log.d("asdfasdf", "onClick: " + addressInsertRequest);
            apiManager.insertAddress(new Callback<AddressInsertResponse>() {
                @Override
                public void onResponse(Call<AddressInsertResponse> call, Response<AddressInsertResponse> response) {
                    Log.d("asdfasdf", "onClick: " + response);

                    // Ẩn ProgressDialog khi có kết quả hoặc xảy ra lỗi
                    progressDialog.dismiss();
                    Log.d("responseresponseresponse", "response: " + response);
                    if (response.isSuccessful()) {
                        Toast.makeText(AddressDetailActivity.this, "Insert addree successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddressDetailActivity.this, AddressActivity.class);
                        startActivity(intent);
                    } else {
                        // Xử lý lỗi khi gọi API không thành công
                        showErrorMessage("Failed to fetch product data");
                    }
                }

                @Override
                public void onFailure(Call<AddressInsertResponse> call, Throwable t) {
                    Log.d("responseresponseresponse", "t: " + t);
                    // Ẩn ProgressDialog khi có lỗi
                    progressDialog.dismiss();
                    // Xử lý lỗi khi gọi API thất bại
                    showErrorMessage("Network error. Please try again later.");
                }
                // Đóng dialog
            });
        } else {
            // Hiển thị thông báo lỗi nếu fullName hoặc address trống
            showErrorMessage("Full Name/ Address/ Phone Number are required.");
        }
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
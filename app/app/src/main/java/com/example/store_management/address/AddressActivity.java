package com.example.store_management.address;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_management.R;
import com.example.store_management.api.ApiManager;
import com.example.store_management.checkout.CheckoutActivity;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
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
    private AddressAdapter adapter;
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.address_activity);

        ImageView btnBack = findViewById(R.id.btnBack);
        addressRecyclerView = findViewById(R.id.addressRecyclerView);
        ImageView btnAdd = findViewById(R.id.btnAdd);
        CardView btnAddContainer = findViewById(R.id.btnAddContainer);

        LayoutInflater inflater = getLayoutInflater();
        adapter = new AddressAdapter(this, addressList, inflater);

        // Đăng ký BroadcastReceiver để lắng nghe sự kiện cập nhật địa chỉ
        IntentFilter intentFilter = new IntentFilter("ADDRESS_UPDATED");
        BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Xử lý khi nhận được broadcast
                // Reload dữ liệu trong RecyclerView
                fetchApi();
            }
        };
        registerReceiver(receiver, intentFilter);


        // Khởi tạo ProgressDialog và cấu hình
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        btnAddContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog khi nhấn vào btnAddContainer
                showAddAddressDialog();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = dataManager.isCheckout();
                Log.d("asjlfkajlsf", "onClick: "+isChecked);
                if (!isChecked) {
                    Intent intent = new Intent(AddressActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    dataManager.setCheckout(false);
                    Intent intent = new Intent(AddressActivity.this, CheckoutActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        fetchApi();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy đăng ký BroadcastReceiver khi Activity bị hủy
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    private void setRecycleViewAddress() {
        LayoutInflater inflater = getLayoutInflater();
        adapter = new AddressAdapter(AddressActivity.this, addressList, inflater);
        addressRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressRecyclerView.setAdapter(adapter);
    }

    private void fetchApi() {
        UserData userData = dataManager.getUserData();
        ApiManager apiManager = new ApiManager(Constants.BASE_URL, userData.getToken());
        // Hiển thị ProgressDialog khi bắt đầu gọi API
        progressDialog.show();

        apiManager.getAddress(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                // Ẩn ProgressDialog khi có kết quả hoặc xảy ra lỗi
                progressDialog.dismiss();
                Log.d("responseresponseresponse", "response: " + response);
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
                Log.d("responseresponseresponse", "t: " + t);
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

    private void showAddAddressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_address, null);
        builder.setView(dialogView);

        EditText editTextFullName = dialogView.findViewById(R.id.editTextFullName);
        EditText editTextAddress = dialogView.findViewById(R.id.editTextAddress);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmit);

        final Dialog dialog = builder.create();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = editTextFullName.getText().toString();
                String address = editTextAddress.getText().toString();

                // Kiểm tra xem fullName và address có dữ liệu không
                if (!fullName.isEmpty() && !address.isEmpty()) {
                    // Log giá trị
                    Log.d("AddressActivity", "Full Name: " + fullName);
                    Log.d("AddressActivity", "Address: " + address);

                    // Đóng dialog
                    UserData userData = dataManager.getUserData();
                    AddressInsertRequest addressInsertRequest = new AddressInsertRequest(fullName, userData.getId(), address);
                    dataManager.setAddressInsertRequest(addressInsertRequest);
                    ApiManager apiManager = new ApiManager(Constants.BASE_URL, userData.getToken());
                    // Hiển thị ProgressDialog khi bắt đầu gọi API
                    progressDialog.show();

                    apiManager.insertAddress(new Callback<AddressInsertResponse>() {
                        @Override
                        public void onResponse(Call<AddressInsertResponse> call, Response<AddressInsertResponse> response) {
                            // Ẩn ProgressDialog khi có kết quả hoặc xảy ra lỗi
                            progressDialog.dismiss();
                            Log.d("responseresponseresponse", "response: " + response);
                            if (response.isSuccessful()) {
                                // Hiển thị thông báo "Add address successfully"
                                showSuccessMessage("Add address successfully");
                                // Đóng dialog
                                dialog.dismiss();
                                // Làm mới hoạt động
                                recreate();
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
                    });
                } else {
                    // Hiển thị thông báo lỗi nếu fullName hoặc address trống
                    showErrorMessage("Full Name and Address are required.");
                }
            }
        });

        editTextFullName.requestFocus();

        // Hiển thị bàn phím ảo
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editTextFullName, InputMethodManager.SHOW_IMPLICIT);

        dialog.show();
    }

    private void showSuccessMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle("Success")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xử lý khi người dùng nhấn nút OK
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
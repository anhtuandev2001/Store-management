package com.example.store_management.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.store_management.R;
import com.example.store_management.api.ApiService;
import com.example.store_management.common.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText txtName;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private TextView txtSignIn;
    private ProgressDialog progressDialog;

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]{2,}";
        return email.matches(emailPattern);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        // Ánh xạ các phần tử giao diện người dùng
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        txtSignIn = findViewById(R.id.txtSignIn);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        // Thêm xử lý khi nhấn nút đăng ký
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi txtSignIn được nhấn, chuyển đến LoginActivity
                navigateLogin();
            }
        });

        // Thêm xử lý khi nhấn nút đăng ký
        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy dữ liệu từ các EditText
                String name = txtName.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String confirmPassword = txtConfirmPassword.getText().toString().trim();

                // Kiểm tra xem tên có rỗng không
                if (name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem email có rỗng không
                if (email.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem email có đúng định dạng không
                if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Email is error", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem email có đúng định dạng không
                if (password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please enter your password or confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem mật khẩu và xác nhận mật khẩu có khớp nhau không
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show();
                registerUser(name, email, password);
            }
        });

    }

    private void registerUser(String name, String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        RegisterRequest registerRequest = new RegisterRequest(name, email, password);

        Call<RegisterResponse> call = apiService.registerUser(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                progressDialog.dismiss(); // Ẩn ProgressDialog sau khi có kết quả
                Log.d("adsfasd", "onResponse: "+response);
                Log.d("adsfasd", "onResponse: "+registerRequest);
                if (response.isSuccessful()) {
                    RegisterResponse registerResponse = response.body();
                    if (registerResponse != null && registerResponse.getMessage().equals("Register user successfully")) {
                        Toast.makeText(getApplicationContext(), "Register successful", Toast.LENGTH_LONG).show();
                        navigateLogin();
                    } else {
                        Toast.makeText(getApplicationContext(), "Register failed", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "API request failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                progressDialog.dismiss(); // Ẩn ProgressDialog sau khi có kết quả
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateLogin (){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}

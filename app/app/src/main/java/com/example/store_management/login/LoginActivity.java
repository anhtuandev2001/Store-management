package com.example.store_management.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.store_management.product.ProductActivity;
import com.example.store_management.R;
import com.example.store_management.api.ApiService;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.user.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends Activity {
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnLogin;
    private TextView btnRegister;

    private ProgressDialog progressDialog;

    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-z]{2,}";
        return email.matches(emailPattern);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmail.getText().toString();
                String password = txtPassword.getText().toString();

                // Kiểm tra xem email có rỗng không
                if (email.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem email có đúng định dạng không
                if (!isValidEmail(email)) {
                    Toast.makeText(LoginActivity.this, "Email is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra xem email có đúng định dạng không
                if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter your password or confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.show(); // Hiển thị ProgressDialog trước khi gửi request
                loginUser(email, password);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi nút btnRegister được nhấn, chuyển đến RegisterActivity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        LoginRequest loginRequest = new LoginRequest(email, password);

        Call<LoginResponse> call = apiService.loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss(); // Ẩn ProgressDialog sau khi có kết quả
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    UserData userData = loginResponse != null ? loginResponse.getUserData() : null;

                    DataManager dataManager = DataManager.getInstance();
                    dataManager.setUserData(userData);
                    dataManager.setToken(userData.getToken());

                    if (loginResponse != null && loginResponse.getMessage().equals("Login user successfully")) {
                        Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this, ProductActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Login failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss(); // Ẩn ProgressDialog sau khi có kết quả
                Toast.makeText(getApplicationContext(), "Network error", Toast.LENGTH_LONG).show();
            }
        });
    }
}

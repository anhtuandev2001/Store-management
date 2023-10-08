package com.example.store_management.order;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.store_management.product.ProductActivity;
import com.example.store_management.R;

public class OrderSuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_success_activity);

        Button btnViewOrder = findViewById(R.id.btnViewOrder);
        Button btnBackHome = findViewById(R.id.btnBackHome);

        btnViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi nút btnRegister được nhấn, chuyển đến RegisterActivity
                Intent intent = new Intent(OrderSuccessActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khi nút btnRegister được nhấn, chuyển đến RegisterActivity
                Intent intent = new Intent(OrderSuccessActivity.this, ProductActivity.class);
                startActivity(intent);
            }
        });
    }
}
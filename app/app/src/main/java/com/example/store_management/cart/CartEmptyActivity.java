package com.example.store_management.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.store_management.product.ProductActivity;
import com.example.store_management.R;

public class CartEmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_empty_activity);

        Button btnBackHome = findViewById(R.id.btnBackHome);

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartEmptyActivity.this, ProductActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
package com.example.store_management.checkout;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.store_management.common.DataManager;
import com.example.store_management.product.Product;
import com.example.store_management.R;
import com.example.store_management.cart.Cart;

import java.util.List;

public class CheckoutAdapter extends RecyclerView.Adapter<CheckoutAdapter.CartViewHolder> {
    private Context mContext;
    private List<Cart> mCart;
    private DataManager dataManager = DataManager.getInstance();
    private List<Product> productList = dataManager.getProductList();
    private ProgressDialog progressDialog;


    public CheckoutAdapter(Context context, List<Cart> carts) {
        mContext = context;
        mCart = carts;

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.checkout_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = mCart.get(holder.getAdapterPosition()); // Retrieve position using getAdapterPosition()
        String productId = cart.getProductId();
        int quantity = cart.getQuantity();
        Product product = null;
        product = getProductById(productId);

        if (product != null) {
            // Đặt thông tin sản phẩm lên các thành phần trong CartViewHolder
            holder.nameTextView.setText(product.getName());
            holder.priceTextView.setText("$ " + String.format("%.2f", product.getPrice()));
            holder.colorTextView.setText(cart.getColor());
            holder.quantityTextView.setText("x "+String.valueOf(quantity));

            // Sử dụng Glide để hiển thị hình ảnh sản phẩm
            Glide.with(mContext).load(product.getImagesList()).placeholder(R.drawable.placeholder_product).error(R.drawable.placeholder_product_error).override(200, 200).fitCenter().into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mCart.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        TextView quantityTextView;
        TextView colorTextView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameTextView = itemView.findViewById(R.id.name);
            priceTextView = itemView.findViewById(R.id.price);
            quantityTextView = itemView.findViewById(R.id.quantity);
            colorTextView = itemView.findViewById(R.id.color);
        }
    }

    public Product getProductById(String productId) {
        if (productList != null) {
            for (Product product : productList) {
                if (product.getId().equals(productId)) {
                    return product;
                }
            }
        }
        return null;
    }
}

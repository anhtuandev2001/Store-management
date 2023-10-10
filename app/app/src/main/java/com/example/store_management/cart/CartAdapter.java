package com.example.store_management.cart;

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
import com.example.store_management.user.UserData;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.product.Product;
import com.example.store_management.R;
import com.example.store_management.api.ApiManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context mContext;
    private List<Cart> mCartList;
    private DataManager dataManager = DataManager.getInstance();
    private List<Product> mProductList = dataManager.getProductList();
    private ProgressDialog progressDialog;

    public CartAdapter(Context context, List<Cart> cartList) {
        mContext = context;
        mCartList = cartList;

        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = mCartList.get(position);
        String productId = cart.getProductId();
        int quantity = cart.getQuantity();
        Product product = getProductById(productId);

        if (product != null) {
            holder.bindProductData(product, cart, quantity);
            holder.bindListeners(product, cart);
        }
    }

    @Override
    public int getItemCount() {
        return mCartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        TextView quantityTextView;
        TextView colorTextView;
        ImageView minusButton;
        ImageView plusButton;
        ImageView close;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameTextView = itemView.findViewById(R.id.name);
            priceTextView = itemView.findViewById(R.id.price);
            quantityTextView = itemView.findViewById(R.id.quantity);
            colorTextView = itemView.findViewById(R.id.color);
            minusButton = itemView.findViewById(R.id.minusButton);
            plusButton = itemView.findViewById(R.id.plusButton);
            close = itemView.findViewById(R.id.close);
        }

        public void bindProductData(Product product, Cart cart, int quantity) {
            nameTextView.setText(product.getName());
            priceTextView.setText("$ " + String.format("%.2f", product.getPrice()));
            colorTextView.setText(cart.getColor());
            quantityTextView.setText(String.valueOf(quantity));

            Glide.with(mContext)
                    .load(product.getImagesList())
                    .placeholder(R.drawable.placeholder_product)
                    .error(R.drawable.placeholder_product_error)
                    .override(200, 200)
                    .fitCenter()
                    .into(imageView);
        }

        public void bindListeners(Product product, Cart cart) {
            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    decreaseQuantity(cart);
                }
            });

            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    increaseQuantity(cart);
                }
            });

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteCart(cart);
                }
            });
        }

        private void decreaseQuantity(Cart cart) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                int quantity = cart.getQuantity();
                if (quantity > 1) {
                    quantity--;
                    updateCartQuantity(adapterPosition, quantity);
                    updateTotalPrice();
                    updateCartOnServer(cart, quantity);
                }
            }
        }

        private void increaseQuantity(Cart cart) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                int quantity = cart.getQuantity() + 1;
                updateCartQuantity(adapterPosition, quantity);
                updateTotalPrice();
                updateCartOnServer(cart, quantity);
            }
        }

        private void updateCartQuantity(int adapterPosition, int quantity) {
            quantityTextView.setText(String.valueOf(quantity));
            mCartList.get(adapterPosition).setQuantity(quantity);
        }

        private void updateTotalPrice() {
            double totalPrice = calculateTotalPrice();
            TextView totalPriceTextView = ((CartActivity) mContext).findViewById(R.id.totalTextView);
            totalPriceTextView.setText("$ " + String.format("%.2f", totalPrice));
        }

        private void updateCartOnServer(Cart cart, int quantity) {
            String cartId = cart.getCartId();
            CartRequest cartRequest = new CartRequest(cartId, String.valueOf(quantity));
            dataManager.setCartRequest(cartRequest);
            UserData userData = dataManager.getUserData();
            ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());

            apiManager.updateCart(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                }

                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {
                }
            });
        }

        private void deleteCart(Cart cart) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition != RecyclerView.NO_POSITION) {
                progressDialog.show();
                String cartId = cart.getCartId();
                dataManager.setCartId(cartId);
                UserData userData = dataManager.getUserData();
                ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());

                apiManager.deleteCart(new Callback<CartInsertResponse>() {
                    @Override
                    public void onResponse(Call<CartInsertResponse> call, Response<CartInsertResponse> response) {
                        removeCart(adapterPosition);
                        updateTotalPrice();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<CartInsertResponse> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }

    public double calculateTotalPrice() {
        double total = 0.0;
        for (Cart cart : mCartList) {
            Product product = getProductById(cart.getProductId());

            if (product != null) {
                total += product.getPrice() * cart.getQuantity();
            }
        }
        return total;
    }

    public int getTotalCartQuantity() {
        int quantity = 0;
        for (Cart cart : mCartList) {
            quantity += cart.getQuantity();
        }
        return quantity;
    }

    public String getCartListIds() {
        StringBuilder cartListIds = new StringBuilder();
        boolean firstElement = true;
        for (Cart cart : mCartList) {
            if (firstElement) {
                cartListIds.append(cart.getCartId());
                firstElement = false;
            } else {
                cartListIds.append(",").append(cart.getCartId());
            }
        }
        return cartListIds.toString();
    }

    public Product getProductById(String productId) {
        if (mProductList != null) {
            for (Product product : mProductList) {
                if (product.getId().equals(productId)) {
                    return product;
                }
            }
        }
        return null;
    }

    public void removeCart(int position) {
        if (position >= 0 && position < mCartList.size()) {
            mCartList.remove(position);
            notifyItemRemoved(position);
        }
    }
}

package com.example.store_management.favourite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.store_management.R;
import com.example.store_management.api.ApiManager;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.product.Product;
import com.example.store_management.product.ProductDetailActivity;
import com.example.store_management.user.UserData;
import com.example.store_management.user.UserFavoriteRequest;
import com.example.store_management.user.UserResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ProductViewHolder> {
    private Context mContext;
    private List<Product> mProducts;
    private DataManager dataManager = DataManager.getInstance();

    public FavouriteAdapter(Context context, List<Product> products) {
        mContext = context;
        mProducts = products;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.favourite_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = mProducts.get(position);

        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText("$ " + String.valueOf(product.getPrice()));
        Glide.with(mContext)
                .load(product.getImagesList())
                .placeholder(R.drawable.placeholder_product)
                .error(R.drawable.product)
                .fitCenter()
                .into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("productId", product.getId());
                mContext.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userData = dataManager.getUserData();

                String userId = userData.getId();
                String favoriteList = userData.getFavoritesList();
                String newFavoriteList = "";

                String[] favoriteListArray = favoriteList.split(",");
                List<String> favoriteListItems = new ArrayList<>(Arrays.asList(favoriteListArray));

                // Kiểm tra xem sản phẩm có trong danh sách yêu thích không
                if (favoriteListItems.contains(product.getId())) {
                    // Nếu sản phẩm có trong danh sách yêu thích, loại bỏ nó
                    favoriteListItems.remove(product.getId());
                    // Chuyển danh sách đã chỉnh sửa thành một chuỗi mới
                    newFavoriteList = TextUtils.join(",", favoriteListItems);
                }

                UserFavoriteRequest userFavoriteRequest = new UserFavoriteRequest(userId, newFavoriteList);
                dataManager.setUserFavoriteRequest(userFavoriteRequest);
                Log.d("klajflkasjd", "onClick: " + userFavoriteRequest);

                fetchDataFromApi(position);
            }
        });
    }

    public void fetchDataFromApi(int positon) {
        ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());
        apiManager.updateFavoriteUser(new Callback<UserResponse>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("klajflkasjd", "onResponse: " + response);

                    UserResponse userResponse = response.body();
                    if (userResponse != null) {
                        Toast.makeText(mContext, "Remove product to favorite successfully", Toast.LENGTH_SHORT).show();
                        removeProduct(positon);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Error").setMessage("Unable to fetch favorite list. Please try again later.").setPositiveButton("OK", null) // Bạn có thể thêm sự kiện xử lý khi người dùng bấm OK ở đây
                .show();
    }

    public void removeProduct(int position) {
        if (position >= 0 && position < mProducts.size()) {
            mProducts.remove(position);
            notifyItemRemoved(position);
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        ImageView btnDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameTextView = itemView.findViewById(R.id.name);
            priceTextView = itemView.findViewById(R.id.price);
            btnDelete = itemView.findViewById(R.id.close);
        }
    }
}

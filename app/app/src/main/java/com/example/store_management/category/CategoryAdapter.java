package com.example.store_management.category;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.store_management.R;
import com.example.store_management.common.DataManager;
import com.example.store_management.product.Product;
import com.example.store_management.product.ProductActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private Context mContext;
    private List<Category> mCategory;
    private ProductActivity productActivity;
    private int selectedPosition = 0; // Variable to store the selected position

    public CategoryAdapter(Context context, List<Category> category, ProductActivity productActivity) {
        mContext = context;
        mCategory = category;
        this.productActivity = productActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = mCategory.get(position);

        holder.nameTextView.setText(category.getName());

        // Use holder.getAdapterPosition() to get the current position
        if (holder.getAdapterPosition() == selectedPosition) {
            holder.nameTextView.setTextColor(Color.parseColor("#000000"));
        } else {
            holder.nameTextView.setTextColor(Color.parseColor("#909090"));
        }

        Glide.with(mContext)
                .load(category.getImageList())
                .placeholder(R.drawable.placeholder_product)
                .error(R.drawable.placeholder_product_error)
                .override(44, 44)
                .fitCenter()
                .into(holder.imageView);

        holder.btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Use holder.getAdapterPosition() to get the current position
                selectedPosition = holder.getAdapterPosition();
                notifyDataSetChanged();

                String categoryId = category.get_id();
                DataManager dataManager = DataManager.getInstance();
                List<Product> productList = dataManager.getProductList();
                List<Product> newProductList = new ArrayList<>();

                if (selectedPosition == 0) {
                    productActivity.setRecycleViewProduct(productList);
                } else {
                    String productFilterSearchInput = dataManager.getProductFilterSearchInput();
                    for (Product product : productList) {
                        if (categoryId.equals(product.getCategoryId()) && productFilterSearchInput.equals(product.getName())) {
                            newProductList.add(product);
                        }
                    }
                    productActivity.setRecycleViewProduct(newProductList);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView nameTextView;
        public ConstraintLayout btnCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameTextView = itemView.findViewById(R.id.name);
            btnCategory = itemView.findViewById(R.id.btnCategory);
        }
    }
}
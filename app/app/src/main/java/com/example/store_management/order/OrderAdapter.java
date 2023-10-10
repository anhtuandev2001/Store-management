package com.example.store_management.order;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_management.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context mContext;
    private List<Order> mOrder;

    public OrderAdapter(Context context, List<Order> orders) {
        mContext = context;
        mOrder = orders;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = mOrder.get(position);
        holder.bind(order);
    }

    @Override
    public int getItemCount() {
        return mOrder.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderId;
        TextView txtDate;
        TextView txtQuantity;
        TextView txtTotal;
        TextView txtStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderId = itemView.findViewById(R.id.txtOrderId);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }

        public void bind(Order order) {
            String status = getStatusString(order.getStatus());

            txtOrderId.setText(order.get_id());
            txtDate.setText(order.getOrderDate());
            txtQuantity.setText(String.valueOf(order.getQuantity()));
            txtTotal.setText(String.format("%.2f", order.getTotal()));
            txtStatus.setText(status);
        }

        private String getStatusString(String status) {
            switch (status) {
                case "1":
                    return "Delivered";
                case "0":
                    return "Processing";
                case "2":
                    return "Canceled";
                default:
                    return "";
            }
        }
    }
}

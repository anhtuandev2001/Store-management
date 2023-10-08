package com.example.store_management.address;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_management.R;
import com.example.store_management.api.ApiManager;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.user.UserAddressRequest;
import com.example.store_management.user.UserData;
import com.example.store_management.user.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    private Context mContext;
    private List<Address> mAddressList;
    private DataManager dataManager = DataManager.getInstance();
    private UserData userData = dataManager.getUserData();
    private ProgressDialog progressDialog;
    private ApiManager apiManager = new ApiManager(Constants.BASE_URL, userData.getToken());

    public AddressAdapter(Context context, List<Address> addressList) {
        mContext = context;
        mAddressList = addressList;

        // Khởi tạo ProgressDialog và cấu hình
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.address_item, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = mAddressList.get(position);

        holder.txtNameUser.setText(address.getFullName());
        holder.txtAddress.setText(address.getAddress());

        holder.checkBox.setOnCheckedChangeListener(null); // Remove the listener temporarily
        boolean defaultChecked = address.getId().equals(userData.getDefault_shipping_id());
        holder.checkBox.setChecked(defaultChecked);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String addressId = address.getId();
                String userId = userData.getId();

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (!isChecked) {
                        notifyDataSetChanged();
                    } else {
                        UserAddressRequest userAddressRequest = new UserAddressRequest(userId, addressId);
                        dataManager.setUserAddressRequest(userAddressRequest);
                        progressDialog.show();
                        fetchApi(addressId);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAddressList.size();
    }

    private void fetchApi(String addressId) {
        apiManager.updateAddressDefautl(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    userData.setDefault_shipping_id(addressId);
                    notifyDataSetChanged();
                } else {
                    showErrorMessage("Cannot update address");
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                showErrorMessage("Cannot update address");
            }
        });
    }

    private void showErrorMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
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

    public class AddressViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView txtNameUser;
        TextView txtAddress;
        ImageView btnEdit;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            txtNameUser = itemView.findViewById(R.id.txtNameUser);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }
}

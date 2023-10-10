package com.example.store_management.address;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.store_management.R;
import com.example.store_management.api.ApiManager;
import com.example.store_management.common.Constants;
import com.example.store_management.common.DataManager;
import com.example.store_management.product.ProductDetailActivity;
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
    private Context context;

    private DataManager dataManager = DataManager.getInstance();
    private UserData userData = dataManager.getUserData();
    private ProgressDialog progressDialog;
    private LayoutInflater inflater;
    private ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());

    public AddressAdapter(Context context, List<Address> addressList, LayoutInflater inflater) {
        mContext = context;
        mAddressList = addressList;
        this.inflater = inflater;
        this.context = context;
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
        holder.txtPhoneNumber.setText(address.getPhoneNumber());

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

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ấn vào btnEdit, hiển thị modal chỉnh sửa với dữ liệu đầy đủ
                Intent intent = new Intent(mContext, AddressDetailActivity.class);
                intent.putExtra("addressId", address.getId());
                mContext.startActivity(intent);
            }
        });

        holder.btnDeleteAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ấn vào btnEdit, hiển thị modal chỉnh sửa với dữ liệu đầy đủ
                showDeleteAddressConfirmationDialog(address);
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

    private void fetchApiDelete(Address address) {
        UserData userData = dataManager.getUserData();
        String addressId = address.getId();
        dataManager.setAddressId(addressId);
        ApiManager apiManager = new ApiManager(Constants.BASE_URL, dataManager.getToken());
        // Hiển thị ProgressDialog khi bắt đầu gọi API
        progressDialog.show();
        apiManager.deleteAddress(new Callback<AddressInsertResponse>() {
            @Override
            public void onResponse(Call<AddressInsertResponse> call, Response<AddressInsertResponse> response) {
                Log.d("asdfasdf", "onClick: " + response);

                // Ẩn ProgressDialog khi có kết quả hoặc xảy ra lỗi
                progressDialog.dismiss();
                Log.d("responseresponseresponse", "response: " + response);
                if (response.isSuccessful()) {
                    // Hiển thị thông báo "Add address successfully"
                    Intent intent = new Intent("ADDRESS_UPDATED");
                    mContext.sendBroadcast(intent);
                } else {
                    // Xử lý lỗi khi gọi API không thành công
                    showErrorMessage("Failed to fetch product data");
                }
            }

            @Override
            public void onFailure(Call<AddressInsertResponse> call, Throwable t) {
                Log.d("responseresponseresponse", "t: " + t);
                // Ẩn ProgressDialog khi có lỗi
                progressDialog.dismiss();
                // Xử lý lỗi khi gọi API thất bại
                showErrorMessage("Network error. Please try again later.");
            }
        });
    }

    private void showDeleteAddressConfirmationDialog(Address address) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Are you sure you want to delete this address?")
                .setTitle("Confirm Deletion")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Xác nhận người dùng muốn xóa địa chỉ
                        fetchApiDelete(address);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Đóng hộp thoại xác nhận xóa nếu người dùng không muốn xóa
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public class AddressViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView txtNameUser;
        TextView txtAddress;
        TextView txtPhoneNumber;
        ImageView btnEdit;
        ImageView btnDeleteAddress;

        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            txtNameUser = itemView.findViewById(R.id.txtNameUser);
            txtAddress = itemView.findViewById(R.id.txtAddress);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDeleteAddress = itemView.findViewById(R.id.btnDeleteAddress);
        }
    }
}

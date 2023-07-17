package com.bibon.furnitureshopping.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.activities.AddressShippingActivity;
import com.bibon.furnitureshopping.models.Address;

import java.util.ArrayList;

public class AddressRVAdapter extends RecyclerView.Adapter<AddressRVAdapter.AddressRvHolder> {
    private ArrayList<Address> addressList;
    private AddressShippingActivity context;

    public AddressRVAdapter(ArrayList<Address> addressList, AddressShippingActivity context) {
        this.addressList = addressList;
        this.context = context;
    }

    @NonNull
    @Override
    public AddressRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_address_view, parent, false);
        AddressRVAdapter.AddressRvHolder addressRvHolder = new AddressRvHolder(view);
        return addressRvHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull AddressRvHolder holder, int position) {
        Address address = addressList.get(position);
        String addressOneLine = address.getAddress() + ", " + address.getWard() + ", " + address.getDistrict() + ", " + address.getProvince();
        holder.tv_fullname.setText(address.getFullname());
        holder.tv_address_detail.setText(addressOneLine);
        holder.tv_phone.setText("|  " + address.getPhone());

        if (address.getStatus() == 1 || addressList.size() == 1) {
            holder.btn_default.setVisibility(View.VISIBLE);
        } else if (address.getStatus() == 0) {
            holder.btn_default.setVisibility(View.GONE);
        }

        holder.ctl_address_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.setDefaultAddress(address.get_id(), address.getUser(), position);
            }
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.deleteAddress(address.get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public class AddressRvHolder extends RecyclerView.ViewHolder {
        private TextView tv_fullname;
        private TextView tv_address_detail;
        private TextView tv_phone;
        private ConstraintLayout ctl_address_view;
        private Button btn_default;
        private ImageView img_delete;

        public AddressRvHolder(@NonNull View itemView) {
            super(itemView);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
            tv_address_detail = itemView.findViewById(R.id.tv_address_detail);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            ctl_address_view = itemView.findViewById(R.id.ctl_address_view);
            btn_default = itemView.findViewById(R.id.btn_default);
            img_delete = itemView.findViewById(R.id.img_delete);
        }
    }

}

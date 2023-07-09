package com.bibon.furnitureshopping.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.Address;

import java.util.ArrayList;

public class AddressRVAdapter extends RecyclerView.Adapter<AddressRVAdapter.AddressRvHolder> {
    private ArrayList<Address> addressList;

    public AddressRVAdapter(ArrayList<Address> addressList) {
        this.addressList = addressList;
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
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public class AddressRvHolder extends RecyclerView.ViewHolder {
        private TextView tv_fullname;
        private TextView tv_address_detail;

        public AddressRvHolder(@NonNull View itemView) {
            super(itemView);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
            tv_address_detail = itemView.findViewById(R.id.tv_address_detail);
        }
    }

}

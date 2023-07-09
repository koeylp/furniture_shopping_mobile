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

public class AddressRVAdapter extends RecyclerView.Adapter<AddressRVAdapter.ViewHolder> {
    private ArrayList<Address> addressList;

    public AddressRVAdapter(ArrayList<Address> addressList) {
        this.addressList = addressList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_address_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = addressList.get(position);
        String addressOneLine = address.getAddress() + ", " + address.getWard() + ", " + address.getDistrict() + ", " + address.getProvince();
        holder.tv_fullname.setText(address.getFullName());
        holder.tv_address_detail.setText(addressOneLine);
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fullname;
        private TextView tv_address_detail;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_fullname = itemView.findViewById(R.id.tv_fullname);
            tv_address_detail = itemView.findViewById(R.id.tv_address_detail);
        }

    }

}

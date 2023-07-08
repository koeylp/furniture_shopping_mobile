package com.bibon.furnitureshopping.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.Address;

import java.util.ArrayList;

public class AddressRVAdapter extends RecyclerView.Adapter<AddressRVAdapter.ViewHolder> {
    private ArrayList<Address> addressList;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public AddressRVAdapter(ArrayList<Address> addressList) {
        this.addressList = addressList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.activity_address_shipping, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = addressList.get(position);

        holder.tvFullname.setText(address.getFullName());
        holder.tvAddress.setText(address.getAddress());
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFullname;
        private TextView tvAddress;

        public ViewHolder(View itemView) {
            super(itemView);
            tvFullname = itemView.findViewById(R.id.tvFullname);
            tvAddress = itemView.findViewById(R.id.tvAddress);

        }

        public void bind(Address address) {
            tvFullname.setText(address.getFullName());
            tvAddress.setText(address.getAddress());

        }
    }

}

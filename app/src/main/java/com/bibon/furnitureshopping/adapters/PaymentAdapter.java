package com.bibon.furnitureshopping.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.Payment;

import java.util.List;

public class PaymentAdapter extends BaseAdapter {
    Context context;
    private List<Payment> paymentList;

    public PaymentAdapter(Context context, List<Payment> paymentList) {
        this.context = context;
        this.paymentList = paymentList;
    }

    @Override
    public int getCount() {
        return paymentList != null ? paymentList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_payment_view, parent, false);
        TextView txtName = view.findViewById(R.id.tvPaymentName);
        ImageView imagePayment = view.findViewById(R.id.ivPaymentImage);

        txtName.setText(paymentList.get(position).getName());
        imagePayment.setImageResource(paymentList.get(position).getPaymentImage());


        return view;
    }
}

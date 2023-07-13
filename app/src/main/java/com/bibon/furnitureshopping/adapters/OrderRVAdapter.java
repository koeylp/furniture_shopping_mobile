package com.bibon.furnitureshopping.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.Order;

import java.util.ArrayList;

public class OrderRVAdapter extends RecyclerView.Adapter<OrderRVAdapter.OrderRvHolder> {

    public ArrayList<Order> orderList;

    public OrderRVAdapter(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_pending_order_view, parent, false);
        OrderRvHolder orderRvHolder = new OrderRvHolder(view);
        return orderRvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRvHolder holder, int position) {
        Order currentOrder = orderList.get(position);
        holder.tv_order_id.setText("Order Id: " + currentOrder.get_id());
        holder.tv_total_price.setText("$" + currentOrder.getTotalPrice());
        holder.tv_date.setText(currentOrder.getOrderDate());

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrderRvHolder extends RecyclerView.ViewHolder {

        TextView tv_order_id, tv_total_price, tv_date;

        public OrderRvHolder(@NonNull View itemView) {
            super(itemView);
            tv_order_id = itemView.findViewById(R.id.tv_order_id);
            tv_total_price = itemView.findViewById(R.id.tv_total_price);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }
}

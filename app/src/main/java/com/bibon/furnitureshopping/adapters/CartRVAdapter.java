package com.bibon.furnitureshopping.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.fragments.CartFragment;
import com.bibon.furnitureshopping.models.Cart;
import com.bibon.furnitureshopping.models.CartItem;
import com.squareup.picasso.Picasso;

public class CartRVAdapter extends RecyclerView.Adapter<CartRVAdapter.CartRvHolder> {

    Cart cart;
    String email;
    CartFragment context;
    double total;

    public CartRVAdapter(Cart cart, String email, CartFragment context, double total) {
        this.cart = cart;
        this.email = email;
        this.context = context;
        this.total = total;
    }

    @NonNull
    @Override
    public CartRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cart_view, parent, false);
        CartRvHolder cartRvHolder = new CartRvHolder(view);
        return cartRvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartRvHolder holder, int position) {
        CartItem cartItem = cart.getItems().get(position);
        Picasso.get().load(cartItem.getProduct().getImg()).placeholder(R.drawable.armchair).error(R.drawable.armchair).fit().into(holder.img_product);
        holder.tv_product_name.setText(cartItem.getProduct().getProductName());
        holder.tv_product_price.setText(cartItem.getProduct().getPrice() + "");
        holder.tv_quantity.setText(cartItem.getCartQuantity() + "");

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart.getItems().remove(position);
                total -= cartItem.getCartQuantity() * cartItem.getProduct().getPrice();
                context.passEmailToDeleteItem(email, cartItem.getProduct().get_id(), total);
                context.callback(position, cart, total);
            }
        });

        holder.img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return cart.getItems().size();
    }

    public class CartRvHolder extends RecyclerView.ViewHolder {

        public ImageView img_product, img_plus, img_minus, img_delete;
        public TextView tv_product_name, tv_product_price, tv_quantity;
        ConstraintLayout constraintLayout;

        public CartRvHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            img_plus = itemView.findViewById(R.id.iv_plus);
            img_minus = itemView.findViewById(R.id.iv_minus);
            img_delete = itemView.findViewById(R.id.img_delete);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_product_price = itemView.findViewById(R.id.tv_price);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);
        }
    }
}

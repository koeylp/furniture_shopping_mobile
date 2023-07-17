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
import com.bibon.furnitureshopping.activities.MainActivity;
import com.bibon.furnitureshopping.fragments.CartFragment;
import com.bibon.furnitureshopping.models.Cart;
import com.bibon.furnitureshopping.models.CartItem;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.Locale;

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
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String price = currencyVN.format(cartItem.getProduct().getPrice());
        holder.tv_product_price.setText(price);
        holder.tv_quantity.setText(String.valueOf(cartItem.getCartQuantity()));

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
                cartItem.setCartQuantity(cartItem.getCartQuantity() + 1);
                holder.tv_quantity.setText(String.valueOf(cartItem.getCartQuantity()));
                total += cartItem.getProduct().getPrice();
                context.passEmailToUpdateQuantity(cartItem.getCartQuantity(), email, cartItem.getProduct().get_id(), total);
                context.callback(position, cart, total);
            }
        });

        holder.img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItem.getCartQuantity() - 1 == 0 ) {
                    cart.getItems().remove(position);
                    total -= cartItem.getCartQuantity() * cartItem.getProduct().getPrice();
                    context.passEmailToDeleteItem(email, cartItem.getProduct().get_id(), total);
                    context.callback(position, cart, total);
                } else {
                    cartItem.setCartQuantity(cartItem.getCartQuantity() - 1);
                    holder.tv_quantity.setText(String.valueOf(cartItem.getCartQuantity()));
                    total -= cartItem.getProduct().getPrice();
                    context.passEmailToUpdateQuantity(cartItem.getCartQuantity(), email, cartItem.getProduct().get_id(), total);
                    context.callback(position, cart, total);
                }


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

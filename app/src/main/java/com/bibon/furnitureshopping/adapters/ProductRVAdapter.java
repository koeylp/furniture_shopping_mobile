package com.bibon.furnitureshopping.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.activities.MainActivity;
import com.bibon.furnitureshopping.animations.FlyToCartAnimation;
import com.bibon.furnitureshopping.fragments.HomeFragment;
import com.bibon.furnitureshopping.models.CartAdding;
import com.bibon.furnitureshopping.models.CartItemAdding;
import com.bibon.furnitureshopping.models.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.ProductRvHolder> {

    public ArrayList<Product> products;
    HomeFragment context;
    String user;

    MainActivity mainActivity;

    public ProductRVAdapter(ArrayList<Product> products, HomeFragment context, String user, MainActivity mainActivity) {
        this.products = products;
        this.context = context;
        this.user = user;
        this.mainActivity = mainActivity;
    }

    public class ProductRvHolder extends RecyclerView.ViewHolder {

        public ImageView img_product, img_add_to_cart;
        public TextView tv_product_name, tv_product_price;
        ConstraintLayout constraintLayout;

        public ProductRvHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            tv_product_price = itemView.findViewById(R.id.tv_price);
            img_add_to_cart = itemView.findViewById(R.id.img_add_to_cart);
            constraintLayout = itemView.findViewById(R.id.constraint_layout);
        }
    }

    @NonNull
    @Override
    public ProductRVAdapter.ProductRvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_product_list_view, parent, false);
        ProductRvHolder productRvHolder = new ProductRvHolder(view);
        return productRvHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRVAdapter.ProductRvHolder holder, @SuppressLint("RecyclerView") int position) {
        Product currentItem = products.get(position);
        Picasso.get().load(currentItem.getImg()).placeholder(R.drawable.armchair).error(R.drawable.armchair).fit().into(holder.img_product);
        holder.tv_product_name.setText(currentItem.getProductName());
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        String price = currencyVN.format(currentItem.getPrice());
        holder.tv_product_price.setText(price);
        holder.img_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<CartItemAdding> items = new ArrayList<>();
                items.add(new CartItemAdding(currentItem.get_id(), 1));
                CartAdding bigCart = new CartAdding(user, items);
                context.addToCart(bigCart);
//                Toast.makeText(v.getContext(), "Added " + currentItem.getProductName() + " to cart", Toast.LENGTH_SHORT).show();
                mainActivity.setCountProductToCart(mainActivity.getCountProduct() + 1 );

                //add animation fly to cart
                FlyToCartAnimation.translateAnimation(mainActivity.getViewAnimation(), holder.img_add_to_cart, mainActivity.getViewEndAnimation(), new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
            }
        });

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.toProductDetail(currentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


}

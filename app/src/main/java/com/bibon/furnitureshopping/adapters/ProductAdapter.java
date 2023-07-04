package com.bibon.furnitureshopping.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.activities.LandingActivity;
import com.bibon.furnitureshopping.models.Cart;
import com.bibon.furnitureshopping.models.CartList;
import com.bibon.furnitureshopping.models.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Product> productList;
    private CartList cartList;

    public ProductAdapter(Context context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tvProductName, tvPrice;
        ImageView imgProduct, imgAddToCart;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder.tvProductName = (TextView) convertView.findViewById(R.id.tv_product_name);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
            holder.imgProduct = (ImageView) convertView.findViewById(R.id.img_product);
            holder.imgAddToCart = (ImageView) convertView.findViewById(R.id.img_add_to_cart);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getProductName());
        holder.tvPrice.setText(product.getPrice() + "");
        Picasso.get().load(product.getImg()).placeholder(R.drawable.armchair).error(R.drawable.armchair).fit().into(holder.imgProduct);

        holder.imgAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (cartList == null) {
//                    cartList = new CartList();
//                }
//                Cart cart = new Cart(product.getProductName(), product.getPrice(), product.getQuantity(), product.getImg());
//                cartList.getCartList().add(cart);

            }
        });

        return convertView;
    }
}

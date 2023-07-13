package com.bibon.furnitureshopping.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.utils.UpdateProductListRecyclerView;
import com.bibon.furnitureshopping.models.Category;
import com.bibon.furnitureshopping.models.Product;
import com.bibon.furnitureshopping.services.ProductService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRVAdapter extends RecyclerView.Adapter<CategoryRVAdapter.CategoryRvViewHolder> {

    ProductService productService;
    private ArrayList<Category> items;
    private ArrayList<Product> productList;
    UpdateProductListRecyclerView updateRecyclerView;
    Activity activity;
    String user;

    public CategoryRVAdapter(ArrayList<Category> items, ArrayList<Product> productList, Activity activity, UpdateProductListRecyclerView updateRecyclerView, String user) {
        this.items = items;
        this.productList = productList;
        this.activity = activity;
        this.updateRecyclerView = updateRecyclerView;
        this.user = user;
    }

    @NonNull
    @Override
    public CategoryRVAdapter.CategoryRvViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category_view, parent, false);
        CategoryRvViewHolder categoryRvViewHolder = new CategoryRvViewHolder(view);
        return categoryRvViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryRVAdapter.CategoryRvViewHolder holder, int position) {
        Category currentItem = items.get(position);
        Picasso.get().load(currentItem.getImg()).placeholder(R.drawable.armchair).error(R.drawable.armchair).fit().into(holder.img_category);
        holder.tv_category_name.setText(currentItem.getCategoryName());

        updateRecyclerView.callback(position, productList, user);

        ArrayList<Product> filteredProduct = new ArrayList<>();
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getCategory().equals(currentItem.get_id())) {
                filteredProduct.add(productList.get(i));
            }
        }

        holder.linearLayoutCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(filteredProduct.size());
                updateRecyclerView.callback(position, filteredProduct, user);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CategoryRvViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_category_name;
        public ImageView img_category;
        LinearLayout linearLayoutCategory;

        public CategoryRvViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_category_name = itemView.findViewById(R.id.tv_category_name);
            img_category = itemView.findViewById(R.id.img_category);
            linearLayoutCategory = itemView.findViewById(R.id.linear_layout_category);
        }
    }
}

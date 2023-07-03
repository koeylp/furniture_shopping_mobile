package com.bibon.furnitureshopping.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.adapters.ProductAdapter;
import com.bibon.furnitureshopping.models.Product;
import com.bibon.furnitureshopping.repositories.ProductRepository;
import com.bibon.furnitureshopping.services.ProductService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    ProductService productService;
    Product product;
    TextView tv_product_name_detail, tv_price, tv_quantity, tv_description_detail;
    ImageView img_product_detail, img_plus_detail, img_minus_detail;
    Button btn_add_to_cart_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        tv_product_name_detail = (TextView) findViewById(R.id.tv_product_name_detail);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_quantity = (TextView) findViewById(R.id.tv_quantity);
        tv_description_detail = (TextView) findViewById(R.id.tv_description_detail);
        img_product_detail = (ImageView) findViewById(R.id.img_product_detail);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        product = (Product) args.getSerializable("Product");

        productService = ProductRepository.getProductService();

        if (product != null) {
            tv_product_name_detail.setText(product.getProductName());
            tv_price.setText(product.getPrice() + "");
            tv_description_detail.setText(product.getDescription());
            Picasso.get().load(product.getImg()).placeholder(R.drawable.armchair).error(R.drawable.armchair).fit().into(img_product_detail);
        }

    }

}
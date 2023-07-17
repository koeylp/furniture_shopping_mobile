package com.bibon.furnitureshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.CartAdding;
import com.bibon.furnitureshopping.models.CartItemAdding;
import com.bibon.furnitureshopping.models.Product;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.CartRepository;
import com.bibon.furnitureshopping.repositories.ProductRepository;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.CartService;
import com.bibon.furnitureshopping.services.ProductService;
import com.bibon.furnitureshopping.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    ProductService productService;
    CartService cartService;
    UserService userService;
    Product product;
    TextView tv_product_name_detail, tv_price, tv_quantity, tv_description_detail;
    ImageView img_product_detail, img_plus_detail, img_minus_detail;
    ConstraintLayout btn_add_to_cart_detail;
    String email;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        // Service Calling
        productService = ProductRepository.getProductService();
        cartService = CartRepository.getCartService();
        userService = UserRepository.getUserService();

        // View Calling
        ImageView img_back = findViewById(R.id.img_back);
        img_plus_detail = findViewById(R.id.img_plus_detail);
        img_minus_detail = findViewById(R.id.img_minus_detail);
        btn_add_to_cart_detail = findViewById(R.id.btn_add_to_cart_detail);
        tv_product_name_detail = findViewById(R.id.tv_product_name_detail);
        tv_price = findViewById(R.id.tv_price);
        tv_quantity = findViewById(R.id.tv_quantity);
        tv_description_detail = findViewById(R.id.tv_description_detail);
        img_product_detail = findViewById(R.id.img_product_detail);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        int[] quantity = new int[1];
        quantity[0] = 1;


        // Intent
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        product = (Product) args.getSerializable("Product");


        if (product != null) {
            tv_product_name_detail.setText(product.getProductName());
            Locale localeVN = new Locale("vi", "VN");
            NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
            String price = currencyVN.format(product.getPrice());
            tv_price.setText(price);
            tv_description_detail.setText(product.getDescription());
            Picasso.get().load(product.getImg()).placeholder(R.drawable.armchair).error(R.drawable.armchair).fit().into(img_product_detail);
        }

        img_plus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity[0]++;
                tv_quantity.setText(String.valueOf(quantity[0]));
            }
        });

        img_minus_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity[0] - 1 > 0) {
                    quantity[0]--;
                    tv_quantity.setText(String.valueOf(quantity[0]));
                }
            }
        });

        // Get email
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
        }

        btn_add_to_cart_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserByEmail(email, quantity[0]);
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Fragment", "cart");
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });

    }

    public void addToCart(CartAdding cart) {
        try {
            Call<CartAdding> call = cartService.addToCart(cart);
            call.enqueue(new Callback<CartAdding>() {
                @Override
                public void onResponse(Call<CartAdding> call, Response<CartAdding> response) {

                }

                @Override
                public void onFailure(Call<CartAdding> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getUserByEmail(String email, int quantity) {
        try {
            Call<User> call = userService.getUserByEmail(email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user == null) {
                        return;
                    }
                    ArrayList<CartItemAdding> items = new ArrayList<>();
                    items.add(new CartItemAdding(product.get_id(), quantity));
                    CartAdding bigCart = new CartAdding(user.get_id(), items);
                    addToCart(bigCart);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println("error: " + t);
                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

}
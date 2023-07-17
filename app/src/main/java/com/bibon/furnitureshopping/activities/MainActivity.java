package com.bibon.furnitureshopping.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.adapters.CartRVAdapter;
import com.bibon.furnitureshopping.databinding.ActivityMainBinding;
import com.bibon.furnitureshopping.fragments.CartFragment;
import com.bibon.furnitureshopping.fragments.HomeFragment;
import com.bibon.furnitureshopping.fragments.NotificationFragment;
import com.bibon.furnitureshopping.fragments.ProfileFragment;
import com.google.android.gms.maps.MapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.bibon.furnitureshopping.models.Cart;
import com.bibon.furnitureshopping.models.CartItem;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.CartRepository;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.CartService;
import com.bibon.furnitureshopping.services.UserService;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionDeniedResponse;
//import com.karumi.dexter.listener.PermissionGrantedResponse;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.single.PermissionListener;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    TextView viewEndAnimation;
    ImageView viewAnimation;

    private int countProduct = 0;

    CartService cartService;
    UserService userService;
    String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //Animation
        viewEndAnimation = (TextView) findViewById(R.id.view_end_animation);
        viewAnimation = (ImageView) findViewById(R.id.view_animation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");

        System.out.println(args + " intent");

        if (args != null) {
            String fragment = args.getString("Fragment");
            if (fragment.equals("profile")) {
                replaceFragment(new ProfileFragment());
            } else if (fragment.equals("cart")) {
                replaceFragment(new CartFragment());
            }

        } else {
            replaceFragment(new HomeFragment());
        }


        ImageView img_back = (ImageView) findViewById(R.id.img_back);

        img_back.setVisibility(View.GONE);


        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    replaceFragment(new HomeFragment());
                } else if (item.getItemId() == R.id.cart) {
                    replaceFragment(new CartFragment());
                } else if (item.getItemId() == R.id.profile) {
                    replaceFragment(new ProfileFragment());
                } else if (item.getItemId() == R.id.noti) {
                    replaceFragment(new NotificationFragment());
                }
                return true;
            }
        });

        InitNavigateToMapFragmentFab();
    }

    private void InitNavigateToMapFragmentFab()
    {
        FloatingActionButton navigateMapFab = findViewById(R.id.navigate_map_fab);

        navigateMapFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestLocationPermission();
            }
        });
    }

    private void RequestLocationPermission()
    {
        Dexter.withActivity(MainActivity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startActivity(new Intent(MainActivity.this, StoreMapActivity.class));
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if(response.isPermanentlyDenied()){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Permission Denied")
                                    .setMessage("Permission to access device location is permanently denied. you need to go to setting to allow the permission.")
                                    .setNegativeButton("Cancel", null)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent();
                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            intent.setData(Uri.fromParts("package", getPackageName(), null));
                                        }
                                    })
                                    .show();
                        } else {
                            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .check();
    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flFragment, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public TextView getViewEndAnimation() {
        return viewEndAnimation;
    }

    public ImageView getViewAnimation() {
        return viewAnimation;
    }

    public void setCountProductToCart(int count) {
        countProduct = count;
        // Badge for cart
        BadgeDrawable badgeDrawable = binding.bottomNavigationView.getOrCreateBadge(R.id.cart);
        badgeDrawable.setVisible(true);
        badgeDrawable.setNumber(count);
    }

    public int getCountProduct() {
        return countProduct;
    }
    private void getUserByEmail(String email) {
        try {
            Call<User> call = userService.getUserByEmail(email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user == null) {
                        return;
                    }
                    getCartByUser(user.get_id(), email);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Reason: ", t.getMessage());
                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
    private void getCartByUser(String user, String email) {
        try {
            Call<Cart> call = cartService.getCartByUser(user);
            call.enqueue(new Callback<Cart>() {
                @Override
                public void onResponse(Call<Cart> call, Response<Cart> response) {
                    Cart cart = response.body();
                    if (cart == null) {
                        return;
                    }
                    setCountProductToCart(cart.getItems().size());
                }

                @Override
                public void onFailure(Call<Cart> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }
}
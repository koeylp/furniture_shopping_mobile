package com.bibon.furnitureshopping.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.databinding.ActivityMainBinding;
import com.bibon.furnitureshopping.fragments.CartFragment;
import com.bibon.furnitureshopping.fragments.HomeFragment;
import com.bibon.furnitureshopping.fragments.MapFragment;
import com.bibon.furnitureshopping.fragments.NotificationFragment;
import com.bibon.furnitureshopping.fragments.ProfileFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//import com.karumi.dexter.Dexter;
//import com.karumi.dexter.PermissionToken;
//import com.karumi.dexter.listener.PermissionDeniedResponse;
//import com.karumi.dexter.listener.PermissionGrantedResponse;
//import com.karumi.dexter.listener.PermissionRequest;
//import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
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
//                RequestLocationPermission();
                  replaceFragment(new MapFragment());
            }
        });
    }

//    private void RequestLocationPermission()
//    {
//        Dexter.withActivity(MainActivity.this)
//                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
//                .withListener(new PermissionListener() {
//                    @Override
//                    public void onPermissionGranted(PermissionGrantedResponse response) {
//                        replaceFragment(new MapFragment());
//                    }
//
//                    @Override
//                    public void onPermissionDenied(PermissionDeniedResponse response) {
//                        if(response.isPermanentlyDenied()){
//                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                            builder.setTitle("Permission Denied")
//                                    .setMessage("Permission to access device location is permanently denied. you need to go to setting to allow the permission.")
//                                    .setNegativeButton("Cancel", null)
//                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            Intent intent = new Intent();
//                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                            intent.setData(Uri.fromParts("package", getPackageName(), null));
//                                        }
//                                    })
//                                    .show();
//                        } else {
//                            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
//                        token.continuePermissionRequest();
//                    }
//                })
//                .check();
//    }


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
}
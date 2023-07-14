package com.bibon.furnitureshopping.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.activities.AddressShippingActivity;
import com.bibon.furnitureshopping.activities.LoginActivity;
import com.bibon.furnitureshopping.activities.OrderHistoryActivity;
import com.bibon.furnitureshopping.activities.ShowProfileActivity;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.UserService;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {

    FirebaseAuth mAuth;
    ImageView img;
    TextView tvUsername, tvEmail;
    UserService userService;
    LinearLayout linear_layout_my_orders;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        try {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            } else {
                System.out.println(currentUser);
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        img = view.findViewById(R.id.img_avatar);
        tvUsername = view.findViewById(R.id.tv_username);
        tvEmail = view.findViewById(R.id.tv_email);
        linear_layout_my_orders = view.findViewById(R.id.linearLayout_my_orders);

        userService = UserRepository.getUserService();

        AppCompatButton btn_log_out = view.findViewById(R.id.btn_logout);
        LinearLayout linearLayoutAddresses = view.findViewById(R.id.linearLayout_addresses);
        LinearLayout linearLayoutProfile = view.findViewById(R.id.linearLayout_my_profile);
        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        linearLayoutAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddressShippingActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        linearLayoutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ShowProfileActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        linear_layout_my_orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OrderHistoryActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        showUserInfor();

    }

    private void showUserInfor() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }

        String username = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        getUserByEmail(email);
        tvUsername.setText(username);
        tvEmail.setText(email);
        Glide.with(this).load(photoUrl).error(R.drawable.avatar).into(img);
    }

    private void getUserByEmail(String email) {
        User[] userModel = new User[1];
        try {
            Call<User> call = userService.getUserByEmail(email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user == null) {
                        return;
                    }
                    userModel[0] = new User(user.get_id(), user.getEmail(), user.getFullname());
                    tvUsername.setText(userModel[0].getFullname());
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
package com.bibon.furnitureshopping.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.activities.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ProfileFragment extends Fragment {

    FirebaseAuth mAuth;

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        try {
//            FirebaseUser currentUser = mAuth.getCurrentUser();
//            if (currentUser == null) {
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
//            } else {
//                System.out.println(currentUser + "kkkkk");
//            }
//        } catch (NullPointerException e) {
//            System.out.println(e);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView img_avatar = (ImageView) getView().findViewById(R.id.img_avatar);
        AppCompatButton btn_log_out = getView().findViewById(R.id.btn_logout);

        btn_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
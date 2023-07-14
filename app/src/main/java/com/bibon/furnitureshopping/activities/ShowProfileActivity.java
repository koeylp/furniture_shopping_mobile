package com.bibon.furnitureshopping.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowProfileActivity extends AppCompatActivity {

    ImageView img,back;
    TextView etUsername, etEmail, etPhone;
    UserService userService;

    public static final int MY_REQUEST_CODE = 10;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                if (intent == null) {
                    return;
                }
                Uri imageUri = intent.getParcelableExtra("imageUri");
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                    img.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_profile_activity);
        userService = UserRepository.getUserService();

        etUsername = findViewById(R.id.tvFullname);
        etEmail = findViewById(R.id.tvEmail);
        etPhone = findViewById(R.id.tvPhone);

        setUserInformation();

        img = findViewById(R.id.img_edit_profile);
        back = findViewById(R.id.img_back_show_profile);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShowProfileActivity.this, UpdateProfileActivity.class);
                activityResultLauncher.launch(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Fragment", "profile");
                intent.putExtra("BUNDLE", bundle);
                startActivity(intent);
            }
        });
    }
    private void setUserInformation() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return;
        }
        getUserByEmail(user.getEmail());
        etEmail.setText(user.getEmail());
        etPhone.setText("Macbook");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "You need to enable permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        activityResultLauncher.launch(intent);
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
                    user = new User(user.get_id(), user.getEmail(), user.getFullname());
                    etUsername.setText(user.getFullname());
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

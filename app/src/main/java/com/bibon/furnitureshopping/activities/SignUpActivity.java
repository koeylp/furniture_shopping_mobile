package com.bibon.furnitureshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.UserService;
import com.bibon.furnitureshopping.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextEmail, editTextName, editTextPassword, editTextConfirmPassword, editTextPhone;
    Button btn_sign_up;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    UserService userService;
    private static final String PASSWORD_REQUIRED = ". a digit must occur at least once\n" +
            ". a lower case letter must occur at least once\n" +
            ". an upper case letter must occur at least once\n" +
            ". a special character must occur at least once\n" +
            ". no whitespace allowed in the entire string\n" +
            ". at least 8 characters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Service Calling
        userService = UserRepository.getUserService();

        // View Calling
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        btn_sign_up = findViewById(R.id.btn_sign_up);


        TextView tvLoginNow = findViewById(R.id.tv_login_now);
        btn_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = String.valueOf(editTextEmail.getText());
                String name = String.valueOf(editTextName.getText());
                String password = String.valueOf(editTextPassword.getText());
                String confirmPassword = String.valueOf(editTextConfirmPassword.getText());
                String phone = String.valueOf(editTextPhone.getText());

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(v.getContext(), "Email Required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(name)) {
                    Toast.makeText(v.getContext(), "Full name Required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(v.getContext(), "Password Required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(v.getContext(), "Confirm Password Required", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(v.getContext(), "Phone required", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(v.getContext(), "Password and Confirm Password must be matched", Toast.LENGTH_SHORT).show();
                } else if (!Utils.validate(email)) {
                    Toast.makeText(v.getContext(), "Wrong format request Email", Toast.LENGTH_SHORT).show();
                } else if (!Utils.passwordvalidation(password)) {
                    editTextPassword.setError(PASSWORD_REQUIRED);
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Account Created",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                        User user = new User(email, name, phone);
                                        Call<User> call = userService.signup(user);
                                        call.enqueue(new Callback<User>() {
                                            @Override
                                            public void onResponse(Call<User> call, Response<User> response) {
                                                if (response.body() != null) {
                                                    System.out.println(user.getFullname());
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<User> call, Throwable t) {

                                            }
                                        });
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
            }
        });

        tvLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
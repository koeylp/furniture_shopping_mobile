package com.bibon.furnitureshopping.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.adapters.PaymentAdapter;
import com.bibon.furnitureshopping.models.Address;
import com.bibon.furnitureshopping.models.CreateOrder;
import com.bibon.furnitureshopping.models.Order;
import com.bibon.furnitureshopping.models.OrderDetail;
import com.bibon.furnitureshopping.models.Payment;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.repositories.AddressRepository;
import com.bibon.furnitureshopping.repositories.OrderRepository;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.AddressService;
import com.bibon.furnitureshopping.services.OrderService;
import com.bibon.furnitureshopping.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class CheckoutActivity extends AppCompatActivity {

    UserService userService;
    AddressService addressService;
    OrderService orderService;
    String email;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    TextView tv_name, tv_phone, tv_address_detail;
    ConstraintLayout btn_submit_order;
    Order order;
    Spinner spinnerPayment;

    PaymentAdapter paymentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // ZaloPay
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitle("");
        toolbar.setSubtitle("");

        // Service Calling
        userService = UserRepository.getUserService();
        addressService = AddressRepository.getAddressService();
        orderService = OrderRepository.getOrderService();

        // View Calling
        ImageView img_back = findViewById(R.id.img_back);
        TextView tv_order_price = findViewById(R.id.tv_order_price);
        TextView tv_total = findViewById(R.id.tv_total);
        tv_name = findViewById(R.id.tv_name);
        tv_phone = findViewById(R.id.tv_phone);
        tv_address_detail = findViewById(R.id.tv_address_detail);
        btn_submit_order = findViewById(R.id.btn_submit_order);

        // Intent
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        double total = args.getDouble("Total");

        tv_order_price.setText("$" + total);
        tv_total.setText("$" + (total + 5));


        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email = currentUser.getEmail();
        }

        getUserByEmail(email);

        ArrayList<OrderDetail> orderDetails = new ArrayList<>();


        btn_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestZaloPay(email, orderDetails, total);
            }

        });

        //set payment
        spinnerPayment = (Spinner) findViewById(R.id.spinnerPayment);
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(new Payment("Zalo Pay", R.drawable.zalopay));
        paymentList.add(new Payment("COD", R.drawable.cashondelivery));
        paymentAdapter = new PaymentAdapter(this, paymentList);
        spinnerPayment.setAdapter(paymentAdapter);
    }

    private void getAddressByUser(String user) {
        try {
            Call<Address[]> call = addressService.getAddressByUser(user);
            call.enqueue(new Callback<Address[]>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<Address[]> call, Response<Address[]> response) {
                    Address[] addresses = response.body();
                    if (addresses == null) {
                        return;
                    }
                    for (Address address : addresses) {
                        if (address.getStatus() == 1 || addresses.length == 1) {
                            tv_name.setText(address.getFullname());
                            tv_phone.setText(address.getPhone());
                            tv_address_detail.setText(address.getAddress() + ", " + address.getWard() + ", " + address.getDistrict() + ", " + address.getProvince());
                        }
                    }
                }

                @Override
                public void onFailure(Call<Address[]> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
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
                    getAddressByUser(user.get_id());
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

    private void getUserByEmailOrder(String email, double total, ArrayList<OrderDetail> orderDetails) {
        try {
            Call<User> call = userService.getUserByEmail(email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user == null) {
                        return;
                    }
                    order = new Order(user.get_id(), "", "ZaloPay", total, orderDetails);
                    getAddressByUserOrder(user.get_id(), order);
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

    private void getAddressByUserOrder(String user, Order order) {
        try {
            Call<Address[]> call = addressService.getAddressByUser(user);
            call.enqueue(new Callback<Address[]>() {
                @Override
                public void onResponse(Call<Address[]> call, Response<Address[]> response) {
                    Address[] addresses = response.body();
                    if (addresses == null) {
                        return;
                    }
                    for (Address address : addresses) {
                        if (address.getStatus() == 1 || addresses.length == 1) {
                            order.setAddress(address.get_id());
                            createOrder(order);
                        }
                    }

                }

                @Override
                public void onFailure(Call<Address[]> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void createOrder(Order order) {
        try {
            Call<Order> call = orderService.createOrder(order);
            call.enqueue(new Callback<Order>() {
                @Override
                public void onResponse(Call<Order> call, Response<Order> response) {

                }

                @Override
                public void onFailure(Call<Order> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void requestZaloPay(String email, ArrayList<OrderDetail> orderDetails, double total) {
        CreateOrder orderApi = new CreateOrder();

        try {
            JSONObject data = orderApi.createOrder("100000");
            String code = data.getString("return_code");

            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                System.out.println(token + "sf");
                ZaloPaySDK.getInstance().payOrder(CheckoutActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        Intent intent = new Intent(getApplicationContext(), ConfirmationActivity.class);

                        getUserByEmailOrder(email, total + 5, orderDetails);

                        startActivity(intent);
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {

                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                    }
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
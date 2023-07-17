package com.bibon.furnitureshopping.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.Address;
import com.bibon.furnitureshopping.models.District;
import com.bibon.furnitureshopping.models.Province;
import com.bibon.furnitureshopping.models.User;
import com.bibon.furnitureshopping.models.Ward;
import com.bibon.furnitureshopping.repositories.AddressRepository;
import com.bibon.furnitureshopping.repositories.UserRepository;
import com.bibon.furnitureshopping.services.AddressService;
import com.bibon.furnitureshopping.services.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressShippingActivity extends AppCompatActivity {
    Spinner spWard, spDistrict, spProvince;
    Button btn_save_address, btn_cancel;
    AddressService addressService;
    ArrayList<String> provinceListName;
    ArrayList<String> districtListName;
    ArrayList<String> wardListName;
    EditText it_fullname, it_address, it_phone;
    UserService userService;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final String REQUIRED = "Required";
    ImageView img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_shipping);

        // View Calling
        spWard = (Spinner) findViewById(R.id.spinnerWard);
        spDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spProvince = (Spinner) findViewById(R.id.spinnerProvince);
        it_fullname = findViewById(R.id.it_fullname);
        it_address = findViewById(R.id.it_address);
        it_phone = findViewById(R.id.it_phone);
        btn_save_address = findViewById(R.id.btn_save_address);
        btn_cancel = findViewById(R.id.btn_cancel_address);
        img_back = findViewById(R.id.img_back);

        // Service Calling
        userService = UserRepository.getUserService();
        addressService = AddressRepository.getAddressService();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddressShippingActivity.class);
                startActivity(intent);
            }
        });

        int[] province_code = new int[1];
        int[] district_code = new int[1];
        String[] province = new String[1];
        String[] district = new String[1];
        String[] ward = new String[1];


        spWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String ward_name = adapterView.getItemAtPosition(position).toString();
                ward[0] = ward_name;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String district_name = adapterView.getItemAtPosition(position).toString();
                district[0] = district_name;
                try {
                    Call<District[]> call = addressService.getAllDistricts();
                    call.enqueue(new Callback<District[]>() {
                        @Override
                        public void onResponse(Call<District[]> call, Response<District[]> response) {
                            District[] districts = response.body();
                            if (districts == null) {
                                return;
                            }
                            for (District district : districts) {
                                if (district.getName().equals(district_name)) {
                                    district_code[0] = district.getCode();
                                }
                            }
                            getWardByDistrictCode(district_code[0]);
                        }

                        @Override
                        public void onFailure(Call<District[]> call, Throwable t) {

                        }
                    });

                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String province_name = adapterView.getItemAtPosition(position).toString();
                province[0] = province_name;
                try {
                    Call<Province[]> call = addressService.getAllProvinces();
                    call.enqueue(new Callback<Province[]>() {
                        @Override
                        public void onResponse(Call<Province[]> call, Response<Province[]> response) {
                            Province[] provinces = response.body();
                            if (provinces == null) {
                                return;
                            }
                            for (Province province : provinces) {
                                if (province.getName().equals(province_name)) {
                                    province_code[0] = province.getCode();
                                }
                            }
                            getDistrictByProvinceCode(province_code[0]);
                        }

                        @Override
                        public void onFailure(Call<Province[]> call, Throwable t) {
                            System.out.println(t);
                        }
                    });
                } catch (Exception e) {
                    Log.d("Error", e.getMessage());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        getAllProvinces();
        String[] email = new String[1];
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            email[0] = currentUser.getEmail();
        }
        System.out.println(ward[0] + district[0] + province[0] + "out");

        System.out.println(email[0]);


        btn_save_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = it_fullname.getText().toString();
                String phone = it_phone.getText().toString();
                String address = it_address.getText().toString();

                if (TextUtils.isEmpty(fullname)) {
                    it_fullname.setError(REQUIRED);
                } else if (TextUtils.isEmpty(phone)) {
                    it_phone.setError(REQUIRED);
                } else if (TextUtils.isEmpty(address)) {
                    it_address.setError(REQUIRED);
                } else {
                    Address addressSelection = new Address("", fullname, phone, address, ward[0], district[0], province[0]);
                    getUserByEmail(email[0], addressSelection);
                    Intent intent = new Intent(v.getContext(), AddressShippingActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void getAllProvinces() {
        this.provinceListName = new ArrayList<>();
        try {
            Call<Province[]> call = addressService.getAllProvinces();
            call.enqueue(new Callback<Province[]>() {
                @Override
                public void onResponse(Call<Province[]> call, Response<Province[]> response) {
                    Province[] provinces = response.body();
                    if (provinces == null) {
                        return;
                    }
                    for (Province province : provinces) {
                        provinceListName.add(province.getName());
                    }
                    ArrayAdapter<String> adapterProvince =
                            new ArrayAdapter<>(AddAddressShippingActivity.this, android.R.layout.simple_spinner_item, provinceListName);
                    adapterProvince.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                    spProvince.setAdapter(adapterProvince);
                }

                @Override
                public void onFailure(Call<Province[]> call, Throwable t) {
                    System.out.println(t + " kkk");
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getDistrictByProvinceCode(int code) {
        this.districtListName = new ArrayList<>();
        try {
            Call<District[]> call = addressService.getAllDistricts();
            call.enqueue(new Callback<District[]>() {
                @Override
                public void onResponse(Call<District[]> call, Response<District[]> response) {
                    District[] districts = response.body();
                    if (districts == null) {
                        return;
                    }
                    for (District district : districts) {
                        if (code == district.getProvince_code()) {
                            districtListName.add(district.getName());
                            System.out.println(district.getName());
                        }
                    }
                    System.out.println(districtListName.size() + "lllas");
                    ArrayAdapter<String> adapterDistrict =
                            new ArrayAdapter<>(AddAddressShippingActivity.this, android.R.layout.simple_spinner_item, districtListName);
                    adapterDistrict.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                    spDistrict.setAdapter(adapterDistrict);
                }

                @Override
                public void onFailure(Call<District[]> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getWardByDistrictCode(int code) {
        this.wardListName = new ArrayList<>();
        try {
            Call<Ward[]> call = addressService.getAllWards();
            call.enqueue(new Callback<Ward[]>() {
                @Override
                public void onResponse(Call<Ward[]> call, Response<Ward[]> response) {
                    Ward[] wards = response.body();
                    if (wards == null) {
                        return;
                    }
                    for (Ward ward : wards) {
                        if (code == ward.getDistrict_code()) {
                            wardListName.add(ward.getName());
                        }
                    }
                    ArrayAdapter<String> adapterWard =
                            new ArrayAdapter<>(AddAddressShippingActivity.this, android.R.layout.simple_spinner_item, wardListName);
                    adapterWard.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
                    spWard.setAdapter(adapterWard);
                }

                @Override
                public void onFailure(Call<Ward[]> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void addNewAddress(Address newAddress) {
        try {
            Call<Address> call = addressService.addAddress(newAddress);
            call.enqueue(new Callback<Address>() {
                @Override
                public void onResponse(Call<Address> call, Response<Address> response) {
                    Toast.makeText(AddAddressShippingActivity.this, "Added successfully", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<Address> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }
    }

    private void getUserByEmail(String email, Address newAddress) {
        try {
            Call<User> call = userService.getUserByEmail(email);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user == null) {
                        return;
                    }
                    newAddress.setUser(user.get_id());
                    addNewAddress(newAddress);
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
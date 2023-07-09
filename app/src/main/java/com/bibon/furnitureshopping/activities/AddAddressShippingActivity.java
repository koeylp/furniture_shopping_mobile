package com.bibon.furnitureshopping.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bibon.furnitureshopping.R;
import com.bibon.furnitureshopping.models.District;
import com.bibon.furnitureshopping.models.Province;
import com.bibon.furnitureshopping.models.Ward;
import com.bibon.furnitureshopping.repositories.AddressRepository;
import com.bibon.furnitureshopping.services.AddressService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressShippingActivity extends AppCompatActivity {
    Spinner spWard, spDistrict, spProvince;
    ConstraintLayout constraintLayout;

    AddressService addressService;
    ArrayList<String> provinceListName;
    ArrayList<String> districtListName;
    ArrayList<String> wardListName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_shipping);

        spWard = (Spinner) findViewById(R.id.spinnerWard);
        spDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spProvince = (Spinner) findViewById(R.id.spinnerProvince);
        spProvince = (Spinner) findViewById(R.id.spinnerProvince);
        constraintLayout = (ConstraintLayout) findViewById(R.id.btn_save_address);

        int[] provice_code = new int[1];
        int[] district_code = new int[1];
        spWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String ward = adapterView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String district_name = adapterView.getItemAtPosition(position).toString();
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
                                    provice_code[0] = province.getCode();
                                }
                            }
                            getDistrictByProvinceCode(provice_code[0]);
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

        addressService = AddressRepository.getAddressService();
        getAllProvinces();


        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAddressShippingActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
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
//                        provinceList.add(new Province(province.getName(), province.getCode(), province.getDivision_type(), province.getCodename(), province.getPhone_code(), province.getDistricts()));
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
//                            districtListName.add(new District(district.getName(), district.getCode(), district.getDivision_type(), district.getCodename(), district.getProvince_code(), district.getWards()));
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
}
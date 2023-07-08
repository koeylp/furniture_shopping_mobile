package com.bibon.furnitureshopping.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bibon.furnitureshopping.R;

import java.util.ArrayList;

public class AddAddressShippingActivity extends AppCompatActivity {
    Spinner spWard, spDistrict,spProvince;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_shipping);

        spWard = (Spinner) findViewById(R.id.spinnerWard);
        spDistrict = (Spinner) findViewById(R.id.spinnerDistrict);
        spProvince = (Spinner) findViewById(R.id.spinnerProvince);
        spProvince = (Spinner) findViewById(R.id.spinnerProvince);
        constraintLayout = (ConstraintLayout) findViewById(R.id.btn_save_address);

        spWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                 String ward = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AddAddressShippingActivity.this, "Selected ward: " + ward, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String ward = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AddAddressShippingActivity.this, "Selected district: " + ward, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String ward = adapterView.getItemAtPosition(position).toString();
                Toast.makeText(AddAddressShippingActivity.this, "Selected province: " + ward, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<String> wardList = new ArrayList<>();
        wardList.add("Tăng Nhơn Phú A");
        wardList.add("Tăng Nhơn Phú B");
        wardList.add("Hiệp Phú");
        wardList.add("Tân Phú");
        wardList.add("Long ThạcH Mỹ");
        wardList.add("Phước Long B");
        ArrayAdapter<String> adapterWard =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wardList);
        adapterWard.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spWard.setAdapter(adapterWard);


        ArrayList<String> districtList = new ArrayList<>();
        districtList.add("Distric 1");
        districtList.add("Distric 5");
        districtList.add("Distric 8");
        districtList.add("Distric 9");
        districtList.add("Bình Thạnh");
        districtList.add("Phú Nhuận");
        ArrayAdapter<String> adapterDistrict =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districtList);
        adapterDistrict.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spDistrict.setAdapter(adapterDistrict);

        ArrayList<String> provinceList = new ArrayList<>();
        provinceList.add("TP Hồ Chí Minh");
        provinceList.add("Hà Nội");
        provinceList.add("Bình Phước");
        provinceList.add("Bình Dương");
        provinceList.add("Khánh Hòa");
        provinceList.add("Kiên Giang");
        ArrayAdapter<String> adapterProvince =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinceList);
        adapterProvince.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spProvince.setAdapter(adapterProvince);



        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddAddressShippingActivity.this, OrderHistoryActivity.class);
                startActivity(intent);
            }
        });
    }
}
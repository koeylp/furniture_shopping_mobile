package com.bibon.furnitureshopping.models;

import java.util.ArrayList;

public class Province {
    private String name;
    private int code;
    private String division_type;
    private String codename;
    private int phone_code;
    private ArrayList<District> districts;

    public Province(String name, int code, String division_type, String codename, int phone_code, ArrayList<District> districts) {
        this.name = name;
        this.code = code;
        this.division_type = division_type;
        this.codename = codename;
        this.phone_code = phone_code;
        this.districts = districts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDivision_type() {
        return division_type;
    }

    public void setDivision_type(String division_type) {
        this.division_type = division_type;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public int getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(int phone_code) {
        this.phone_code = phone_code;
    }

    public ArrayList<District> getDistricts() {
        return districts;
    }

    public void setDistricts(ArrayList<District> districts) {
        this.districts = districts;
    }
}

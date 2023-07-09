package com.bibon.furnitureshopping.models;

import java.util.ArrayList;

public class District {
    private String name;
    private int code;
    private String division_type;
    private String codename;
    private int province_code;
    private ArrayList<Ward> wards;

    public District(String name, int code, String division_type, String codename, int province_code, ArrayList<Ward> wards) {
        this.name = name;
        this.code = code;
        this.division_type = division_type;
        this.codename = codename;
        this.province_code = province_code;
        this.wards = wards;
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

    public int getProvince_code() {
        return province_code;
    }

    public void setProvince_code(int province_code) {
        this.province_code = province_code;
    }

    public ArrayList<Ward> getWards() {
        return wards;
    }

    public void setWards(ArrayList<Ward> wards) {
        this.wards = wards;
    }
}

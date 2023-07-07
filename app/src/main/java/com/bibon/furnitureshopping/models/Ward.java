package com.bibon.furnitureshopping.models;

public class Ward {
    private String name;
    private int code;
    private String division_type;
    private String codename;
    private int district_code;

    public Ward(String name, int code, String division_type, String codename, int district_code) {
        this.name = name;
        this.code = code;
        this.division_type = division_type;
        this.codename = codename;
        this.district_code = district_code;
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

    public int getDistrict_code() {
        return district_code;
    }

    public void setDistrict_code(int district_code) {
        this.district_code = district_code;
    }
}

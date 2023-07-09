package com.bibon.furnitureshopping.models;

public class Address {

    private String fullname;
    private String address;
    private String ward;
    private String district;
    private String province;

    public Address(String fullname, String address, String ward, String district, String province) {
        this.fullname = fullname;
        this.address = address;
        this.ward = ward;
        this.district = district;
        this.province = province;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}

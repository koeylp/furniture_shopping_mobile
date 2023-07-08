package com.bibon.furnitureshopping.models;

public class Address {

    private String fullName;
    private String address;
    private String zipCode;
    private String ward;
    private String district;
    private String province;

    public Address(String fullName, String address, String zipCode, String ward, String district, String province) {
        this.fullName = fullName;
        this.address = address;
        this.zipCode = zipCode;
        this.ward = ward;
        this.district = district;
        this.province = province;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
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

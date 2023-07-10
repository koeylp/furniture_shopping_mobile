package com.bibon.furnitureshopping.models;

public class Address {

    private String _id;
    private String user;
    private String fullname;
    private String phone;
    private String address;
    private String ward;
    private String district;
    private String province;
    private int status;

    public Address(String _id, String user, String fullname, String phone, String address, String ward, String district, String province, int status) {
        this._id = _id;
        this.user = user;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.status = status;
    }

    public Address(String user, String fullname, String phone, String address, String ward, String district, String province, int status) {
        this.user = user;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.ward = ward;
        this.district = district;
        this.province = province;
        this.status = status;
    }

    public Address(String user, String fullname, String phone, String address, String ward, String district, String province) {
        this.user = user;
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
        this.ward = ward;
        this.district = district;
        this.province = province;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

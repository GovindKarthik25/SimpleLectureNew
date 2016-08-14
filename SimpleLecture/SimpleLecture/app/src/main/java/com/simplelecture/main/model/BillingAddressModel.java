package com.simplelecture.main.model;

/**
 * Created by Raos on 8/6/2016.
 */
public class BillingAddressModel {

    private String UserID;
    private String FullName;
    private String Address;
    private String City;
    private String State;
    private String Pincode;
    private String Mobile;
    private String Email;

    public BillingAddressModel() {
    }

    public BillingAddressModel(String userID, String fullName, String address, String city, String state, String pincode, String mobile, String email) {
        UserID = userID;
        FullName = fullName;
        Address = address;
        City = city;
        State = state;
        Pincode = pincode;
        Mobile = mobile;
        Email = email;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPincode() {
        return Pincode;
    }

    public void setPincode(String pincode) {
        Pincode = pincode;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    @Override
    public String toString() {
        return "BillingAddressModel{" +
                "UserID='" + UserID + '\'' +
                ", FullName='" + FullName + '\'' +
                ", Address='" + Address + '\'' +
                ", City='" + City + '\'' +
                ", State='" + State + '\'' +
                ", Pincode='" + Pincode + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }
}

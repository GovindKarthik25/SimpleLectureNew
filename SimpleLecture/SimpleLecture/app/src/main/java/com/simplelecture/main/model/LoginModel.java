package com.simplelecture.main.model;

/**
 * Created by Raos on 2/16/2016.
 */
public class LoginModel {

    private String ue;
    private String up;
    private String LoginType;
    private String MobileOSType;

    public LoginModel() {
    }

    public LoginModel(String ue, String up, String loginType, String mobileOSType) {
        this.ue = ue;
        this.up = up;
        LoginType = loginType;
        MobileOSType = mobileOSType;
    }

    public String getUe() {
        return ue;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }

    public String getUp() {
        return up;
    }

    public void setUp(String up) {
        this.up = up;
    }

    public String getLoginType() {
        return LoginType;
    }

    public void setLoginType(String loginType) {
        LoginType = loginType;
    }

    public String getMobileOSType() {
        return MobileOSType;
    }

    public void setMobileOSType(String mobileOSType) {
        MobileOSType = mobileOSType;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "ue='" + ue + '\'' +
                ", up='" + up + '\'' +
                ", LoginType='" + LoginType + '\'' +
                ", MobileOSType='" + MobileOSType + '\'' +
                '}';
    }
}

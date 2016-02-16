package com.simplelecture.main.model;

/**
 * Created by Raos on 2/16/2016.
 */
public class LoginModel {

    private String ue;
    private String up;

    public LoginModel() {
    }

    public LoginModel(String ue, String up) {
        this.ue = ue;
        this.up = up;
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

    @Override
    public String toString() {
        return "LoginModel{" +
                "ue='" + ue + '\'' +
                ", up='" + up + '\'' +
                '}';
    }
}

package com.simplelecture.main.model.viewmodel;

/**
 * Created by Raos on 2/16/2016.
 */
public class LoginResponseModel {

    private String uId;
    private String uName;
    private String uToken;

    public LoginResponseModel() {
    }

    public LoginResponseModel(String uId, String uName, String uToken) {
        this.uId = uId;
        this.uName = uName;
        this.uToken = uToken;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }


    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuToken() {
        return uToken;
    }

    public void setuToken(String uToken) {
        this.uToken = uToken;
    }

    @Override
    public String toString() {
        return "LoginResponseModel{" +
                "uId='" + uId + '\'' +
                ", uName='" + uName + '\'' +
                ", uToken='" + uToken + '\'' +
                '}';
    }
}

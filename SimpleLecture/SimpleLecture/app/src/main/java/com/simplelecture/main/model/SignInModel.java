package com.simplelecture.main.model;

/**
 * Created by Raos on 7/17/2016.
 */
public class SignInModel {

    private String Name;
    private String Email;
    private String Mobile;
    private String Password;

    public SignInModel() {
    }

    public SignInModel(String name, String email, String mobile, String password) {
        Name = name;
        Email = email;
        Mobile = mobile;
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "SignInModel{" +
                "Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Mobile='" + Mobile + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}

package com.simplelecture.main.model.viewmodel;

/**
 * Created by Raos on 2/16/2016.
 */
public class LoginResponseModel {

    private String uId;
    private String uName;
    private String uToken;
    private boolean isSuccess;
    private String FailureType;
    private int MyCoursesCount;
    private boolean IsMobileVerified;


    public LoginResponseModel() {
    }

    public LoginResponseModel(String uId, String uName, String uToken, boolean isSuccess, String failureType, int myCoursesCount, boolean isMobileVerified) {
        this.uId = uId;
        this.uName = uName;
        this.uToken = uToken;
        this.isSuccess = isSuccess;
        FailureType = failureType;
        MyCoursesCount = myCoursesCount;
        IsMobileVerified = isMobileVerified;
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

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getFailureType() {
        return FailureType;
    }

    public void setFailureType(String failureType) {
        FailureType = failureType;
    }

    public int getMyCoursesCount() {
        return MyCoursesCount;
    }

    public void setMyCoursesCount(int myCoursesCount) {
        MyCoursesCount = myCoursesCount;
    }

    public boolean isMobileVerified() {
        return IsMobileVerified;
    }

    public void setMobileVerified(boolean mobileVerified) {
        IsMobileVerified = mobileVerified;
    }

    @Override
    public String toString() {
        return "LoginResponseModel{" +
                "uId='" + uId + '\'' +
                ", uName='" + uName + '\'' +
                ", uToken='" + uToken + '\'' +
                ", isSuccess=" + isSuccess +
                ", FailureType='" + FailureType + '\'' +
                ", MyCoursesCount=" + MyCoursesCount +
                ", IsMobileVerified=" + IsMobileVerified +
                '}';
    }
}

package com.simplelecture.main.model.viewmodel;

/**
 * Created by Raos on 7/16/2016.
 */
public class OutputResponseModel {

    private String message;
    private boolean isSuccess;

    public OutputResponseModel() {
    }

    public OutputResponseModel(String message, boolean isSuccess) {
        this.message = message;
        this.isSuccess = isSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "OutputResponseModel{" +
                "message='" + message + '\'' +
                ", isSuccess=" + isSuccess +
                '}';
    }
}

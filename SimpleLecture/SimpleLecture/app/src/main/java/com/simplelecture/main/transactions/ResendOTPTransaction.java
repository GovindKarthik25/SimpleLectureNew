package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by Raos on 7/27/2016.
 */
public class ResendOTPTransaction extends GetTransaction {

    private final String userID;
    private final String phoneNo;
    private String mUrlEncoded;

    public ResendOTPTransaction(JSONObject jsonObject, Context context, String userId, String phoneNO) {
        super(jsonObject, context);
        this.userID = userId;
        this.phoneNo = phoneNO;
    }

    @Override
    protected JSONObject setupRequestBody() {
        return null;
    }

    @Override
    protected String getUri() {
        return BuildConfig.BASE_URL;
    }

    @Override
    protected URI getRequestUri() {
        return null;
    }

    @Override
    protected String getHeader() {
        return null;
    }

    @Override
    protected String getUrlPrefix() {
        return Constants.GET_USER_RESENDOTP + userID +"/"+phoneNo;
    }

}

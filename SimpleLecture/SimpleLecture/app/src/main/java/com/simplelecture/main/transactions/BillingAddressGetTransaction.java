package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by M1032185 on 7/27/2016.
 */
public class BillingAddressGetTransaction extends GetTransaction {

    private final String userID;
    private String mUrlEncoded;

    public BillingAddressGetTransaction(JSONObject jsonObject, Context context, String userId) {
        super(jsonObject, context);
        this.userID = userId;
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
        return Constants.GET_BILLINGADDRESSGET + userID;
    }

}

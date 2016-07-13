package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.PostTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by Raos on 2/14/2016.
 */
public class ChangePasswordTransaction extends PostTransaction {

    private final String headerVal;
    JSONObject jsonObject;

    public ChangePasswordTransaction(JSONObject jsonObject, Context context, String token) {
        super(jsonObject, context);
        this.jsonObject = jsonObject;
        this.headerVal = token;
    }

    @Override
    protected JSONObject setupRequestBody() {
        return jsonObject;
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
        return headerVal;
    }

    @Override
    protected String getUrlPrefix() {
        return Constants.GET_CHANGEPASSWORD;
    }


}

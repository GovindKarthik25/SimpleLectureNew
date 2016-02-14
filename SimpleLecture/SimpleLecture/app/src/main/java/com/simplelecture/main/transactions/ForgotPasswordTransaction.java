package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.http.PostTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by M1032185 on 2/14/2016.
 */
public class ForgotPasswordTransaction extends PostTransaction {

    JSONObject jsonObject;

    public ForgotPasswordTransaction(JSONObject jsonObject,Context context) {
        super(jsonObject,context);
        this.jsonObject = jsonObject;
    }

    @Override
    protected JSONObject setupRequestBody() {
        return jsonObject;
    }

    @Override
    protected String getUrlPrefix() {
        return "authorizedriver";
    }

    @Override
    protected String getUri() {
        return BuildConfig.BASE_URL;
    }

    @Override
    protected URI getRequestUri() {
        return null;
    }


}

package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.PostTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by Raos on 7/27/2016.
 */
public class PromoCodeTransaction extends PostTransaction {

    JSONObject jsonObject;

    public PromoCodeTransaction(JSONObject jsonObject, Context context) {
        super(jsonObject, context);
        this.jsonObject = jsonObject;

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
        return null;
    }

    @Override
    protected String getUrlPrefix() {
        return Constants.GET_PROMOCODE;
    }

}

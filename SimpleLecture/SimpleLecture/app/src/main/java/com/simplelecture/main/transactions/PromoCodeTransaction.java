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
public class PromoCodeTransaction extends GetTransaction {

    private String userID;
    private String code;

    public PromoCodeTransaction(JSONObject jsonObject, Context context, String userId, String coDE) {
        super(jsonObject, context);
        userID = userId;
        code = coDE;
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
        return Constants.GET_Promocode + userID + "/" + code;
    }

}

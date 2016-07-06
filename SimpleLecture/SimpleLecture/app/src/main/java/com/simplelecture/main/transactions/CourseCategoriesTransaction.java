package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by Raos on 3/29/2016.
 */
public class CourseCategoriesTransaction extends GetTransaction {

    private String mUrlEncoded;

    private String mHeaderVal = "";

    public CourseCategoriesTransaction(JSONObject jsonObject, Context context, String urlEncoded) {
        super(jsonObject, context);
        mUrlEncoded = urlEncoded;
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
        return mHeaderVal;
    }

    @Override
    protected String getUrlPrefix() {
        return Constants.GET_COURSECATEGORIES + mUrlEncoded;
    }
}


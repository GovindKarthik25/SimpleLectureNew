package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by M1032185 on 2/18/2016.
 */
public class ChaptersTransaction extends GetTransaction {

    private String mUrlEncoded;

    private String mHeaderVal;

    public ChaptersTransaction(JSONObject jsonObject, Context context, String urlEncoded, String headerVal) {
        super(jsonObject, context);
        mUrlEncoded = urlEncoded;
        mHeaderVal = headerVal;
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
        return "Course/Chapters/" + mUrlEncoded;
    }
}


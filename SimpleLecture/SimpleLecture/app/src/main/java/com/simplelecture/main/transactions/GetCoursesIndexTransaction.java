package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by M1032185 on 2/15/2016.
 */
public class GetCoursesIndexTransaction extends GetTransaction {

    private String mUrlEncoded;

    public GetCoursesIndexTransaction(JSONObject jsonObject, Context context, String urlEncoded) {
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
        return "";
    }

    @Override
    protected String getUrlPrefix() {
        return "getcoursedetails/cId=" + mUrlEncoded;
    }
}

package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by M1032185 on 2/15/2016.
 */
public class GetCoursesIndexTransaction extends GetTransaction {

    String testUri =  "http://simplelecture.com/mobile/getcoursedetails?cId=1";

    public GetCoursesIndexTransaction(JSONObject jsonObject, Context context) {
        super(jsonObject, context);
    }

    @Override
    protected JSONObject setupRequestBody() {
        return null;
    }

    @Override
    protected String getUri() {
        return testUri;
    }

    @Override
    protected URI getRequestUri() {
        return null;
    }

    @Override
    protected String getUrlPrefix() {
        return "";
    }
}

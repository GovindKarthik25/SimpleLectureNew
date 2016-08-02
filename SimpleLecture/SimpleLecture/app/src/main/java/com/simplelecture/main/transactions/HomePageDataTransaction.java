package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by M1032185 on 3/25/2016.
 */
public class HomePageDataTransaction extends GetTransaction {

    private String mUrlEncoded;
    private String userID;

    public HomePageDataTransaction(JSONObject jsonObject, Context context, String urlEncoded, String userId) {
        super(jsonObject, context);
        mUrlEncoded = urlEncoded;
        userID = userId;
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
        return Constants.GET_HOME_PAGE + mUrlEncoded + userID;
       // "Home/Page/" + mUrlEncoded; userId
    }
}

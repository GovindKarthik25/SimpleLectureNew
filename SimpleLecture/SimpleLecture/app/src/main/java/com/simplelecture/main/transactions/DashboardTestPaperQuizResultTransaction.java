package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by Raos on 8/29/2016.
 */
public class DashboardTestPaperQuizResultTransaction extends GetTransaction {

    private final String testId;

    public DashboardTestPaperQuizResultTransaction(JSONObject jsonObject, Context context, String testID) {
        super(jsonObject, context);
        this.testId = testID;
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
        return Constants.GET_USER_QUIZ_RESULT + testId;
    }

}

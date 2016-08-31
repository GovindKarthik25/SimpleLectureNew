package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.PostTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by M1032185 on 2/16/2016.
 */
public class SubmitQuizAnswerTransaction extends PostTransaction {

    JSONObject jsonObject;
    String testid;

    public SubmitQuizAnswerTransaction(JSONObject jsonObject, Context context, String testiD) {
        super(jsonObject, context);
        this.jsonObject = jsonObject;

        this.testid = testiD;

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
        return Constants.GET_USER_QUIZ_SUBMITANSWERS + testid;
    }
}

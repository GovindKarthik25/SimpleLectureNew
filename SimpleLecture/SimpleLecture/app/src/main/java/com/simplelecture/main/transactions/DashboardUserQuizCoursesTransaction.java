package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by Raos on 8/25/2016.
 */

public class DashboardUserQuizCoursesTransaction extends GetTransaction {

    private final String courseId;
    private String userID;

    public DashboardUserQuizCoursesTransaction(JSONObject jsonObject, Context context, String userId, String courseID) {
        super(jsonObject, context);
        userID = userId;
        courseId = courseID;
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
        return Constants.GET_USER_QUIZ_COURSES + userID + "/" +courseId;
    }
}


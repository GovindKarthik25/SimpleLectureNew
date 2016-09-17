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

public class DashboardForumCourseGetTransaction extends GetTransaction {

    private final String courseID;
    private String userID;

    public DashboardForumCourseGetTransaction(JSONObject jsonObject, Context context, String userId, String courseId) {
        super(jsonObject, context);
        userID = userId;
        courseID = courseId;
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
        return Constants.GET_USER_FORUMGET + userID + "/" + courseID;
    }
}


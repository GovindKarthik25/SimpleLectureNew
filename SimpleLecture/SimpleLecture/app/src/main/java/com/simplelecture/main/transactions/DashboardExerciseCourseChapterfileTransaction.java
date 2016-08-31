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

public class DashboardExerciseCourseChapterfileTransaction extends GetTransaction {

    private String userID;
    private String courseChapterID;

    public DashboardExerciseCourseChapterfileTransaction(JSONObject jsonObject, Context context, String userId, String courseChapterId) {
        super(jsonObject, context);
        userID = userId;
        courseChapterID = courseChapterId;
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
        return Constants.GET_USER_COURSE_CHAPTERFILE + userID + "/" + courseChapterID;
    }
}


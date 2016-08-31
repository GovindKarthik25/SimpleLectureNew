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
public class DashboardTestPaperQuizQuestionsTransaction extends GetTransaction {

    private final String userId;
    private final String courseChapterId;

    public DashboardTestPaperQuizQuestionsTransaction(JSONObject jsonObject, Context context, String userID, String courseChapterID) {
        super(jsonObject, context);
        this.userId = userID;
        this.courseChapterId = courseChapterID;
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
        return Constants.GET_USER_QUIZ_QUESTIONS + userId + "/" + courseChapterId;
    }

}

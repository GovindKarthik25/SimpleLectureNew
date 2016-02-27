package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by Raos on 2/23/2016.
 */
public class VimeoVideoTransaction extends GetTransaction {

    private final String videoId;

    public VimeoVideoTransaction(JSONObject jsonObject, Context context, String videoid) {
        super(jsonObject, context);
        videoId = videoid;
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
        return "Course/TopicVideo/" + videoId;
    }

   /* @Override
    protected String getUrlPrefix() {
        return videoId + "/config";
    }*/
}

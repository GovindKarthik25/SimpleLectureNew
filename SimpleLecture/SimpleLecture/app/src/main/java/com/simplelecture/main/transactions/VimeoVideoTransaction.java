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

    private final int ctId;
    private String mHeaderVal;

    public VimeoVideoTransaction(JSONObject jsonObject, Context context, int ctid, String headerVal) {
        super(jsonObject, context);
        ctId = ctid;
        mHeaderVal = headerVal;
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
        return mHeaderVal;
    }

    @Override
    protected String getUrlPrefix() {
        return "Course/TopicVideo/" + ctId;
    }

   /* @Override
    protected String getUrlPrefix() {
        return videoId + "/config";
    }*/
}

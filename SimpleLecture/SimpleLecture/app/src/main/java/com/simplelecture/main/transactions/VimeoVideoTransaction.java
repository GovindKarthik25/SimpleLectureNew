package com.simplelecture.main.transactions;

import android.content.Context;

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
        return null;
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
        return "https://player.vimeo.com/video/"+ videoId +"/config";
    }
}

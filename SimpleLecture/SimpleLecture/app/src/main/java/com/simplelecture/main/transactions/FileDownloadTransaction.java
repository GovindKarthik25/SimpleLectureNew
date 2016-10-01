package com.simplelecture.main.transactions;

import android.content.Context;

import com.simplelecture.main.BuildConfig;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.GetTransaction;

import org.json.JSONObject;

import java.net.URI;

/**
 * Created by Govind on 9/28/2016.
 */
public class FileDownloadTransaction extends GetTransaction {

    private String userID;
    @Override
    protected JSONObject setupRequestBody() {
        return null;
    }

    private String path;

    public FileDownloadTransaction(Context context, String path) {
        super(null,context);
        this.path = path;
    }

    @Override
    protected String getUri() {
        return "";
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
        return path;
    }
}


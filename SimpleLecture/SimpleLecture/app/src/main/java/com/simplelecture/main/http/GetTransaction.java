package com.simplelecture.main.http;

import android.content.Context;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

/**
 * Created by M1032185 on 2/5/2016.
 */
public abstract class GetTransaction extends Transaction {

    protected JSONObject mRequestBody;

    URI mUri;

    String header;

    public GetTransaction(JSONObject jsonObject, Context context) {
        super(context);
        mRequestBody = jsonObject;
    }

    @Override
    public void initializeExecution() throws Exception {
        super.initializeExecution();

        mRequestBody = setupRequestBody();
    }

    protected abstract JSONObject setupRequestBody();

    @Override
    protected HttpResponse sendRequest() throws IOException {
        return mRestMethod.sendGetRequest(mUri,getHeader());

    }

    @Override
    protected String getRequestBody() {
        if (mRequestBody != null) {
            return mRequestBody.toString();
        }

        return null;
    }

    @Override
    protected void setupRequestUri() {
        mUri = URI.create(getUri() + getUrlPrefix());
    }
}

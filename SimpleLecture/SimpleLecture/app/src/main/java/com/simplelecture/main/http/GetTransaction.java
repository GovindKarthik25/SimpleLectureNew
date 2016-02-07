package com.simplelecture.main.http;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

/**
 * Created by M1032185 on 2/5/2016.
 */
public class GetTransaction extends Transaction {

    protected JSONObject mRequestBody;

    URI mUri;

    public GetTransaction(JSONObject jsonObject) {
        mRequestBody = jsonObject;
    }

    @Override
    protected HttpResponse sendRequest() throws IOException {
        return mRestMethod.sendGetRequest(getRequestUri());

    }

    @Override
    protected String getRequestBody() {
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
}

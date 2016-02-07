package com.simplelecture.main.http;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;

/**
 * Created by M1032185 on 2/5/2016.
 */
public class PostTransaction extends Transaction {

    protected JSONObject mRequestBody;

    URI mUri;

    public PostTransaction(JSONObject jsonObject) {
        mRequestBody = jsonObject;
    }

    @Override
    protected HttpResponse sendRequest() throws IOException {
        return mRestMethod.sendPostRequest(getRequestUri(), getRequestBody());
    }

    @Override
    protected String getRequestBody() {
        if (mRequestBody != null) {
            return mRequestBody.toString();
        }

        return null;
    }

    @Override
    protected String getUri() {
        return "";
    }

    @Override
    protected URI getRequestUri() {
        // Construct base URL
        return mUri = URI.create(getUri());
    }
}

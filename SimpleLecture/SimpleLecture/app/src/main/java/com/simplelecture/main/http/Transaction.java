package com.simplelecture.main.http;

import android.util.Log;

import java.io.IOException;
import java.net.URI;

/**
 * Created by M1032185 on 2/5/2016.
 */
public abstract class Transaction {

    public HttpResponse mResponse;

    RestMethod mRestMethod;

    String mResponseString;

    private void initExecution(){
        mRestMethod = RestMethod.getInstance();
        mResponse = new HttpResponse();
    }

    /**
     * Returns the response body.
     *
     * @return the full response contents in form of a string.
     * @throws IllegalStateException if this method is called before this transaction has terminated,
     *                               or if it terminated with an error.
     */
    public String getResponseBody() throws IllegalStateException {

        // First try to see if the string is already cached
        if (mResponse != null && mResponse.getResponseBody() != null) {
            mResponseString = mResponse.getResponseBody();
            return mResponse.getResponseBody();
        }

        return null;
    }

    public boolean execute(){

        Log.d("", "Init Execution");
        initExecution();
        try {
            mResponse = sendRequest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    protected abstract HttpResponse sendRequest() throws IOException;

    protected abstract String getRequestBody();

    protected abstract String getUri();

    protected abstract URI getRequestUri();

}

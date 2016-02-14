package com.simplelecture.main.http;

import android.content.Context;
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

    Context mContext;

    public Transaction(Context context) {
        mContext = context;
    }

//    private void initExecution() {
//        mRestMethod = RestMethod.getInstance();
//        mResponse = new HttpResponse();
//    }

    public void initializeExecution() throws IllegalStateException, Exception {

        // Initialize uri and headers
        setupRequestUri();
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

    public HttpResponse execute() {

        Log.d("", "Init Execution");
        try {
            initializeExecution();
            mResponse = sendRequest();
            mResponseString = mResponse.getResponseBody();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mResponse;


    }


    protected abstract HttpResponse sendRequest() throws IOException;

    protected abstract String getRequestBody();

    protected abstract String getUri();

    protected abstract URI getRequestUri();

    protected abstract void setupRequestUri();

    protected String getUrlPrefix() {
        return "";
    }


}

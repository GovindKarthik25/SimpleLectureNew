package com.simplelecture.main.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by M1032185 on 2/4/2016.
 */
public class TransactionProcessor extends AsyncTask<Transaction, Integer, HttpResponse> {

    public static final String TAG = TransactionProcessor.class.getName();

    ProgressDialog progressDialog;

    Context mContext;

    Fragment mFragmentContext;

    private Transaction mT;

    public TransactionProcessor(Context context) {
        mContext = context;
    }

    public TransactionProcessor(Fragment fragmentContext) {
        mFragmentContext = fragmentContext;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected HttpResponse doInBackground(Transaction... params) {

        mT = params[0];
        HttpResponse response = null;

        // Execute transaction
        try {
            Log.d("Transaction", "Transaction begins");
            response = mT.execute();
        } catch (Exception e) {
            final String tName = mT.getClass().getSimpleName();
            Log.d(TAG, "Error while executing transaction %s for op %s id %d");
        }

        return response;
    }

    @Override
    protected void onPostExecute(HttpResponse response) {
        super.onPostExecute(response);

        try {
            final int statusCode = response.getStatusCode();

            if (isSuccesfulStatusCode(statusCode)) {

                if (mContext != null) {
                    ((NetworkLayer) mContext).parseResponse(response.getResponseBody());
                } else {
                    ((NetworkLayer) mFragmentContext).parseResponse(response.getResponseBody());
                }
            } else {
                String message = handleDefaultErrors(statusCode);
                ((NetworkLayer) mContext).showError(message);

                if (mContext != null) {
                    ((NetworkLayer) mContext).showError(message);
                } else {
                    ((NetworkLayer) mFragmentContext).showError(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isSuccesfulStatusCode(final int statusCode) {
        return statusCode == 200;
    }

    private String handleDefaultErrors(int statusCode) {

        String errorMessage = null;
        Log.e("statusCode--> " , statusCode +"");
        switch (statusCode) {
            case 404:
                break;
            case 500:
                break;
            case 400:
                errorMessage = "Bad Request.";
                break;
            case 401:
                errorMessage = "Un Authorized.";
                break;
        }

        return errorMessage;
    }

}

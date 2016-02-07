package com.simplelecture.main.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Created by M1032185 on 2/4/2016.
 */
public class TransactionProcessor extends AsyncTask<Transaction, Integer, Boolean> {

    public static final String TAG = TransactionProcessor.class.getName();

    ProgressDialog progressDialog;

    Context mContext;

    private Transaction mT;


    public TransactionProcessor(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Transaction... params) {

        mT = params[0];
        boolean status = false;

        // Execute transaction
        try {
            Log.d("Transaction", "Transaction begins");
            status = mT.execute();
        } catch (Exception e) {
            final String tName = mT.getClass().getSimpleName();
            Log.d(TAG, "Error while executing transaction %s for op %s id %d");
            status = false;
        }

        return status;
    }

    @Override
    protected void onPostExecute(Boolean status) {
        super.onPostExecute(status);

        ((NetworkLayer) mContext).parseResponse(mT.getResponseBody());
    }
}

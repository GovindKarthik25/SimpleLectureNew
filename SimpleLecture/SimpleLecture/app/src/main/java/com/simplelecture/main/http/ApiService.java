package com.simplelecture.main.http;

import android.content.Context;
import android.util.Log;

import com.simplelecture.main.model.LoginModel;
import com.simplelecture.main.transactions.LoginTransaction;
import com.simplelecture.main.transactions.MyCoursesTransaction;
import com.simplelecture.main.util.JsonFactory;
import com.simplelecture.main.util.Util;

import org.json.JSONObject;

/**
 * Created by M1032185 on 2/16/2016.
 */
public class ApiService {

    public static ApiService apiService = new ApiService();

    private ApiService() {

    }

    public static ApiService getApiService() {
        return apiService;
    }

    public void doLogin(LoginModel loginModel, Context context) {
        JsonFactory jsonFactory = new JsonFactory();
        JSONObject jsonObject = jsonFactory.getLoginParams(loginModel.getUe(), loginModel.getUp());
        LoginTransaction loginTransaction = new LoginTransaction(jsonObject, context);
        TransactionProcessor transactionProcessor = new TransactionProcessor(context);
        transactionProcessor.execute(loginTransaction);

    }

    public void doGetMyCourses(String uId, Context mContext) {

        //use this to get token stored in prefrences
//        String token = Util.getFromPrefrences(mContext, "urkey");

        String token = "alc3ZXpKOE1MSWl2aVlBV25tNHlpSHlRc3N3MkYvWGFLZGRsV3FLU0QzWT06REVFS1NIQU5BSURVMTlAR01BSUwuQ09NOjYzNTkxMjM1MjY4NzE0Njg0OQ==";

        MyCoursesTransaction myCoursesTransaction = new MyCoursesTransaction(null, mContext, uId, token);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(myCoursesTransaction);

    }
}

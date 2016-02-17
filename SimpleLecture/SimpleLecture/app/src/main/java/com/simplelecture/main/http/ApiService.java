package com.simplelecture.main.http;

import android.content.Context;
import android.util.Log;

import com.simplelecture.main.model.LoginModel;
import com.simplelecture.main.transactions.CoursesDetailsTransaction;
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
        try {
            JsonFactory jsonFactory = new JsonFactory();
            JSONObject jsonObject = jsonFactory.getLoginParams(loginModel.getUe(), loginModel.getUp());
            LoginTransaction loginTransaction = new LoginTransaction(jsonObject, context);
            TransactionProcessor transactionProcessor = new TransactionProcessor(context);
            transactionProcessor.execute(loginTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetMyCourses(Context mContext, String uId) {

        try {
            //use this to get token stored in prefrences
            String token = Util.getFromPrefrences(mContext, "uToken");

            //String token = "alc3ZXpKOE1MSWl2aVlBV25tNHlpSHlRc3N3MkYvWGFLZGRsV3FLU0QzWT06REVFS1NIQU5BSURVMTlAR01BSUwuQ09NOjYzNTkxMjM1MjY4NzE0Njg0OQ==";

            MyCoursesTransaction myCoursesTransaction = new MyCoursesTransaction(null, mContext, uId, token);
            TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
            transactionProcessor.execute(myCoursesTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getCourseDetails(String uId, Context mContext) {

        CoursesDetailsTransaction coursesDetailsTransaction = new CoursesDetailsTransaction(null, mContext, uId);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(coursesDetailsTransaction);

    }
}

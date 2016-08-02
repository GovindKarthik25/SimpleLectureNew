package com.simplelecture.main.util;

import com.simplelecture.main.model.SignInModel;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by M1032185 on 2/14/2016.
 */
public class JsonFactory {

    JSONObject jsonObject;

    public JsonFactory() {

        jsonObject = new JSONObject();
    }

    public JSONObject getLoginParams(String ue, String up) {

        try {
            jsonObject.put("ue", ue);
            jsonObject.put("up", up);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject getForgotPwdParams(String userEmail) {

        try {
            jsonObject.put("Email", userEmail);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getChangePwdParams(String uId, String oldPassword, String confirmPassword) {

        try {
            jsonObject.put("uid", uId);
            jsonObject.put("currentPwd", oldPassword);
            jsonObject.put("newPwd", confirmPassword);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getSignInParams(SignInModel signInModel) {

        try {
            jsonObject.put("Name", signInModel.getName());
            jsonObject.put("Email", signInModel.getEmail());
            jsonObject.put("Mobile", signInModel.getMobile());
            jsonObject.put("Password", signInModel.getPassword());


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getCoursePostReview(String uId, String cid, String reviewText) {

        try {
            jsonObject.put("uid", uId);
            jsonObject.put("cid ", cid);
            jsonObject.put("review", reviewText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public JSONObject getCartAdd(String userID, String courseID, String months, String courseMaterial) {

        try {

            jsonObject.put("UserID", userID);
            jsonObject.put("CourseID", courseID);
            jsonObject.put("Months", months);
            jsonObject.put("CourseMaterial", courseMaterial);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

}

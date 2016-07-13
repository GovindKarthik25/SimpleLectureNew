package com.simplelecture.main.util;

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
}

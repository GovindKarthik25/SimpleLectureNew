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

    public JSONObject getForgotPwdParams(String userEmail, String userHeight, String userWeight, String journeyId, String deviceid) {

        try {
            jsonObject.put("journeyid", journeyId);
            jsonObject.put("emailid", userEmail);
            jsonObject.put("height", userHeight);
            jsonObject.put("weight", userWeight);
            jsonObject.put("deviceid", deviceid);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}

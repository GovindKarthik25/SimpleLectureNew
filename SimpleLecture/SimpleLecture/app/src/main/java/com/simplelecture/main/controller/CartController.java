package com.simplelecture.main.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.model.viewmodel.CartDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.CourseListCartModel;
import com.simplelecture.main.model.viewmodel.CourseMonths;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Raos on 7/31/2016.
 */
public class CartController {

    private Gson gson = new Gson();
    private JsonParser parser = new JsonParser();
    private CartDetailsResponseModel cartResponseModel;
    private JsonArray jarray;
    private List<CourseListCartModel> courseListCartModelLstArray;
    private List<CourseMonths> courseMonthsLstArray;

    public CartController() {
    }


    public CartDetailsResponseModel getCartDetails(String response) {

        try {
            cartResponseModel = gson.fromJson(response, CartDetailsResponseModel.class);
            JSONObject jSONObject = new JSONObject(response);

            String monthContent = jSONObject.getString("Months");
            jarray = parser.parse(monthContent).getAsJsonArray();

            courseMonthsLstArray = new ArrayList<CourseMonths>();
            for (JsonElement obj : jarray) {
                CourseMonths courseMonthsObj = gson.fromJson(obj, CourseMonths.class);
                courseMonthsLstArray.add(courseMonthsObj);
            }

            cartResponseModel.setCourseMonths(courseMonthsLstArray);

            String cartContent = jSONObject.getString("CourseList");
            jarray = parser.parse(cartContent).getAsJsonArray();

            courseListCartModelLstArray = new ArrayList<CourseListCartModel>();
            for (JsonElement obj : jarray) {
                CourseListCartModel courseListCartModelObj = gson.fromJson(obj, CourseListCartModel.class);
                courseListCartModelLstArray.add(courseListCartModelObj);
            }

            cartResponseModel.setCourseCartList(courseListCartModelLstArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return cartResponseModel;
    }

}

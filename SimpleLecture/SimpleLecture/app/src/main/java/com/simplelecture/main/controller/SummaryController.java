package com.simplelecture.main.controller;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.model.viewmodel.OrderSummaryListModel;
import com.simplelecture.main.model.viewmodel.OrderSummaryModel;
import com.simplelecture.main.model.viewmodel.PromoDetails;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Raos on 8/1/2016.
 */
public class SummaryController {

    private Gson gson = new Gson();
    private OrderSummaryModel orderSummaryModel;
    private JsonArray jarray;
    private JsonArray yJarray;
    private JsonParser parser = new JsonParser();
    private ArrayList<OrderSummaryListModel> orderSummaryListModelArray;

    public SummaryController() {
    }


    public OrderSummaryModel getSummaryDetails(String response) {

        try {

            orderSummaryModel = gson.fromJson(response, OrderSummaryModel.class);

            JSONObject jSONObject = new JSONObject(response);

            String courseListContent = jSONObject.getString("CourseList");
            jarray = parser.parse(courseListContent).getAsJsonArray();

            orderSummaryListModelArray = new ArrayList<OrderSummaryListModel>();
            for (JsonElement obj : jarray) {
                OrderSummaryListModel orderSummaryListModelObj = gson.fromJson(obj, OrderSummaryListModel.class);
                orderSummaryListModelArray.add(orderSummaryListModelObj);
            }

            orderSummaryModel.setOrderSummaryListModel(orderSummaryListModelArray);

            String promoDetailsContent = jSONObject.getString("PromoDetails");

            PromoDetails promoDetailsObj = gson.fromJson(promoDetailsContent, PromoDetails.class);

            orderSummaryModel.setPromoDetails(promoDetailsObj);

            Log.i("orderSummaryModel", orderSummaryModel.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }

        return orderSummaryModel;
    }
}

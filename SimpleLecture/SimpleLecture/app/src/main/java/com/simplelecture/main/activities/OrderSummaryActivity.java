package com.simplelecture.main.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.CartDetailsAdapter;
import com.simplelecture.main.adapters.OrderDetailsAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.CartDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.CourseMaterials;
import com.simplelecture.main.model.viewmodel.OrderSummaryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class OrderSummaryActivity extends AppCompatActivity implements NetworkLayer, OnItemClickListener {

    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;

    OrderDetailsAdapter cartDetailsAdapter;

    Toolbar toolbar;

    ArrayList<OrderSummaryModel> cartDetailsResponseModels;

    private String param_get_ServiceCallResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.orders_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        readFileFromAssets();

    }

    private void readFileFromAssets() {

        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("Order.json"), "UTF-8"));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            parseResponse1(stringBuilder.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<CourseMaterials> courseMaterialsLstArray;
    private JsonParser parser = new JsonParser();
    private JsonArray jarray;

    private void parseResponse1(String response) {

        Gson gson = new Gson();
        cartDetailsResponseModels = new ArrayList<>();


        try {
            JSONObject jSONObject = new JSONObject(response);
            String bannersLstResponse = jSONObject.getString("CourseList");
            jarray = parser.parse(bannersLstResponse).getAsJsonArray();

            for(JsonElement obj : jarray){
                JSONObject jsonObject = new JSONObject(obj.toString());
                OrderSummaryModel cartDetailsResponseModel = gson.fromJson(obj.toString(), OrderSummaryModel.class);
                String courseMaterialsContent = jsonObject.getString("CourseMaterials");
                jarray = parser.parse(courseMaterialsContent).getAsJsonArray();
                courseMaterialsLstArray = new ArrayList<CourseMaterials>();


                for (JsonElement obj1 : jarray) {
                    CourseMaterials courseMaterialsObj = gson.fromJson(obj1, CourseMaterials.class);
                    courseMaterialsLstArray.add(courseMaterialsObj);
                    cartDetailsResponseModel.setCourseMaterials(courseMaterialsLstArray);
                }

                cartDetailsResponseModels.add(cartDetailsResponseModel);

            }

            cartDetailsAdapter = new OrderDetailsAdapter(getApplicationContext(), cartDetailsResponseModels, this);
            recyclerView.setAdapter(cartDetailsAdapter);

            Log.d("", "" + cartDetailsResponseModels);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResponse(String response) {
        if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_CART_ALL)) {

            parseResponse(response);

        } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_CART_REMOVE)) {

            try {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("isSuccess")) {
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Try again later.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_CART_CHANGEMONTH)) {

        }
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}

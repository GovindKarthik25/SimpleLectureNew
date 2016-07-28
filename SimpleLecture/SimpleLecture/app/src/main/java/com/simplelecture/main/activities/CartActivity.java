package com.simplelecture.main.activities;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.CartDetailsAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.CartDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.CourseMaterials;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CartActivity extends AppCompatActivity implements OnItemClickListener, NetworkLayer {


    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;

    CartDetailsAdapter cartDetailsAdapter;

    Toolbar toolbar;

    ArrayList<CartDetailsResponseModel> cartDetailsResponseModels;

    private String param_get_ServiceCallResult = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        readFileFromAssets();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void readFileFromAssets() {

        try {
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("Response.json"), "UTF-8"));
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
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                CartDetailsResponseModel cartDetailsResponseModel = gson.fromJson(jsonObject.toString(), CartDetailsResponseModel.class);

                courseMaterialsLstArray = new ArrayList<CourseMaterials>();
                String courseMaterialsContent = jsonObject.getString("CourseMaterials");
                jarray = parser.parse(courseMaterialsContent).getAsJsonArray();

                for (JsonElement obj : jarray) {
                    CourseMaterials courseMaterialsObj = gson.fromJson(obj, CourseMaterials.class);
                    courseMaterialsLstArray.add(courseMaterialsObj);
                    cartDetailsResponseModel.setCourseMaterials(courseMaterialsLstArray);
                }

                cartDetailsResponseModels.add(cartDetailsResponseModel);
            }

            cartDetailsAdapter = new CartDetailsAdapter(getApplicationContext(), cartDetailsResponseModels, this);
            recyclerView.setAdapter(cartDetailsAdapter);

            Log.d("", "" + cartDetailsResponseModels);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {

        if(position == -1){



        }else{
            String courseId = cartDetailsResponseModels.get(position).getCourseId();

            ApiService.getApiService().doRemoveFromCart(getApplicationContext(), courseId);
        }



    }

    @Override
    public void parseResponse(String response) {
        if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_CART_ALL)) {

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
}

package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.OrderDetailsAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.controller.SummaryController;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.OrderSummaryModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;

import org.json.JSONObject;

public class OrderSummaryActivity extends AppCompatActivity implements NetworkLayer, OnItemClickListener {

    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;

    OrderDetailsAdapter cartDetailsAdapter;

    Toolbar toolbar;


    private String param_get_ServiceCallResult = "";
    private ProgressDialog pd;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private CoordinatorLayout coordinatorLayout;
    private OrderSummaryModel orderSummaryModel;
    private TextView subTotal_TextView;
    private TextView discount_TextView;
    private TextView tax_TextView;
    private TextView total_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Order Summary");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        subTotal_TextView = (TextView) findViewById(R.id.subTotal_TextView);
        discount_TextView = (TextView) findViewById(R.id.discount_TextView);
        tax_TextView = (TextView) findViewById(R.id.tax_TextView);
        total_TextView = (TextView) findViewById(R.id.total_TextView);

        recyclerView = (RecyclerView) findViewById(R.id.orders_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        showSummary();

    }

    private void showSummary() {

        try {
            if (new ConnectionDetector(OrderSummaryActivity.this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_ORDER_SUMMARY;
                pd = new Util().waitingMessage(OrderSummaryActivity.this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetSummaryDetails(OrderSummaryActivity.this);
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // OrderSummaryModel


    @Override
    public void parseResponse(String response) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            Gson gson = new Gson();
            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_ORDER_SUMMARY)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);

                    String dataContent = jSONObject1.getString("data");
                    orderSummaryModel = new SummaryController().getSummaryDetails(dataContent);

                    cartDetailsAdapter = new OrderDetailsAdapter(getApplicationContext(), orderSummaryModel.getOrderSummaryListModel(), this);
                    recyclerView.setAdapter(cartDetailsAdapter);

                    subTotal_TextView.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryModel.getSubTotalPrice())).toString());
                    discount_TextView.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryModel.getPromocodeDiscountPrice())).toString());
                    tax_TextView.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryModel.getTaxPrice())).toString());
                    total_TextView.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryModel.getTotalPrice())));
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(String error) {
        if (pd.isShowing()) {
            pd.cancel();
        }

    }

    @Override
    public void onItemClick(View view, int position) {

    }
}

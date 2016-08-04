package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.CartDetailsAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.controller.CartController;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.CartDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONObject;

public class CartActivity extends AppCompatActivity implements OnItemClickListener, NetworkLayer {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    CartDetailsAdapter cartDetailsAdapter;
    Toolbar toolbar;
    private String param_get_ServiceCallResult = "";
    private ProgressDialog pd;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private CoordinatorLayout coordinatorLayout;
    private CartDetailsResponseModel cartDetailsResponseModels;
    private OutputResponseModel outputResponseModel;
    private TextView lbl_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Cart");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        lbl_total = (TextView) findViewById(R.id.lbl_total);
        recyclerView = (RecyclerView) findViewById(R.id.cart_recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadCartDetails();

        // Put this where onclick of Spinner
          /*  if (new ConnectionDetector(CartActivity.this).isConnectingToInternet()) {
        param_get_ServiceCallResult = Constants.GET_CART_CHANGEMONTH;
        pd = new Util().waitingMessage(CartActivity.this, "", getResources().getString(R.string.loading));
        ApiService.getApiService().doChangeMonthFromCart(CartActivity.this, courseId, months);

    } else {
        snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
    }*/

    }

    public void doContinueShopping(View view) {
        finish();
        new ViewManager().gotoHomeView(this);
    }

    public void doCheckout(View view) {
        new ViewManager().gotoOrderSummaryActivity(this);
    }

    private void loadCartDetails() {

        try {
            if (new ConnectionDetector(CartActivity.this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_CART_ALL;
                pd = new Util().waitingMessage(CartActivity.this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetCartDetails(CartActivity.this);
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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


    @Override
    public void onItemClick(View view, int position) {

        try {
            if (position == -1) {


            } else {
                String courseId = cartDetailsResponseModels.getCourseCartList().get(position).getCourseId();

                if (new ConnectionDetector(CartActivity.this).isConnectingToInternet()) {
                    param_get_ServiceCallResult = Constants.GET_CART_REMOVE;
                    pd = new Util().waitingMessage(CartActivity.this, "", getResources().getString(R.string.loading));
                    ApiService.getApiService().doRemoveFromCart(CartActivity.this, courseId);

                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResponse(String response) {

        try {
            if (pd.isShowing()) {
                pd.cancel();
            }

            Gson gson = new Gson();

            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_CART_ALL)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);

                    String dataContent = jSONObject1.getString("data");

                    cartDetailsResponseModels = new CartController().getCartDetails(dataContent);

                    lbl_total.setText("Total : Rs " + Util.decFormat(Float.valueOf(cartDetailsResponseModels.getTotalPrice())));

                    cartDetailsAdapter = new CartDetailsAdapter(CartActivity.this, cartDetailsResponseModels.getCourseCartList(), this);
                    recyclerView.setAdapter(cartDetailsAdapter);
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));

                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_CART_REMOVE)) {

                outputResponseModel = gson.fromJson(response, OutputResponseModel.class);
                if (outputResponseModel.isSuccess()) {
                    Toast.makeText(CartActivity.this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();

                    loadCartDetails();
                } else {
                    alertMessageManagement.alertDialogActivation(CartActivity.this, 1, "Alert!", outputResponseModel.getMessage(), "OK", "");
                }

            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_CART_CHANGEMONTH)) {


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
}

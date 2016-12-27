package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.simplelecture.main.model.viewmodel.PlaceOrderResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.Validator;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONObject;

public class OrderSummaryActivity extends AppCompatActivity implements NetworkLayer, OnItemClickListener, View.OnClickListener {

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
    private Button btn_continue_shopping;
    private Button button_Apply;
    private EditText editText_PromoCode;
    private TextInputLayout input_layout_Promocode;
    private CheckBox chk_TermOfUse;
   // private CheckBox chk_PrivacyPlicy;
    private PlaceOrderResponseModel placeOrderResponseModel;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(Util.setActionBarText("Order Summary"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        snack = new SnackBarManagement(OrderSummaryActivity.this);
        alertMessageManagement = new AlertMessageManagement(OrderSummaryActivity.this);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        subTotal_TextView = (TextView) findViewById(R.id.subTotal_TextView);
        discount_TextView = (TextView) findViewById(R.id.discount_TextView);
        tax_TextView = (TextView) findViewById(R.id.tax_TextView);
        total_TextView = (TextView) findViewById(R.id.total_TextView);
        btn_continue_shopping = (Button) findViewById(R.id.btn_continue_shopping);
        button_Apply = (Button) findViewById(R.id.button_Apply);
        input_layout_Promocode = (TextInputLayout) findViewById(R.id.input_layout_Promocode);
        editText_PromoCode = (EditText) findViewById(R.id.editText_PromoCode);
        editText_PromoCode.setOnEditorActionListener(new DoneOnEditorActionListener());

        recyclerView = (RecyclerView) findViewById(R.id.orders_recycler_view);
        chk_TermOfUse = (CheckBox) findViewById(R.id.chk_TermOfUse);
        //chk_PrivacyPlicy = (CheckBox) findViewById(R.id.chk_PrivacyPlicy);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        showSummary();

        btn_continue_shopping.setOnClickListener(this);
        button_Apply.setOnClickListener(this);

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

    private void showPromoCode() {

        try {
            if (new ConnectionDetector(OrderSummaryActivity.this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_PROMOCODE;
                pd = new Util().waitingMessage(OrderSummaryActivity.this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetPromoCode(OrderSummaryActivity.this, editText_PromoCode.getText().toString().trim());
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showOrderPlaceOrder() {

        try {
            if (new ConnectionDetector(OrderSummaryActivity.this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_ORDER_PLACEORDER;
                pd = new Util().waitingMessage(OrderSummaryActivity.this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetPlaceOrder(OrderSummaryActivity.this, getResources().getString(R.string.EbsCode));
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void parseResponse(String response) {

        //   Log.i("parseResponse", response);
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }

            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_ORDER_SUMMARY)) {

                doResponseRefresh(response);

            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_PROMOCODE)) {

                doResponseRefresh(response);
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_ORDER_PLACEORDER)) {

                Gson gson = new Gson();
                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);
                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");

                    placeOrderResponseModel = new SummaryController().getPlaceOrder(dataContent);

                    new ViewManager().gotoEBSPaymentGatewayWebViewActivity(OrderSummaryActivity.this, placeOrderResponseModel);
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doResponseRefresh(String response) {
        try {
            Gson gson = new Gson();
            OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

            if (outputResponseModel.isSuccess()) {

                JSONObject jSONObject1 = new JSONObject(response);

                String dataContent = jSONObject1.getString("data");
                orderSummaryModel = new SummaryController().getSummaryDetails(dataContent);

                cartDetailsAdapter = new OrderDetailsAdapter(getApplicationContext(), orderSummaryModel.getOrderSummaryListModel(), this);
                recyclerView.setAdapter(cartDetailsAdapter);

                subTotal_TextView.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryModel.getSubTotalPrice())).toString());
                discount_TextView.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryModel.getPromoDetails().getPromoDiscountPrice())).toString());
                tax_TextView.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryModel.getTaxPrice())).toString());
                total_TextView.setText("Rs." + Util.decFormat(Float.valueOf(orderSummaryModel.getTotalOrderPrice())));
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));

                if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_PROMOCODE)) {
                    editText_PromoCode.setText("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showError(String error) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            if (error.isEmpty()) {
                error = "Error in connection";
            }

            snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onItemClick(View view, int position) {

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == button_Apply) {
            if (!Validator.validateName(this, editText_PromoCode, input_layout_Promocode, getString(R.string.err_msg_Promocode))) {
                return;
            }

            showPromoCode();

        } else if (v == btn_continue_shopping) {

            if (chk_TermOfUse.isChecked()) {
                if (orderSummaryModel.isContainsCourseMaterial()) {
                    new ViewManager().gotoBillingAddressActivityView(this, orderSummaryModel.isContainsCourseMaterial());
                } else {
                    //Payment GateWay

                    showOrderPlaceOrder();
                }
            } else {
                Toast.makeText(OrderSummaryActivity.this, "Please accept the Term of Use", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

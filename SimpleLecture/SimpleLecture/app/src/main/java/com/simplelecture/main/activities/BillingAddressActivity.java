package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.simplelecture.main.R;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.BillingAddressModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.Validator;

import org.json.JSONObject;

/**
 * Created by Raos on 8/6/2016.
 */
public class BillingAddressActivity extends AppCompatActivity implements View.OnClickListener, NetworkLayer {

    private Button btn_Save;
    private TextInputLayout input_layout_State, input_layout_email, input_layout_FullName, input_layout_mobile, input_layout_ShippingAddress, input_layout_Pincode;
    private EditText input_FullName, input_ShippingAddress, input_State, input_Pincode, input_mobile, input_email;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private BillingAddressModel billingAddressModel;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private ProgressDialog pd;
    private String param_get_ServiceCallResult;
    private boolean containsCourseMaterial;
    private Button btn_Pay;
    private EditText input_City;
    private TextInputLayout input_layout_City;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billingaddress);

        containsCourseMaterial = getIntent().getExtras().getBoolean("containsCourseMaterial");

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("Profile"));

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        input_layout_FullName = (TextInputLayout) findViewById(R.id.input_layout_FullName);
        input_layout_ShippingAddress = (TextInputLayout) findViewById(R.id.input_layout_ShippingAddress);
        input_layout_State = (TextInputLayout) findViewById(R.id.input_layout_State);
        input_layout_Pincode = (TextInputLayout) findViewById(R.id.input_layout_Pincode);
        input_layout_mobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        input_layout_email = (TextInputLayout) findViewById(R.id.input_layout_email);
        input_layout_City = (TextInputLayout) findViewById(R.id.input_layout_City);

        input_FullName = (EditText) findViewById(R.id.input_FullName);
        input_ShippingAddress = (EditText) findViewById(R.id.input_ShippingAddress);
        input_State = (EditText) findViewById(R.id.input_State);
        input_Pincode = (EditText) findViewById(R.id.input_Pincode);
        input_mobile = (EditText) findViewById(R.id.input_mobile);
        input_email = (EditText) findViewById(R.id.input_email);
        input_City = (EditText) findViewById(R.id.input_City);

        btn_Save = (Button) findViewById(R.id.btn_Save);
        btn_Pay = (Button) findViewById(R.id.btn_Pay);
        btn_Save.setOnClickListener(this);
        btn_Pay.setOnClickListener(this);

        if (containsCourseMaterial) {
            btn_Save.setVisibility(View.GONE);
            btn_Pay.setVisibility(View.VISIBLE);
        } else {
            btn_Save.setVisibility(View.VISIBLE);
            btn_Pay.setVisibility(View.GONE);
        }

        onBillingAddressLoad();

    }

    private void onBillingAddressLoad() {
        if (new ConnectionDetector(this).isConnectingToInternet()) {
            param_get_ServiceCallResult = Constants.GET_BILLINGADDRESSGET;
            pd = new Util().waitingMessage(this, "", getResources().getString(R.string.loading));
            ApiService.getApiService().doGetBillingAddress(this);
        } else {
            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
        }

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

        if (v == btn_Save) {

            onSaveBtn();
        } else if (v == btn_Pay) {
            onSaveBtn();

        }

    }

    private void onSaveBtn() {
        try {
            if (!Validator.validateName(this, input_FullName, input_layout_FullName, getString(R.string.err_msg_name))) {
                return;
            }

            if (!Validator.validateName(this, input_ShippingAddress, input_layout_ShippingAddress, getString(R.string.err_msg_shippingAddess))) {
                return;
            }

            if (!Validator.validateName(this, input_City, input_layout_City, getString(R.string.err_msg_City))) {
                return;
            }

            if (!Validator.validateName(this, input_State, input_layout_State, getString(R.string.err_msg_State))) {
                return;
            }

            if (!Validator.validateName(this, input_Pincode, input_layout_Pincode, getString(R.string.err_msg_pincode))) {
                return;
            }

            if (!Validator.validateName(this, input_mobile, input_layout_mobile, getString(R.string.err_msg_MobileNo))) {
                return;
            }

            if (!Validator.validateEmail(this, input_email, input_layout_email, getString(R.string.err_msg_email))) {
                return;
            }

            billingAddressModel = new BillingAddressModel();
            billingAddressModel.setFullName(input_FullName.getText().toString());
            billingAddressModel.setAddress(input_ShippingAddress.getText().toString());
            billingAddressModel.setCity(input_City.getText().toString());
            billingAddressModel.setState(input_State.getText().toString());
            billingAddressModel.setPincode(input_Pincode.getText().toString());
            billingAddressModel.setMobile(input_mobile.getText().toString());

            if (new ConnectionDetector(this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_BILLINGADDRESSSAVE;
                pd = new Util().waitingMessage(this, "", getResources().getString(R.string.loading));
                ApiService.getApiService().doBillingAddress(this, billingAddressModel);
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void parseResponse(String response) {

        try {

            Log.i("parseResponse", String.valueOf(containsCourseMaterial));
            if (pd.isShowing()) {
                pd.cancel();
            }

            Gson gson = new Gson();
            OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

            if (param_get_ServiceCallResult.equals(Constants.GET_BILLINGADDRESSSAVE)) {

                if (outputResponseModel.isSuccess()) {
                    if (containsCourseMaterial) {
                        // To Payment Gate Way
                        Toast.makeText(BillingAddressActivity.this, "Payment Gate Way", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(BillingAddressActivity.this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));

                }


            } else if (param_get_ServiceCallResult.equals(Constants.GET_BILLINGADDRESSGET)) {
                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");

                    billingAddressModel = gson.fromJson(dataContent, BillingAddressModel.class);

                    input_FullName.setText(Validator.validateForNull(billingAddressModel.getFullName()));
                    input_ShippingAddress.setText(Validator.validateForNull(billingAddressModel.getAddress()));
                    input_State.setText(Validator.validateForNull(billingAddressModel.getState()));
                    input_Pincode.setText(Validator.validateForNull(billingAddressModel.getPincode()));
                    input_mobile.setText(Validator.validateForNull(billingAddressModel.getMobile()));
                    input_email.setText(Validator.validateForNull(billingAddressModel.getEmail()));
                    input_City.setText(Validator.validateForNull(billingAddressModel.getCity()));

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
}

class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            return true;
        }
        return false;
    }
}

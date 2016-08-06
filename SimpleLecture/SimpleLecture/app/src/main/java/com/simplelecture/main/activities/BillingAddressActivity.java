package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simplelecture.main.R;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.BillingAddressModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.Validator;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billingaddress);

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Changing the action bar color
        getSupportActionBar().setTitle("Profile");

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        input_layout_FullName = (TextInputLayout) findViewById(R.id.input_layout_FullName);
        input_layout_ShippingAddress = (TextInputLayout) findViewById(R.id.input_layout_ShippingAddress);
        input_layout_State = (TextInputLayout) findViewById(R.id.input_layout_State);
        input_layout_Pincode = (TextInputLayout) findViewById(R.id.input_layout_Pincode);
        input_layout_mobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        input_layout_email = (TextInputLayout) findViewById(R.id.input_layout_email);

        input_FullName = (EditText) findViewById(R.id.input_FullName);
        input_ShippingAddress = (EditText) findViewById(R.id.input_ShippingAddress);
        input_State = (EditText) findViewById(R.id.input_State);
        input_Pincode = (EditText) findViewById(R.id.input_Pincode);
        input_mobile = (EditText) findViewById(R.id.input_mobile);
        input_email = (EditText) findViewById(R.id.input_email);

        btn_Save = (Button) findViewById(R.id.btn_Save);
        btn_Save.setOnClickListener(this);

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
            billingAddressModel.setState(input_State.getText().toString());
            billingAddressModel.setPinCode(input_Pincode.getText().toString());
            billingAddressModel.setMobile(input_mobile.getText().toString());
            billingAddressModel.setEmail(input_email.getText().toString());

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

        if(param_get_ServiceCallResult.equals(Constants.GET_BILLINGADDRESSSAVE)){
            
        }

    }

    @Override
    public void showError(String error) {

    }
}

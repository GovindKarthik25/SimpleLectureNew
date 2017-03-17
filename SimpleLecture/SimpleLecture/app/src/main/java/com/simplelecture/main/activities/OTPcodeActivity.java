package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.simplelecture.main.R;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.Validator;
import com.simplelecture.main.viewManager.ViewManager;

public class OTPcodeActivity extends AppCompatActivity implements View.OnClickListener, NetworkLayer {

    private Toolbar toolbar;
    private TextInputLayout input_layout_otpCode;
    private EditText input_OTPcode;
    private Button btn_verify;
    private TextView resendOtpTextView;
    private EditText input_MobileNo;
    private String param_get_ServiceCallResult;
    private ProgressDialog pd;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private CoordinatorLayout coordinatorLayout;
    private TextInputLayout input_layout_MobileNo;
    private String mobile;
    private String page;
    private int myCoursesCount;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpcode);

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){
            mobile = bundle.getString("mobile");
            page = bundle.getString("page");
            myCoursesCount = bundle.getInt("count");
            Log.i("mobile", mobile);
        }

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("OTP Number"));

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        input_layout_MobileNo = (TextInputLayout) findViewById(R.id.input_layout_MobileNo);
        input_layout_otpCode = (TextInputLayout) findViewById(R.id.input_layout_otpCode);
        input_OTPcode = (EditText) findViewById(R.id.input_OTPcode);
        input_MobileNo = (EditText) findViewById(R.id.input_MobileNo);
        resendOtpTextView = (TextView) findViewById(R.id.resendOtpTextView);
        resendOtpTextView.setOnClickListener(this);

        btn_verify = (Button) findViewById(R.id.btn_verify);
        btn_verify.setOnClickListener(this);

       // String mobile = Util.getFromPrefrences(this, "mobile");

        Log.v("mobile", mobile);

        if (!mobile.equals("") && mobile != null) {
            input_MobileNo.setText(mobile.toString());
        }


    }

    @Override
    public void onClick(View v) {

        if (v == btn_verify) {
            if (!Validator.validateName(this, input_OTPcode, input_layout_otpCode, "Enter the OTPCode")) {
                return;
            }

            if (new ConnectionDetector(this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_VERIFYOTP;
                pd = new Util().waitingMessage(this, "", getResources().getString(R.string.loading));
                ApiService.getApiService().doGetVerify(this, input_OTPcode.getText().toString().trim());
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }


        } else if (v == resendOtpTextView) {

            if (!Validator.validateName(this, input_MobileNo, input_layout_MobileNo, getString(R.string.err_msg_MobileNo))) {
                return;
            }

            if (new ConnectionDetector(this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_RESENDOTP;
                pd = new Util().waitingMessage(this, "", getResources().getString(R.string.loading));
                ApiService.getApiService().doGetResendOTP(this, input_MobileNo.getText().toString().trim());
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }

        }

    }

    @Override
    public void parseResponse(String response) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }

            Gson gson = new Gson();

            if (param_get_ServiceCallResult.equals(Constants.GET_USER_VERIFYOTP)) {
                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {
                    finish();
                    Toast.makeText(this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    new ViewManager().gotoLoginView(this);
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));

                }
            } else if (param_get_ServiceCallResult.equals(Constants.GET_USER_RESENDOTP)) {
                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    Toast.makeText(this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();

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

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

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, NetworkLayer {

    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText input_emailForgotPassword;
    private TextInputLayout inputLayoutEmail;
    private Button btn_ForgotPassword;
    private SnackBarManagement snack;
    private String[] param_get_ForgotPassword = new String[]{Constants.GET_FORGOTPASSWORD};
    private ProgressDialog pd;
    CoordinatorLayout coordinatorLayout;
    private String param_get_ServiceCallResult;
    private OutputResponseModel outputResponseModel;
    private AlertMessageManagement alertMessageManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.secureScreenShot(ForgotPasswordActivity.this);
        setContentView(R.layout.activity_forgotpassword);

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("Forgot Password"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        input_emailForgotPassword = (EditText) findViewById(R.id.input_emailForgotPassword);
        btn_ForgotPassword = (Button) findViewById(R.id.btn_ForgotPassword);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);


        btn_ForgotPassword.setOnClickListener(this);

    }

    /**
     * Validating form
     */
    private void submitForm() {

        if (!Validator.validateEmail(ForgotPasswordActivity.this, input_emailForgotPassword, inputLayoutEmail, getString(R.string.err_msg_email))) {
            return;
        }


        if (new ConnectionDetector(ForgotPasswordActivity.this).isConnectingToInternet()) {
            param_get_ServiceCallResult = Constants.GET_FORGOTPASSWORD;
            pd = new Util().waitingMessage(ForgotPasswordActivity.this, "", getResources().getString(R.string.loading));

            ApiService.getApiService().doGetForgotPassword(ForgotPasswordActivity.this, input_emailForgotPassword.getText().toString().trim());
        } else {
            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
        }

    }

    @Override
    public void onClick(View v) {
        if (v == btn_ForgotPassword) {
            submitForm();
        }
    }

    @Override
    public void parseResponse(String response) {
        if (pd.isShowing()) {
            pd.cancel();
        }
        Gson gson = new Gson();
        if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_FORGOTPASSWORD)) {

            outputResponseModel = gson.fromJson(response, OutputResponseModel.class);
            if (outputResponseModel.isSuccess()) {
                Toast.makeText(ForgotPasswordActivity.this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                new ViewManager().gotoLoginView(ForgotPasswordActivity.this);
            } else {
                alertMessageManagement.alertDialogActivation(ForgotPasswordActivity.this, 1, "Alert!", outputResponseModel.getMessage(), "OK", "");
            }

        }

    }

    @Override
    public void showError(String error) {
        if (pd.isShowing()) {
            pd.cancel();
        }
    }

}

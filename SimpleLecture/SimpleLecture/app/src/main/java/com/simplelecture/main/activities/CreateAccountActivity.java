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
import com.simplelecture.main.model.SignInModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.Validator;
import com.simplelecture.main.viewManager.ViewManager;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener, NetworkLayer {

    private SnackBarManagement snack;
    private Toolbar toolbar;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, input_layout_Mobile;
    private EditText inputName, inputEmail, input_Mobile, inputPassword;
    private Button btn_Signin;
    private AlertMessageManagement alertMessageManagement;
    private String param_get_ServiceCallResult = "";
    private ProgressDialog pd;
    private CoordinatorLayout coordinatorLayout;
    private OutputResponseModel outputResponseModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("SignIn"));

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        input_layout_Mobile = (TextInputLayout) findViewById(R.id.input_layout_Mobile);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        input_Mobile = (EditText) findViewById(R.id.input_Mobile);
        inputPassword = (EditText) findViewById(R.id.input_password);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        btn_Signin = (Button) findViewById(R.id.btn_Signin);

        btn_Signin.setOnClickListener(this);
    }

    /**
     * Validating form
     */
    private void submitForm() {

        if (!Validator.validateEmail(this, inputEmail, inputLayoutEmail, getString(R.string.err_msg_email))) {
            return;
        }

        if (!Validator.validateName(this, input_Mobile, input_layout_Mobile, getString(R.string.err_msg_MobileNo))) {
            return;
        }

        if (new ConnectionDetector(CreateAccountActivity.this).isConnectingToInternet()) {
            param_get_ServiceCallResult = Constants.GET_CREATEACCOUNT;
            pd = new Util().waitingMessage(CreateAccountActivity.this, "", getResources().getString(R.string.loading));

            SignInModel signInModel = new SignInModel();
            signInModel.setName(inputName.getText().toString().trim());
            signInModel.setEmail(inputEmail.getText().toString().trim());
            signInModel.setMobile(input_Mobile.getText().toString().trim());
            signInModel.setPassword(inputPassword.getText().toString().trim());

            ApiService.getApiService().doGetSignIn(CreateAccountActivity.this, signInModel);
        } else {
            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
        }

    }

    @Override
    public void onClick(View v) {
        if (v == btn_Signin) {
            submitForm();

            //new ViewManager().gotoOTPcodeView(this);
        }
    }

    @Override
    public void parseResponse(String response) {

        if (pd.isShowing()) {
            pd.cancel();
        }

        Gson gson = new Gson();

        if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_CREATEACCOUNT)) {

            outputResponseModel = gson.fromJson(response, OutputResponseModel.class);
            if (outputResponseModel.isSuccess()) {
                Toast.makeText(CreateAccountActivity.this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                new ViewManager().gotoOTPcodeView(CreateAccountActivity.this);
            } else {
                alertMessageManagement.alertDialogActivation(CreateAccountActivity.this, 1, "Alert!", outputResponseModel.getMessage(), "OK", "");
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

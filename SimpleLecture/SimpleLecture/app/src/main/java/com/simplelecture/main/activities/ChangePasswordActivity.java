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

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener, NetworkLayer {

    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText input_OldPassword, input_NewPassword, input_ConfirmPassword;
    private TextInputLayout input_layout_OldPassword, input_layout_NewPassword, input_layout_ConfirmPassword;
    private Button btn_Submit;
    private CoordinatorLayout coordinatorLayout;
    private ProgressDialog pd;
    private String param_get_ServiceCallResult;
    private SnackBarManagement snack;
    private OutputResponseModel outputResponseModel;
    private AlertMessageManagement alertMessageManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.secureScreenShot(ChangePasswordActivity.this);
        setContentView(R.layout.activity_changepassword);

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText(getSupportActionBar().getTitle().toString()));
        input_layout_OldPassword = (TextInputLayout) findViewById(R.id.input_layout_OldPassword);
        input_layout_NewPassword = (TextInputLayout) findViewById(R.id.input_layout_NewPassword);
        input_layout_ConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_ConfirmPassword);

        input_OldPassword = (EditText) findViewById(R.id.input_OldPassword);
        input_NewPassword = (EditText) findViewById(R.id.input_NewPassword);
        input_ConfirmPassword = (EditText) findViewById(R.id.input_ConfirmPassword);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);


        btn_Submit = (Button) findViewById(R.id.btn_Submit);

        btn_Submit.setOnClickListener(this);

    }

    /**
     * Validating form
     */
    private void submitForm() {

        if (!Validator.validatePassword(this, input_OldPassword, input_layout_OldPassword, getString(R.string.err_msg_OldPassword))) {
            return;
        }

        if (!Validator.validatePassword(this, input_NewPassword, input_layout_NewPassword, getString(R.string.err_msg_NewPassword))) {
            return;
        }

        if (!Validator.validatePassword(this, input_ConfirmPassword, input_layout_ConfirmPassword, getString(R.string.err_msg_ConfirmPassword))) {
            return;
        }

        if (!input_NewPassword.getText().toString().trim().toLowerCase().equals(input_ConfirmPassword.getText().toString().trim().toLowerCase())) {
            Validator.validatePassword(this, input_ConfirmPassword, input_layout_ConfirmPassword, getString(R.string.err_msg_notMatchConfirmPassword));
            input_NewPassword.setText("");
            input_ConfirmPassword.setText("");
            return;
        }

        String oldPassword = input_OldPassword.getText().toString().trim();
        String confirmPassword = input_ConfirmPassword.getText().toString().trim();

        if (new ConnectionDetector(ChangePasswordActivity.this).isConnectingToInternet()) {
            param_get_ServiceCallResult = Constants.GET_CHANGEPASSWORD;
            pd = new Util().waitingMessage(ChangePasswordActivity.this, "", getResources().getString(R.string.loading));

            ApiService.getApiService().doGetChangePassword(ChangePasswordActivity.this, oldPassword, confirmPassword);
        } else {
            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
        }

    }

    @Override
    public void onClick(View v) {
        if (v == btn_Submit) {
            submitForm();
        }
    }

    @Override
    public void parseResponse(String response) {
        if (pd.isShowing()) {
            pd.cancel();
        }

        Gson gson = new Gson();
        if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_CHANGEPASSWORD)) {
            outputResponseModel = gson.fromJson(response, OutputResponseModel.class);
            if (outputResponseModel.isSuccess()) {
                Toast.makeText(ChangePasswordActivity.this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                new ViewManager().gotoLoginView(ChangePasswordActivity.this);
            } else {
                alertMessageManagement.alertDialogActivation(ChangePasswordActivity.this, 1, "Alert!", outputResponseModel.getMessage(), "OK", "");
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

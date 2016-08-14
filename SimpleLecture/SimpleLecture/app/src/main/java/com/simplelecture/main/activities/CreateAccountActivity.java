package com.simplelecture.main.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.simplelecture.main.R;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.SignInModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SessionManager;
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

    private LoginButton facebooklogin_button;
    private CallbackManager callbackManager;

    private ConnectionResult mConnectionResult;

    private boolean mSignInClicked;

    private boolean mIntentInProgress;

    private static final String TAG = "MainActivity";

    private static final int PROFILE_PIC_SIZE = 400;


    GoogleApiClient mGoogleApiClient;

    //Signin button
    private SignInButton signInButton;

    private int RC_SIGN_IN = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                /*// Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.*/
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_createaccount);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //facebook callbackManager
        callbackManager = CallbackManager.Factory.create();

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("Signup"));

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        input_layout_Mobile = (TextInputLayout) findViewById(R.id.input_layout_Mobile);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        input_Mobile = (EditText) findViewById(R.id.input_Mobile);
        inputPassword = (EditText) findViewById(R.id.input_password);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        facebooklogin_button = (LoginButton) findViewById(R.id.login_button);
        final SessionManager sessionManager = SessionManager.getInstance();

        signInButton.setOnClickListener(googleClientListenr);

        btn_Signin = (Button) findViewById(R.id.btn_Signin);

        btn_Signin.setOnClickListener(this);

        facebooklogin_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v("onSuccess", "onSuccess");
                // If Facebook login is true Go to Homeview
                sessionManager.setLoginStatus(true);

//                new ViewManager().gotoHomeView(LoginActivity.this);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });
    }


    View.OnClickListener googleClientListenr = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pd = new Util().waitingMessage(CreateAccountActivity.this, "", getResources().getString(R.string.loading));
            signIn();

        }
    };

    private void signIn() {
        //Creating an intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            pd.cancel();
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();


            doSendGmailDetails(acct.getDisplayName(), acct.getEmail());

        } else {
            //If login fails
            pd.cancel();
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void doSendGmailDetails(final String displayName, final String email) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_otp);

       final EditText editText = (EditText) dialog.findViewById(R.id.edit_mobile);

        Button btnOk = (Button) dialog.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String number = editText.getText().toString();
                if (!number.isEmpty() && number.length() == 10) {
                    if (new ConnectionDetector(CreateAccountActivity.this).isConnectingToInternet()) {

                        dialog.cancel();
                        param_get_ServiceCallResult = Constants.GET_CREATEACCOUNT;
                        pd = new Util().waitingMessage(CreateAccountActivity.this, "", getResources().getString(R.string.loading));

                        SignInModel signInModel = new SignInModel();
                        signInModel.setName(displayName);
                        signInModel.setEmail(email);
                        signInModel.setMobile(number);
                        signInModel.setPassword("");
                        signInModel.setLoginType("G");
                        signInModel.setLoginType("Android");

                        ApiService.getApiService().doGetSignIn(CreateAccountActivity.this, signInModel);
                    } else {
                        snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                    }
                }
            }
        });

        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

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
                Util.storeToPrefrences(this, "mobile", input_Mobile.getText().toString().trim());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
    }

}

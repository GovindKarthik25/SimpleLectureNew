package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
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

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener, NetworkLayer,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{

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

    GoogleApiClient mGoogleApiClient;

    SignInButton signInButton;


    private ConnectionResult mConnectionResult;

    private boolean mSignInClicked;

    private boolean mIntentInProgress;

    private static final int RC_SIGN_IN = 0;

    private static final String TAG = "MainActivity";

    private static final int PROFILE_PIC_SIZE = 400;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                /*// Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.*/
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_createaccount);

        //facebook callbackManager
        callbackManager = CallbackManager.Factory.create();

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
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);

        facebooklogin_button = (LoginButton) findViewById(R.id.login_button);
        final SessionManager sessionManager = SessionManager.getInstance();

        signInButton.setOnClickListener(googleClientListenr);

        btn_Signin = (Button) findViewById(R.id.btn_Signin);

        btn_Signin.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).addScope(Plus.SCOPE_PLUS_PROFILE).build();

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

            signInWithGplus();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        mGoogleApiClient.connect();

    }

    private void signInWithGplus() {
        if (!mGoogleApiClient.isConnecting()) {
            mSignInClicked = true;
            resolveSignInError();
        }
    }

    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();
    }

    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = currentPerson.getDisplayName();
                String personPhotoUrl = currentPerson.getImage().getUrl();
                String personGooglePlusProfile = currentPerson.getUrl();
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

                ViewManager viewManager = new ViewManager();
                viewManager.gotoHomeView(this);

                Log.e(TAG, "Name: " + personName + ", plusProfile: "
                        + personGooglePlusProfile + ", email: " + email
                        + ", Image: " + personPhotoUrl);

//                txtName.setText(personName);
//                txtEmail.setText(email);

                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

//                new LoadProfileImage(imgProfilePic).execute(personPhotoUrl);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = connectionResult;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }
    }
}

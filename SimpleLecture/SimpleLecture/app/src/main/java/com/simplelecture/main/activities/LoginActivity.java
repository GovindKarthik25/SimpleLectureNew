package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.LoginModel;
import com.simplelecture.main.model.viewmodel.LoginResponseModel;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SessionManager;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, NetworkLayer {


    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText inputEmail, inputPassword; //inputName
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btn_Login;
  //  private TextView createAccountTextView, forgotPasswordtextView;
    private LoginButton facebooklogin_button;
    private CallbackManager callbackManager;

    GoogleApiClient mGoogleApiClient;

    SignInButton signInButton;

    private ConnectionResult mConnectionResult;

    private boolean mSignInClicked;

    private boolean mIntentInProgress;

    private static final int RC_SIGN_IN = 0;
    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
    private boolean param_get_Login = false;
    private CoordinatorLayout coordinatorLayout;
    private SnackBarManagement snack;
    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*// Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.*/
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //facebook callbackManager
      //  callbackManager = CallbackManager.Factory.create();

        snack = new SnackBarManagement(getApplicationContext());
        final SessionManager sessionManager = SessionManager.getInstance();

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        //inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btn_Login = (Button) findViewById(R.id.btn_Login);


       // createAccountTextView = (TextView) findViewById(R.id.createAccountTextView);
       // forgotPasswordtextView = (TextView) findViewById(R.id.forgotPasswordtextView);
       // facebooklogin_button = (LoginButton) findViewById(R.id.login_button);
        //signInButton = (SignInButton) findViewById(R.id.sign_in_button);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        btn_Login.setOnClickListener(this);
        // signInButton.setOnClickListener(googleClientListenr);

        //createAccountTextView.setOnClickListener(this);
        //forgotPasswordtextView.setOnClickListener(this);

       /* mGoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).addScope(Plus.SCOPE_PLUS_PROFILE).build();*/

        /*facebooklogin_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v("onSuccess", "onSuccess");
                // If Facebook login is true Go to Homeview
                sessionManager.setLoginStatus(true);

                new ViewManager().gotoHomeView(LoginActivity.this);

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();

//        mGoogleApiClient.connect();

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
        /*if (!Validator.validateName(this, inputName, inputLayoutName, getString(R.string.err_msg_name))) {
            return;
        }

        if (!Validator.validateEmail(this, inputEmail, inputLayoutEmail, getString(R.string.err_msg_email))) {
            return;
        }

        if (!Validator.validatePassword(this, inputPassword, inputLayoutPassword, getString(R.string.err_msg_password))) {
            return;
        }*/


        LoginModel loginModel = new LoginModel();
        loginModel.setUe("karthikrao19@gmail.com");
        loginModel.setUp("simple");

        if (new ConnectionDetector(LoginActivity.this).isConnectingToInternet()) {
            param_get_Login = true;
            pd = new Util().waitingMessage(LoginActivity.this, "", getResources().getString(R.string.loading));
            pd.setCanceledOnTouchOutside(false);
            //Login Service
            ApiService.getApiService().doLogin(loginModel, LoginActivity.this);
        } else {
            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
        }


    }


    View.OnClickListener googleClientListenr = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            signInWithGplus();
        }
    };

    @Override
    public void onClick(View v) {
        if (v == btn_Login) {
            submitForm();
        } /*else if (v == createAccountTextView) {
            new ViewManager().gotoCreateAccountView(this);
        } else if (v == forgotPasswordtextView) {
            new ViewManager().gotoForgotPasswordView(this);
        }*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setVisible(false);
        this.invalidateOptionsMenu();

        MenuItem item1 = menu.findItem(R.id.action_settings);
        item1.setVisible(false);
        this.invalidateOptionsMenu();

        MenuItem item2 = menu.findItem(R.id.action_changePassword);
        item2.setVisible(false);
        this.invalidateOptionsMenu();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_changePassword) {
            new ViewManager().gotoChangePasswordView(this);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information
        getProfileInformation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
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

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
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
    public void parseResponse(String response) {
        Log.i("response", "response");
        Log.i(TAG, response);
        try {
            pd.cancel();
            if (param_get_Login) {
                Gson gson = new Gson();
                JSONObject jSONObject = new JSONObject(response);
                boolean isSuccess = jSONObject.getBoolean("isSuccess");

                if (isSuccess) {
                    String dataResponse = jSONObject.getString("data");

                    LoginResponseModel loginResponseModelObj = gson.fromJson(dataResponse, LoginResponseModel.class);
                    loginResponseModelObj.setIsSuccess(isSuccess);

                    Util.storeToPrefrences(LoginActivity.this, "uId", loginResponseModelObj.getuId());
                    Util.storeToPrefrences(LoginActivity.this, "uToken", loginResponseModelObj.getuToken());
                    param_get_Login = false;
                    new ViewManager().gotoDashboardView(this);
                } else {
                    param_get_Login = false;
                }
            } else {
                param_get_Login = false;
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.loginFailed), getResources().getString(R.string.dismiss));
            }


//            new ViewManager().gotoDashboardView(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showError(String error) {
        Log.v("error", "error");
        pd.cancel();
        if (error.isEmpty()) {
            error = "Error";
        }
        snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));

    }
}

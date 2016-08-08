package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Resources;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
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
import com.simplelecture.main.util.Validator;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, NetworkLayer {


    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText inputEmail, inputPassword; //inputName
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btn_Login;
    private TextView createAccountTextView, forgotPasswordtextView;
    private LoginButton facebooklogin_button;
    private CallbackManager callbackManager;

    GoogleApiClient mGoogleApiClient;

    SignInButton gmailSign_in_button;

    private ConnectionResult mConnectionResult;

    private boolean mSignInClicked;

    private boolean mIntentInProgress;

    private static final int RC_SIGN_IN = 0;
    private static final int FB_SIGN_IN = 1;
    // Logcat tag
    private static final String TAG = "MainActivity";

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
    private boolean param_get_Login = false;
    private CoordinatorLayout coordinatorLayout;
    private SnackBarManagement snack;
    private ProgressDialog pd;
    private LoginModel loginModel;
    private SessionManager sessionManager;


    /*@Override
    public void onBackPressed() {

    }*/

    @Override
    protected void onPause() {
        super.onPause();

        if (sessionManager != null) {
            Util.storeToPrefrencesBoolean(LoginActivity.this, "loginStatus", sessionManager.isLoginStatus());
            Util.storeToPrefrencesBoolean(LoginActivity.this, "FBStatus", sessionManager.isLoginFBStatus());
            Util.storeToPrefrencesBoolean(LoginActivity.this, "GmailStatus", sessionManager.isLoginGmailStatus());
            Util.storeToPrefrencesBoolean(LoginActivity.this, "SLStatus", sessionManager.isLoginSLStatus());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Util.secureScreenShot(LoginActivity.this);

        /*// Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.*/
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_login);

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

        snack = new SnackBarManagement(LoginActivity.this);
        sessionManager = SessionManager.getInstance();

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText(getSupportActionBar().getTitle().toString()));

        //inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        //inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btn_Login = (Button) findViewById(R.id.btn_Login);
        inputEmail.setText(Util.getFromPrefrences(LoginActivity.this, "email"));


        createAccountTextView = (TextView) findViewById(R.id.createAccountTextView);
        forgotPasswordtextView = (TextView) findViewById(R.id.forgotPasswordtextView);
        facebooklogin_button = (LoginButton) findViewById(R.id.facebooklogin_button);
        gmailSign_in_button = (SignInButton) findViewById(R.id.gmailSign_in_button);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        btn_Login.setOnClickListener(this);
        gmailSign_in_button.setOnClickListener(googleClientListenr);

        createAccountTextView.setOnClickListener(this);
        forgotPasswordtextView.setOnClickListener(this);


        facebooklogin_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v("onSuccess", "onSuccess");
                // If Facebook login is true Go to Homeview
                sessionManager.setLoginStatus(true);
                sessionManager.setLoginFBStatus(true);

                Toast.makeText(LoginActivity.this, "FB Success", Toast.LENGTH_SHORT).show();

                new ViewManager().gotoHomeView(LoginActivity.this);

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "onCancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "onError", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                sessionManager.setLoginStatus(false);
                sessionManager.setLoginFBStatus(false);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();


    }


    /**
     * Validating form
     */
    private void submitForm() {
        try {
        /*if (!Validator.validateName(this, inputName, inputLayoutName, getString(R.string.err_msg_name))) {
            return;
        }*/


            if (!Validator.validateEmail(LoginActivity.this, inputEmail, inputLayoutEmail, getString(R.string.err_msg_email))) {
                return;
            }

            if (!Validator.validatePassword(LoginActivity.this, inputPassword, inputLayoutPassword, getString(R.string.err_msg_password))) {
                return;
            }

            loginModel = new LoginModel();
        /*loginModel.setUe("karthikrao19@gmail.com");
        loginModel.setUp("simple");*/

            loginModel.setUe(inputEmail.getText().toString().trim());
            loginModel.setUp(inputPassword.getText().toString().trim());

            if (new ConnectionDetector(LoginActivity.this).isConnectingToInternet()) {
                param_get_Login = true;
                pd = new Util().waitingMessage(LoginActivity.this, "", getResources().getString(R.string.loading));
                //Login Service
                ApiService.getApiService().doLogin(loginModel, LoginActivity.this);
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    View.OnClickListener googleClientListenr = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pd = new Util().waitingMessage(LoginActivity.this, "", getResources().getString(R.string.loading));
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


            doGmailLogin(acct.getEmail());

        } else {
            //If login fails
            pd.cancel();
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }

    private void doGmailLogin(String email) {


        loginModel = new LoginModel();
        /*loginModel.setUe("karthikrao19@gmail.com");
        loginModel.setUp("simple");*/

        loginModel.setUe(email);
        loginModel.setUp("");
        loginModel.setLoginType("G");
        loginModel.setLoginType("Android");

        if (new ConnectionDetector(LoginActivity.this).isConnectingToInternet()) {
            param_get_Login = true;
            pd = new Util().waitingMessage(LoginActivity.this, "", getResources().getString(R.string.loading));
            //Login Service
            ApiService.getApiService().doLogin(loginModel, LoginActivity.this);
        } else {
            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
        }
    }

    @Override
    public void onClick(View v) {
        try {
            Util.hideKeyboard(LoginActivity.this, v);
            if (v == btn_Login) {
                submitForm();
            } else if (v == createAccountTextView) {
                new ViewManager().gotoSigninView(this);
            } else if (v == forgotPasswordtextView) {
                new ViewManager().gotoForgotPasswordView(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    sessionManager.setLoginStatus(true);
                    sessionManager.setLoginSLStatus(true);

                    String dataResponse = jSONObject.getString("data");

                    LoginResponseModel loginResponseModelObj = gson.fromJson(dataResponse, LoginResponseModel.class);
                    loginResponseModelObj.setIsSuccess(isSuccess);

                    Util.storeToPrefrences(LoginActivity.this, "email", loginModel.getUe());
                    Util.storeToPrefrences(LoginActivity.this, "uId", loginResponseModelObj.getuId());
                    Util.storeToPrefrences(LoginActivity.this, "uToken", loginResponseModelObj.getuToken());
                    param_get_Login = false;
                    new ViewManager().gotoHomeView(this);
                } else {
                    param_get_Login = false;
                    snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.loginFailed), getResources().getString(R.string.dismiss));
                }
            } else {
                param_get_Login = false;
            }


//            new ViewManager().gotoDashboardView(this);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showError(String error) {
        Log.v("error", "error");
        try {
            pd.cancel();
            if (error.isEmpty()) {
                error = "Error in Login";
            }
            snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }
}

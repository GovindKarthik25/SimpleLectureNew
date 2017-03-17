package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
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
import com.simplelecture.main.model.FBUser;
import com.simplelecture.main.model.LoginModel;
import com.simplelecture.main.model.viewmodel.LoginResponseModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SessionManager;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.Validator;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, NetworkLayer {


    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText inputEmail, inputPassword; //inputName
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btn_Login;
    private TextView createAccountTextView, forgotPasswordtextView;
    private LoginButton facebooklogin_button;
    private CallbackManager callbackManager;

    public static GoogleApiClient mGoogleApiClient;

    SignInButton gmailSign_in_button;

    private ConnectionResult mConnectionResult;

    private boolean mSignInClicked;

    private boolean mIntentInProgress;

    private static final int RC_SIGN_IN = 0;
    private static final int FB_SIGN_IN = 1;

    // Profile pic image size in pixels
    private static final int PROFILE_PIC_SIZE = 400;
    private CoordinatorLayout coordinatorLayout;
    private SnackBarManagement snack;
    private ProgressDialog pd;
    private LoginModel loginModel;
    private SessionManager sessionManager;
    private String param_get_ServiceCallResult = "";
    private OutputResponseModel outputResponseModel;
    private AlertMessageManagement alertMessageManagement;
    private String number;
    private int myCoursesCount;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // new ViewManager().gotoHomeView(LoginActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (sessionManager != null && sessionManager.isLoginStatus()) {
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
        //  AppEventsLogger.activateApp(this);
        //  Util.keyHashgenrate(this);
        //facebook callbackManager
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        snack = new SnackBarManagement(LoginActivity.this);
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        sessionManager = SessionManager.getInstance();

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        setSupportActionBar(toolbar);

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("Login"));

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
        facebooklogin_button.setReadPermissions(Arrays.asList("public_profile", "email"));

        gmailSign_in_button = (SignInButton) findViewById(R.id.gmailSign_in_button);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        btn_Login.setOnClickListener(this);
        gmailSign_in_button.setOnClickListener(googleClientListenr);

        createAccountTextView.setOnClickListener(this);
        forgotPasswordtextView.setOnClickListener(this);

        // to logout FB if LoginFBStatus is False
        if (!sessionManager.isLoginFBStatus() && isLoggedIn()) {
            LoginManager.getInstance().logOut();
        }

        facebooklogin_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.v("onSuccess", "onSuccess");

                final FBUser fbUser = new FBUser();
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse graphResponse) {
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object, fbUser);

                        Log.i("LoginActivity 1", fbUser.toString());

                        doFBLogin(fbUser.getEmail());

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);

                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "onCancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
                sessionManager.setLoginStatus(false);
                sessionManager.setLoginFBStatus(false);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        } else {

            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
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
            loginModel.setLoginType(Constants.loginTypeSL);
            loginModel.setMobileOSType(Constants.android);

            callServiceLogin(loginModel);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void doFBLogin(String email) {


        loginModel = new LoginModel();
        loginModel.setUe(email);
        loginModel.setUp("");
        loginModel.setLoginType(Constants.loginTypeFB);
        loginModel.setMobileOSType(Constants.android);

        callServiceLogin(loginModel);

    }

    private void doGmailLogin(String email) {


        loginModel = new LoginModel();
        loginModel.setUe(email);
        loginModel.setUp("");
        loginModel.setLoginType(Constants.loginTypeG);
        loginModel.setMobileOSType(Constants.android);

        callServiceLogin(loginModel);

    }

    private void callServiceLogin(LoginModel loginModels) {

        if (new ConnectionDetector(LoginActivity.this).isConnectingToInternet()) {
            param_get_ServiceCallResult = Constants.GET_LOGIN;
            pd = new Util().waitingMessage(LoginActivity.this, "", getResources().getString(R.string.loading));
            //Login Service
            ApiService.getApiService().doLogin(loginModels, LoginActivity.this);
        } else {
            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
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
            sessionManager.setLoginStatus(false);
            sessionManager.setLoginGmailStatus(false);
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
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
    public void parseResponse(String response) {
        Log.i("Loginresponse-->", response);
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            Gson gson = new Gson();
            if (param_get_ServiceCallResult.equals(Constants.GET_LOGIN)) {

                JSONObject jSONObject = new JSONObject(response);
                outputResponseModel = gson.fromJson(response, OutputResponseModel.class);
                if (outputResponseModel.isSuccess()) {
                    String dataResponse = jSONObject.getString("data");
                    LoginResponseModel loginResponseModelObj = gson.fromJson(dataResponse, LoginResponseModel.class);
                    myCoursesCount = loginResponseModelObj.getMyCoursesCount();

                    Util.storeToPrefrences(LoginActivity.this, "uId", loginResponseModelObj.getuId());

                    if (!loginResponseModelObj.isMobileVerified()) {
                        doOtpDetails();
                    } else {

                        sessionManager.setLoginStatus(true);
                        if (loginModel.getLoginType().equalsIgnoreCase(Constants.loginTypeSL)) {
                            sessionManager.setLoginSLStatus(true);
                            sessionManager.setLoginFBStatus(false);
                            sessionManager.setLoginGmailStatus(false);
                        } else if (loginModel.getLoginType().equalsIgnoreCase(Constants.loginTypeFB)) {

                            sessionManager.setLoginFBStatus(true);
                            sessionManager.setLoginGmailStatus(false);
                            sessionManager.setLoginSLStatus(false);
                        } else if (loginModel.getLoginType().equalsIgnoreCase(Constants.loginTypeG)) {

                            sessionManager.setLoginFBStatus(false);
                            sessionManager.setLoginGmailStatus(true);
                            sessionManager.setLoginSLStatus(false);
                        }


                        loginResponseModelObj.setSuccess(outputResponseModel.isSuccess());

                        Util.storeToPrefrences(LoginActivity.this, "email", loginModel.getUe());
                        Util.storeToPrefrences(LoginActivity.this, "uId", loginResponseModelObj.getuId());
                        Util.storeToPrefrences(LoginActivity.this, "uToken", loginResponseModelObj.getuToken());

                        if (loginResponseModelObj.getMyCoursesCount() == 0) {
                            new ViewManager().gotoHomeView(this);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("isDashboard", true);
                            startActivity(intent);
                        }
                    }
                } else {
                    if (sessionManager.isLoginFBStatus() && isLoggedIn()) {
                        LoginManager.getInstance().logOut();
                    }
                    param_get_ServiceCallResult = "";

                    String dataResponse = jSONObject.getString("data");
                    LoginResponseModel loginResponseModelObj = gson.fromJson(dataResponse, LoginResponseModel.class);

                    if (loginResponseModelObj.getFailureType().equalsIgnoreCase("1")) {
                        //Toast.makeText(LoginActivity.this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        alertMessageManagement.alertDialogActivation(LoginActivity.this, 1, "Alert!", outputResponseModel.getMessage(), "OK", "");

                    } else if (loginResponseModelObj.getFailureType().equalsIgnoreCase("2")) {

                        Toast.makeText(LoginActivity.this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                        new ViewManager().gotoSigninView(this);
                    }

                }
            } else if (param_get_ServiceCallResult.equals(Constants.GET_USER_RESENDOTP)) {
                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {
                    Toast.makeText(this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
                    new ViewManager().gotoOTPcodeView(LoginActivity.this, "LoginActivity", number.toString().trim(), myCoursesCount);
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, outputResponseModel.getMessage(), getResources().getString(R.string.dismiss));
                }
            } else {
                param_get_ServiceCallResult = "";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showError(String error) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }

            if (sessionManager.isLoginFBStatus() && isLoggedIn()) {
                LoginManager.getInstance().logOut();
            }

            if (error.isEmpty()) {
                error = "Error in Login";
            }
            snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }


    private Bundle getFacebookData(JSONObject object, FBUser fbUser) {
        Bundle bundle = new Bundle();

        try {

            String id = object.getString("id");
            fbUser.setId(id);

           /* try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=200&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }*/

            bundle.putString("idFacebook", id);
            if (object.has("first_name")) {
                bundle.putString("first_name", object.getString("first_name"));
                String first_name = object.getString("first_name");
                fbUser.setName(first_name);
            }
            if (object.has("last_name")) {
                bundle.putString("last_name", object.getString("last_name"));
            }
            if (object.has("email")) {
                bundle.putString("email", object.getString("email"));
                String email = object.getString("email");
                fbUser.setEmail(email);
            }
           /* if (object.has("gender"))
                bundle.putString("gender", object.getString("gender"));
            if (object.has("birthday"))
                bundle.putString("birthday", object.getString("birthday"));
            if (object.has("location"))
                bundle.putString("location", object.getJSONObject("location").getString("name"));*/

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bundle;
    }

    public boolean isLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


    private void doOtpDetails() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(LoginActivity.this);
        edittext.setInputType(InputType.TYPE_CLASS_PHONE);
        edittext.setFilters(new InputFilter[] { new InputFilter.LengthFilter(11) });
        alert.setTitle("Mobile Number");
        alert.setCancelable(false);
        alert.setView(edittext);

        alert.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

                number = edittext.getText().toString().trim();
                if (!number.isEmpty() && number.length() == 11) {
                    if (new ConnectionDetector(LoginActivity.this).isConnectingToInternet()) {
                        param_get_ServiceCallResult = Constants.GET_USER_RESENDOTP;
                        pd = new Util().waitingMessage(LoginActivity.this, "", getResources().getString(R.string.loading));
                        ApiService.getApiService().doGetResendOTP(LoginActivity.this, number);
                    } else {
                        doOtpDetails();
                        snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                    }
                } else {
                    doOtpDetails();
                    Toast.makeText(LoginActivity.this, "Enter vaild mobile no", Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.show();
    }


}

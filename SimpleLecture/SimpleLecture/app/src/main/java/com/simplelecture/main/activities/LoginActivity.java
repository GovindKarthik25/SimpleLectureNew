package com.simplelecture.main.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.simplelecture.main.R;
import com.simplelecture.main.util.SessionManager;
import com.simplelecture.main.util.Validator;
import com.simplelecture.main.viewManager.ViewManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText inputName, inputEmail, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btn_Login;
    private TextView createAccountTextView, forgotPasswordtextView;
    private LoginButton facebooklogin_button;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*// Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.*/
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        //facebook callbackManager
        callbackManager = CallbackManager.Factory.create();


        final SessionManager sessionManager = SessionManager.getInstance();

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputName = (EditText) findViewById(R.id.input_name);
        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btn_Login = (Button) findViewById(R.id.btn_Login);

        createAccountTextView = (TextView) findViewById(R.id.createAccountTextView);
        forgotPasswordtextView = (TextView) findViewById(R.id.forgotPasswordtextView);
        facebooklogin_button = (LoginButton) findViewById(R.id.login_button);

        btn_Login.setOnClickListener(this);
        createAccountTextView.setOnClickListener(this);
        forgotPasswordtextView.setOnClickListener(this);

        facebooklogin_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
        });

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

        new ViewManager().gotoHomeView(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_Login) {
            submitForm();
        } else if (v == createAccountTextView) {
            new ViewManager().gotoCreateAccountView(this);
        } else if (v == forgotPasswordtextView) {
            new ViewManager().gotoForgotPasswordView(this);
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

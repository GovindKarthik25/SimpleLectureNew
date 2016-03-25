package com.simplelecture.main.activities;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.simplelecture.main.R;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.Validator;
import com.simplelecture.main.viewManager.ViewManager;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private SnackBarManagement snack;
    private Toolbar toolbar;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword, input_layout_Mobile;
    private EditText inputName, inputEmail, input_Mobile, inputPassword;
    private Button btn_Signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        snack = new SnackBarManagement(CreateAccountActivity.this);
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

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClick(View v) {
        if (v == btn_Signin) {
            //submitForm();

            new ViewManager().gotoOTPcodeView(this);
        }
    }
}

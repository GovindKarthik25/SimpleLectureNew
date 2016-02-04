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
import com.simplelecture.main.util.Validator;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText input_mobileSignIn, input_emailSignIn;
    private TextInputLayout inputLayoutMobile, inputLayoutEmail;
    private Button btn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createaccount);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutMobile = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        input_emailSignIn = (EditText) findViewById(R.id.input_emailSignIn);
        input_mobileSignIn = (EditText) findViewById(R.id.input_mobileSignIn);
        btn_signup = (Button) findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(this);

    }

        /**
         * Validating form
         */
    private void submitForm() {

        if (!Validator.validateEmail(this, input_emailSignIn, inputLayoutEmail, getString(R.string.err_msg_email))) {
            return;
        }

        if (!Validator.validateName(this, input_mobileSignIn, inputLayoutMobile, getString(R.string.err_msg_MobileNo))) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_signup) {
            submitForm();
        }
    }
}

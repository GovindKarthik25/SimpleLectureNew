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

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText input_emailForgotPassword;
    private TextInputLayout inputLayoutEmail;
    private Button btn_ForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        input_emailForgotPassword = (EditText) findViewById(R.id.input_emailForgotPassword);
        btn_ForgotPassword = (Button) findViewById(R.id.btn_ForgotPassword);

        btn_ForgotPassword.setOnClickListener(this);
    }

    /**
     * Validating form
     */
    private void submitForm() {

        if (!Validator.validateEmail(this, input_emailForgotPassword, inputLayoutEmail, getString(R.string.err_msg_email))) {
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_ForgotPassword) {
            submitForm();
        }
    }
}

package com.simplelecture.main.activities;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.simplelecture.main.R;
import com.simplelecture.main.util.Util;

public class OTPcodeActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextInputLayout input_layout_otpCode;
    private EditText input_OTPcode;
    private Button btn_verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpcode);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("OTP Number"));

        input_layout_otpCode = (TextInputLayout) findViewById(R.id.input_layout_otpCode);
        input_OTPcode = (EditText) findViewById(R.id.input_OTPcode);

        btn_verify = (Button) findViewById(R.id.btn_verify);
        btn_verify.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
}

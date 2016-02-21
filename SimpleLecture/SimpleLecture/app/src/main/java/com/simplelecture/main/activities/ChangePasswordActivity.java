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
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.Validator;

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText input_OldPassword, input_NewPassword, input_ConfirmPassword;
    private TextInputLayout input_layout_OldPassword, input_layout_NewPassword, input_layout_ConfirmPassword;
    private Button btn_Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText(getSupportActionBar().getTitle().toString()));
        input_layout_OldPassword = (TextInputLayout) findViewById(R.id.input_layout_OldPassword);
        input_layout_NewPassword = (TextInputLayout) findViewById(R.id.input_layout_NewPassword);
        input_layout_ConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_ConfirmPassword);

        input_OldPassword = (EditText) findViewById(R.id.input_OldPassword);
        input_NewPassword = (EditText) findViewById(R.id.input_NewPassword);
        input_ConfirmPassword = (EditText) findViewById(R.id.input_ConfirmPassword);

        btn_Submit = (Button) findViewById(R.id.btn_Submit);

        btn_Submit.setOnClickListener(this);

    }

    /**
     * Validating form
     */
    private void submitForm() {

        if (!Validator.validatePassword(this, input_OldPassword, input_layout_OldPassword, getString(R.string.err_msg_OldPassword))) {
            return;
        }

        if (!Validator.validatePassword(this, input_NewPassword, input_layout_NewPassword, getString(R.string.err_msg_NewPassword))) {
            return;
        }

        if (!Validator.validatePassword(this, input_ConfirmPassword, input_layout_ConfirmPassword, getString(R.string.err_msg_ConfirmPassword))) {
            return;
        }

        if(!input_NewPassword.getText().toString().trim().toLowerCase().equals(input_ConfirmPassword.getText().toString().trim().toLowerCase())) {
            Validator.validatePassword(this, input_ConfirmPassword, input_layout_ConfirmPassword, getString(R.string.err_msg_notMatchConfirmPassword));
            input_NewPassword.setText("");
            input_ConfirmPassword.setText("");
            return;
        }

        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v == btn_Submit) {
            submitForm();
        }
    }
}

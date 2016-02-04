package com.simplelecture.main.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.simplelecture.main.R;
import com.simplelecture.main.util.Validator;
import com.simplelecture.main.viewManager.ViewManager;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText inputName, inputEmail, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btn_Login;
    private TextView createAccountTextView, forgotPasswordtextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btn_Login.setOnClickListener(this);
        createAccountTextView.setOnClickListener(this);
        forgotPasswordtextView.setOnClickListener(this);
    }

    /**
     * Validating form
     */
    private void submitForm() {
        if (!Validator.validateName(this, inputName, inputLayoutName, getString(R.string.err_msg_name))) {
            return;
        }

        if (!Validator.validateEmail(this, inputEmail, inputLayoutEmail, getString(R.string.err_msg_email))) {
            return;
        }

        if (!Validator.validatePassword(this, inputPassword, inputLayoutPassword, getString(R.string.err_msg_password))) {
            return;
        }

        new ViewManager().gotoHomeView(this);
        Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
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

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    Validator.validateName(LoginActivity.this, inputName, inputLayoutName, getString(R.string.err_msg_name));
                    break;
                case R.id.input_email:
                    Validator.validateEmail(LoginActivity.this, inputEmail, inputLayoutEmail, getString(R.string.err_msg_email));
                    break;
                case R.id.input_password:
                    Validator.validatePassword(LoginActivity.this, inputPassword, inputLayoutPassword, getString(R.string.err_msg_password));
                    break;
            }
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

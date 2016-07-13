package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.simplelecture.main.R;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.Validator;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener, NetworkLayer {

    private Toolbar toolbar;
    private EditText searchEditText;
    private EditText input_emailForgotPassword;
    private TextInputLayout inputLayoutEmail;
    private Button btn_ForgotPassword;
    private SnackBarManagement snack;
    private String[] param_get_ForgotPassword = new String[]{Constants.GET_FORGOTPASSWORD};
    private ProgressDialog pd;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.secureScreenShot(ForgotPasswordActivity.this);
        setContentView(R.layout.activity_forgotpassword);

        snack = new SnackBarManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        searchEditText = (EditText) toolbar.findViewById(R.id.searchEditText);
        searchEditText.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText(getSupportActionBar().getTitle().toString()));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        input_emailForgotPassword = (EditText) findViewById(R.id.input_emailForgotPassword);
        btn_ForgotPassword = (Button) findViewById(R.id.btn_ForgotPassword);

        btn_ForgotPassword.setOnClickListener(this);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }

    /**
     * Validating form
     */
    private void submitForm() {

        if (!Validator.validateEmail(ForgotPasswordActivity.this, input_emailForgotPassword, inputLayoutEmail, getString(R.string.err_msg_email))) {
            return;
        }

        if (new ConnectionDetector(this).isConnectingToInternet()) {

            Toast.makeText(getApplicationContext(), "Thank You!", Toast.LENGTH_SHORT).show();
            pd = new Util().waitingMessage(this, "", getResources().getString(R.string.loading));

            /*if (new ConnectionDetector(ForgotPasswordActivity.this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_COURSEDETAILS;
                pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                //My HomeCoursesModel service
                ApiService.getApiService().doGetCourseDetails(getActivity(), ForgotPasswordActivity.this, courseCombosObj.getcId());
            }*/

      /*      JsonFactory jsonFactory = new JsonFactory();
            JSONObject jsonObject = jsonFactory.getForgotPwdParams("test", "test", "test", "test", "tst");
            ForgotPasswordTransaction forgotPasswordTransaction = new ForgotPasswordTransaction(jsonObject, this);
            TransactionProcessor transactionProcessor = new TransactionProcessor(this);
            transactionProcessor.execute(forgotPasswordTransaction);*/

        } else {

            snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
        }

    }

    @Override
    public void onClick(View v) {
        if (v == btn_ForgotPassword) {
            submitForm();
        }
    }

    @Override
    public void parseResponse(String response) {
        pd.cancel();

        Toast.makeText(getApplicationContext(), "Response" + response, Toast.LENGTH_LONG).show();

    }

    @Override
    public void showError(String error) {
        pd.cancel();
        Toast.makeText(getApplicationContext(), "Response" + error, Toast.LENGTH_LONG).show();
    }

    private class Connection extends AsyncTask<String, Void, String> {

        String forgotPasswordOutput;
        String email = input_emailForgotPassword.getText().toString().trim();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                if (urls[0].equals(param_get_ForgotPassword[0])) {

                   // forgotPasswordOutput = new ForgotPasswordController().getForgotPassword(email);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return urls[0];
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            try {
                pd.cancel();
                if (result.equals(param_get_ForgotPassword[0])) {
                    /* if(response!=null){
                       if(response.getStatusCode().equals("200")){



                        } else {
                            pd.cancel();


                        }

                    }*/

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}

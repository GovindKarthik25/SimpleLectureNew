package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.adapters.CheckYourAnswerAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.CheckYourAnswerResponseModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CheckyourAnswerActivity extends AppCompatActivity implements NetworkLayer {

    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private RecyclerView recyclerView_CheckYourAnswer;
    private LinearLayoutManager linearLayoutManager;
    private CheckYourAnswerAdapter checkYourAnswerAdapter;
    private String param_get_ServiceCallResult = "";
    private ProgressDialog pd;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private String testID;
    private List<CheckYourAnswerResponseModel> checkYourAnswerResponseModelList;

    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkyour_answer);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        snack = new SnackBarManagement(CheckyourAnswerActivity.this);
        alertMessageManagement = new AlertMessageManagement(CheckyourAnswerActivity.this);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("Check Your Answer"));

        Bundle bundle = getIntent().getExtras();
        testID = (String) bundle.get("testId");

        checkYourAnswerMethod();

        recyclerView_CheckYourAnswer = (RecyclerView) findViewById(R.id.recyclerView_CheckYourAnswer);


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_CheckYourAnswer.setLayoutManager(linearLayoutManager);

        if (checkYourAnswerAdapter != null) {
            checkYourAnswerAdapter = new CheckYourAnswerAdapter(CheckyourAnswerActivity.this, checkYourAnswerResponseModelList);
            recyclerView_CheckYourAnswer.setAdapter(checkYourAnswerAdapter);
        }
    }

    private void checkYourAnswerMethod() {

        try {
            if (new ConnectionDetector(CheckyourAnswerActivity.this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_QUIZ_ANSWERS;
                pd = new Util().waitingMessage(CheckyourAnswerActivity.this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetCheckYourAnswer(CheckyourAnswerActivity.this, testID);
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parseResponse(String response) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }

            Gson gson = new Gson();
            JsonArray jArray;
            JsonParser parser = new JsonParser();
            checkYourAnswerResponseModelList = new ArrayList<CheckYourAnswerResponseModel>();
            OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);


            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_QUIZ_ANSWERS)) {

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");

                    jArray = parser.parse(dataContent).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        CheckYourAnswerResponseModel checkYourAnswerResponseModelObj = gson.fromJson(obj, CheckYourAnswerResponseModel.class);
                        checkYourAnswerResponseModelList.add(checkYourAnswerResponseModelObj);
                    }

                    checkYourAnswerAdapter = new CheckYourAnswerAdapter(CheckyourAnswerActivity.this, checkYourAnswerResponseModelList);
                    recyclerView_CheckYourAnswer.setAdapter(checkYourAnswerAdapter);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showError(String error) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            if (error.equals("null") || error.isEmpty()) {
                error = "Error in connection";
            }

            snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

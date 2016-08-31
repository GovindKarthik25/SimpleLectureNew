package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.adapters.DashboardTestPaperChapterAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.DashboardQuizQuestionsResponseModel;
import com.simplelecture.main.model.viewmodel.DashboardTestPaperChapterModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.model.viewmodel.Questions;
import com.simplelecture.main.model.viewmodel.myCourses;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DashboardTestPaperChapterActivity extends AppCompatActivity implements NetworkLayer {

    private Toolbar toolbar;
    private myCourses myCoursesObj;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private ListView testPaperChapter_ListView;
    private DashboardTestPaperChapterAdapter dashboardTestPaperChapterAdapter;
    private ProgressDialog pd;
    private String param_get_ServiceCallResult = "";
    private CoordinatorLayout coordinatorLayout;
    private List<DashboardTestPaperChapterModel> dashboardTestPaperChapterModelList;
    private DashboardQuizQuestionsResponseModel dashboardQuizQuestionsResponseModelObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_test_paper_chapter);

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);


        Intent intent = getIntent();
        if (intent.hasExtra("myCoursesObj")) {
            myCoursesObj = (myCourses) intent.getSerializableExtra("myCoursesObj");

        }

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText(myCoursesObj.getcName()));

        loadGetDashboardTestPaper();

        testPaperChapter_ListView = (ListView) findViewById(R.id.testPaperChapter_ListView);

        if (dashboardTestPaperChapterAdapter != null) {
            dashboardTestPaperChapterAdapter = new DashboardTestPaperChapterAdapter(DashboardTestPaperChapterActivity.this, dashboardTestPaperChapterModelList);
            testPaperChapter_ListView.setAdapter(dashboardTestPaperChapterAdapter);
        }

        testPaperChapter_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                DashboardTestPaperChapterModel dashboardTestPaperChapterModelObj = dashboardTestPaperChapterModelList.get(position);
               // Log.i("onItemClick--->", dashboardTestPaperChapterModelObj.getCourseChapterId());

                loadGetDashboardQuizQuestions(dashboardTestPaperChapterModelObj.getCourseChapterId());
            }
        });
    }

    private void loadGetDashboardQuizQuestions(String courseChapterId) {
        try {
            if (new ConnectionDetector(this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_QUIZ_QUESTIONS;
                pd = new Util().waitingMessage(this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetDashboardUser_Quiz_Questions(DashboardTestPaperChapterActivity.this, courseChapterId);
            } else {
                alertMessageManagement.alertDialogActivation(this, 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadGetDashboardTestPaper() {
        try {
            if (new ConnectionDetector(this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_QUIZ_CHAPTERS;
                pd = new Util().waitingMessage(this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetDashboardUser_Quiz_Chapters(DashboardTestPaperChapterActivity.this, myCoursesObj.getcId());
            } else {
                alertMessageManagement.alertDialogActivation(this, 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
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
            JsonParser parser = new JsonParser();
            JsonArray jArray;
            OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);
            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_QUIZ_CHAPTERS)) {

                dashboardTestPaperChapterModelList = new ArrayList<DashboardTestPaperChapterModel>();
                if (outputResponseModel.isSuccess()) {
                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");

                    jArray = parser.parse(dataContent).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        DashboardTestPaperChapterModel dashboardTestPaperChapterModelObj = gson.fromJson(obj, DashboardTestPaperChapterModel.class);
                        dashboardTestPaperChapterModelList.add(dashboardTestPaperChapterModelObj);
                    }

                    dashboardTestPaperChapterAdapter = new DashboardTestPaperChapterAdapter(DashboardTestPaperChapterActivity.this, dashboardTestPaperChapterModelList);
                    testPaperChapter_ListView.setAdapter(dashboardTestPaperChapterAdapter);
                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_QUIZ_QUESTIONS)) {


                List<Questions> questionsList = new ArrayList<Questions>();
                if (outputResponseModel.isSuccess()) {
                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");

                    dashboardQuizQuestionsResponseModelObj = gson.fromJson(dataContent, DashboardQuizQuestionsResponseModel.class);

                    JSONObject jSONObject2 = new JSONObject(dataContent);
                    String dataContentInner = jSONObject2.getString("Questions");

                    jArray = parser.parse(dataContentInner).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        Questions questionsObj = gson.fromJson(obj, Questions.class);
                        questionsList.add(questionsObj);

                    }

                    dashboardQuizQuestionsResponseModelObj.setQuestions(questionsList);

                    new ViewManager().gotoTestPaperStartQuizActivity(DashboardTestPaperChapterActivity.this, dashboardQuizQuestionsResponseModelObj);
                 //  Log.i("QuizQuestions***", dashboardQuizQuestionsResponseModelObj.toString());
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
            if (error.isEmpty()) {
                error = "Error in connection";
            }

            snack.snackBarNotification(coordinatorLayout, 1, error, getResources().getString(R.string.dismiss));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

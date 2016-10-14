package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.adapters.DasboardTestPaperQuestionAnswerAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.Answers;
import com.simplelecture.main.model.viewmodel.DashboardQuizQuestionsResponseModel;
import com.simplelecture.main.model.viewmodel.DashboardQuizResult;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.model.viewmodel.Questions;
import com.simplelecture.main.model.viewmodel.QuizToppersModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DashboardTestPaperQuesionAnswerActivity extends AppCompatActivity implements NetworkLayer, View.OnClickListener {

    private TextView textViewLessonHeader, textViewtotalQuestion, textViewResponseTime;
    private Button buttonSubmit;
    private RecyclerView recyclerView_QuestionAnswer;
    private CoordinatorLayout coordinatorLayout;
    private DashboardQuizQuestionsResponseModel dashboardQuizQuestionsResponseModelObj;
    private DasboardTestPaperQuestionAnswerAdapter dasboardTestPaperQuestionAnswerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private CountDownTimer lessonCounterTimer;
    private static final String FORMAT = "%02d:%02d";
    private String param_get_ServiceCallResult = "";
    private ProgressDialog pd;
    private DashboardQuizResult dashboardQuizResultObj;
    private List<Answers> answerslst = new ArrayList<Answers>();
    private List<Questions> question;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_test_paper_quesion_answer);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        snack = new SnackBarManagement(DashboardTestPaperQuesionAnswerActivity.this);
        alertMessageManagement = new AlertMessageManagement(DashboardTestPaperQuesionAnswerActivity.this);

        Intent intent = getIntent();
        if (intent.hasExtra("dashboardQuizQuestionsResponseModelObj")) {
            dashboardQuizQuestionsResponseModelObj = (DashboardQuizQuestionsResponseModel) intent.getSerializableExtra("dashboardQuizQuestionsResponseModelObj");

        }


        textViewLessonHeader = (TextView) findViewById(R.id.textViewLessonHeader);
        textViewtotalQuestion = (TextView) findViewById(R.id.textViewtotalQuestion);
        textViewResponseTime = (TextView) findViewById(R.id.textViewResponseTime);
        buttonSubmit = (Button) findViewById(R.id.buttonSubmit);
        recyclerView_QuestionAnswer = (RecyclerView) findViewById(R.id.recyclerView_QuestionAnswer);


        textViewLessonHeader.setText(dashboardQuizQuestionsResponseModelObj.getCourseChapterName());
        textViewtotalQuestion.setText(dashboardQuizQuestionsResponseModelObj.getMaxQuestions());

        countLessonTimer(Long.valueOf(dashboardQuizQuestionsResponseModelObj.getMaxSeconds()));

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView_QuestionAnswer.setLayoutManager(linearLayoutManager);
        question = dashboardQuizQuestionsResponseModelObj.getQuestions();

        dasboardTestPaperQuestionAnswerAdapter = new DasboardTestPaperQuestionAnswerAdapter(DashboardTestPaperQuesionAnswerActivity.this, question);
        recyclerView_QuestionAnswer.setAdapter(dasboardTestPaperQuestionAnswerAdapter);

        buttonSubmit.setOnClickListener(this);

    }

    /**
     * Description: To count the Lesson Timer
     *
     * @param lessonTime
     */
    private void countLessonTimer(final long lessonTime) {

        long llTime = TimeUnit.SECONDS.toMillis(lessonTime);

        lessonCounterTimer = new CountDownTimer(llTime, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                textViewResponseTime.setText("" + String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            }

            public void onFinish() {
                try {
                    Log.i("Finish--->", "------------finishLesson--------->" + textViewResponseTime.getText());

                    callQuizResultMethod();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private void callQuizResultMethod() {

        try {
            if (new ConnectionDetector(DashboardTestPaperQuesionAnswerActivity.this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_QUIZ_RESULT;
                pd = new Util().waitingMessage(DashboardTestPaperQuesionAnswerActivity.this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetDashboardUser_Quiz_Result(DashboardTestPaperQuesionAnswerActivity.this, dashboardQuizQuestionsResponseModelObj.getTestId());
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void submitQuizAnswerMethod() {

        try {
            if (new ConnectionDetector(DashboardTestPaperQuesionAnswerActivity.this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_USER_QUIZ_SUBMITANSWERS;
                pd = new Util().waitingMessage(DashboardTestPaperQuesionAnswerActivity.this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doSubmitQuizAnswer(DashboardTestPaperQuesionAnswerActivity.this, dashboardQuizQuestionsResponseModelObj.getTestId(), answerslst);
            } else {
                snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == buttonSubmit) {
            Log.i("buttonSubmit**", question.toString());

            // answerslst = new ArrayList<Answers>();


           // submitQuizAnswerMethod();
        }
    }

    @Override
    public void parseResponse(String response) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            List<QuizToppersModel> quizToppersModelLstArray = new ArrayList<QuizToppersModel>();

            Gson gson = new Gson();
            JsonArray jArray;
            JsonParser parser = new JsonParser();

            OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);


            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_QUIZ_RESULT)) {

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject1 = new JSONObject(response);
                    String dataContent = jSONObject1.getString("data");

                    dashboardQuizResultObj = gson.fromJson(dataContent, DashboardQuizResult.class);

                    JSONObject jSONObject = new JSONObject(dataContent);
                    String quizToppersResponse = jSONObject.getString("QuizToppers");
                    jArray = parser.parse(quizToppersResponse).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        QuizToppersModel quizToppersModelObj = gson.fromJson(obj, QuizToppersModel.class);
                        quizToppersModelLstArray.add(quizToppersModelObj);
                    }

                    dashboardQuizResultObj.setQuizToppers(quizToppersModelLstArray);

                    lessonCounterTimer.cancel();

                    new ViewManager().gotoTestPaperResultScreenActivity(DashboardTestPaperQuesionAnswerActivity.this, dashboardQuizResultObj);
                }
            } else if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_USER_QUIZ_RESULT)) {

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

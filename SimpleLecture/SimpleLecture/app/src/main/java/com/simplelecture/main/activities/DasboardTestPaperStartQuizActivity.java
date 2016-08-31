package com.simplelecture.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.model.viewmodel.DashboardQuizQuestionsResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

public class DasboardTestPaperStartQuizActivity extends AppCompatActivity {

    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private Toolbar toolbar;
    private CoordinatorLayout coordinatorLayout;
    private TextView totalNoofQuestions_textView;
    private TextView duration_textView;
    private Button button_StartQuiz;
    private DashboardQuizQuestionsResponseModel dashboardQuizQuestionsResponseModelObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard_test_paper_start_quiz);

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        Intent intent = getIntent();
        if (intent.hasExtra("dashboardQuizQuestionsResponseModelObj")) {
            dashboardQuizQuestionsResponseModelObj = (DashboardQuizQuestionsResponseModel) intent.getSerializableExtra("dashboardQuizQuestionsResponseModelObj");

        }

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText(dashboardQuizQuestionsResponseModelObj.getCourseChapterName()));


        totalNoofQuestions_textView = (TextView) findViewById(R.id.totalNoofQuestions_textView);
        duration_textView = (TextView) findViewById(R.id.duration_textView);
        button_StartQuiz = (Button) findViewById(R.id.button_StartQuiz);

        totalNoofQuestions_textView.setText("Total No.of Questions : " + dashboardQuizQuestionsResponseModelObj.getMaxQuestions());
        int minutes = (Integer.valueOf(dashboardQuizQuestionsResponseModelObj.getMaxSeconds()) % 3600) / 60;
        duration_textView.setText("Duration : " + minutes + " Minutes");


        button_StartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ViewManager().gotoTestPaperQuesionAnswerActivity(DasboardTestPaperStartQuizActivity.this, dashboardQuizQuestionsResponseModelObj);
            }
        });
    }
}

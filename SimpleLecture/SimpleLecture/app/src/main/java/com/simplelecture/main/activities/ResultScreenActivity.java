package com.simplelecture.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.adapters.ResultScreenAdapter;
import com.simplelecture.main.model.viewmodel.DashboardQuizResult;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;

public class ResultScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView textViewYourScore;
    private Button button_CheckyourAnswer;
    private Button button_startAgain;
    private ListView listView_weeklyTopper;
    private CoordinatorLayout coordinatorLayout;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private DashboardQuizResult dashboardQuizResultObj;
    private ResultScreenAdapter resultScreenAdapter;
    private Toolbar toolbar;

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_screen);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        //Changing the action bar color
        getSupportActionBar().setTitle(Util.setActionBarText("Result"));

        snack = new SnackBarManagement(getBaseContext());
        alertMessageManagement = new AlertMessageManagement(getBaseContext());

        Intent intent = getIntent();
        if (intent.hasExtra("dashboardQuizResultObj")) {
            dashboardQuizResultObj = (DashboardQuizResult) intent.getSerializableExtra("dashboardQuizResultObj");
        }

        textViewYourScore = (TextView) findViewById(R.id.textViewYourScore);
        button_CheckyourAnswer = (Button) findViewById(R.id.button_CheckyourAnswer);
        button_startAgain = (Button) findViewById(R.id.button_startAgain);
        listView_weeklyTopper = (ListView) findViewById(R.id.listView_weeklyTopper);

        textViewYourScore.setText("Your Score is " + dashboardQuizResultObj.getQuizCorrectAnswer() + "/" + dashboardQuizResultObj.getMaxQuestions() + " from " + dashboardQuizResultObj.getQuizSeconds() + " Seconds!");


        resultScreenAdapter = new ResultScreenAdapter(ResultScreenActivity.this, dashboardQuizResultObj.getQuizToppers());
        listView_weeklyTopper.setAdapter(resultScreenAdapter);

        button_startAgain.setOnClickListener(this);
        button_CheckyourAnswer.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if(v == button_startAgain){
            finish();
            new ViewManager().gotoDashboardView(ResultScreenActivity.this, 2);
        } else if(v == button_CheckyourAnswer){

        }
    }
}

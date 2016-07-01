package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.activities.interfaces.OnItemClickListener;
import com.simplelecture.main.adapters.DemoTutorialAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.DemoTutorialResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;

import java.util.ArrayList;

public class DemoTutorialActivity extends AppCompatActivity implements NetworkLayer {

    private DemoTutorialAdapter demoTutorialAdapter;
    private ProgressDialog pd;
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private String param_get_DemoTutorial = "";
    private ArrayList<DemoTutorialResponseModel> demoTutorialResponseModelLstArray;
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_tutorial);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        snack = new SnackBarManagement(getApplicationContext());
        alertMessageManagement = new AlertMessageManagement(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(DemoTutorialActivity.this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        loadGetDemoTutorial();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadRecyclerView() {


        if (demoTutorialAdapter != null) {
            demoTutorialAdapter = new DemoTutorialAdapter(DemoTutorialActivity.this, demoTutorialResponseModelLstArray);
            recyclerView.setAdapter(demoTutorialAdapter);

            demoTutorialAdapter.setOnItemClickListener(onItemClickListener);
        }
    }

    private void loadGetDemoTutorial() {
        try {
            if (new ConnectionDetector(this).isConnectingToInternet()) {
                param_get_DemoTutorial = Constants.GET_DEMOTUTORIALS;
                pd = new Util().waitingMessage(DemoTutorialActivity.this, "", getResources().getString(R.string.loading));
                //DemoTutorial service
                ApiService.getApiService().doGetDemoTutorial(DemoTutorialActivity.this, null);
            } else {
                alertMessageManagement.alertDialogActivation(DemoTutorialActivity.this, 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            try {
                /*myCoursesObj = myCoursesLstArray.get(position);

                if (new ConnectionDetector(getActivity()).isConnectingToInternet()) {
                    param_get_DemoTutorial = true;
                    pd = new Util().waitingMessage(getActivity(), "", getResources().getString(R.string.loading));
                    //My HomeCoursesModel service
                    ApiService.getApiService().doGetCourseDetails(getActivity(), DemoTutorialFragment.this, myCoursesObj.getcId());
                } else {
                    snack.snackBarNotification(coordinatorLayout, 1, getResources().getString(R.string.noInternetConnection), getResources().getString(R.string.dismiss));
                }*/
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void parseResponse(String response) {
        try {
            pd.cancel();
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            if (param_get_DemoTutorial.equals(Constants.GET_DEMOTUTORIALS)) {

                JsonArray jarray = parser.parse(response).getAsJsonArray();

                demoTutorialResponseModelLstArray = new ArrayList<DemoTutorialResponseModel>();
                for (JsonElement obj : jarray) {
                    DemoTutorialResponseModel demoTutorialResponseModelObj = gson.fromJson(obj, DemoTutorialResponseModel.class);
                    demoTutorialResponseModelLstArray.add(demoTutorialResponseModelObj);
                }

                loadRecyclerView();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void showError(String error) {

    }
}

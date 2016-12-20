package com.simplelecture.main.splash;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.simplelecture.main.R;
import com.simplelecture.main.adapters.SplashPromoSlidePagerAdapter;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.fragments.SelectYourCoursesFragment;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.model.viewmodel.SplashResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SessionManager;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.util.ViewPagerIndicator;
import com.simplelecture.main.util.ZoomOutPageTransformer;
import com.simplelecture.main.viewManager.ViewManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity implements NetworkLayer, View.OnClickListener {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private ViewPagerIndicator pageIndicator;
    private Button buttonExplore, buttonSignin;
    private Button buttonLogin;
    private String param_get_ServiceCallResult = "";
    private ProgressDialog pd;
    private AlertMessageManagement alertMessageManagement;
    private SnackBarManagement snack;
    List<SplashResponseModel> splashResponseModelLstArray;

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }
    //android:background="@drawable/button_border"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.secureScreenShot(SplashActivity.this);

        setContentView(R.layout.activity_splash);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonExplore = (Button) findViewById(R.id.buttonExplore);
        buttonSignin = (Button) findViewById(R.id.buttonSignin);
        pageIndicator = (ViewPagerIndicator) findViewById(R.id.page_indicator);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);

        snack = new SnackBarManagement(this);
        alertMessageManagement = new AlertMessageManagement(this);

        callSplashDataService();


      /*  mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), null);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setAdapter(mPagerAdapter);
        pageIndicator.setViewPager(mPager);*/

        if(splashResponseModelLstArray != null){
            loadData();
        }

        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                pageIndicator.setViewPager(mPager);

                //  validateTheButton();
                //pageIndicator.notifyDataSetChanged();

            }
        });


        buttonExplore.setOnClickListener(this);
        buttonSignin.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }

    private void callSplashDataService() {

        try {
            if (new ConnectionDetector(this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_HOME_FLASHIMAGES;
                pd = new Util().waitingMessage(this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetSplashImage(SplashActivity.this, null);
            } else {
                alertMessageManagement.alertDialogActivation(this, 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loadData(){
        mPagerAdapter = new SplashPromoSlidePagerAdapter(getSupportFragmentManager(), splashResponseModelLstArray);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        mPager.setAdapter(mPagerAdapter);
        pageIndicator.setViewPager(mPager);

    }

    @Override
    public void onClick(View v) {
        if (v == buttonExplore) {
            String displayName = "SplashActivity";

            SessionManager sessionManager = SessionManager.getInstance();

            sessionManager.setLoginStatus(Util.getFromPrefrencesBoolean(this, "loginStatus"));
            sessionManager.setLoginFBStatus(Util.getFromPrefrencesBoolean(this, "FBStatus"));
            sessionManager.setLoginGmailStatus(Util.getFromPrefrencesBoolean(this, "GmailStatus"));
            sessionManager.setLoginSLStatus(Util.getFromPrefrencesBoolean(this, "SLStatus"));

            SelectYourCoursesFragment selectYourCoursesFragment = new SelectYourCoursesFragment().newInstance(displayName, "");
            FragmentManager fragmentManager = getSupportFragmentManager();
            selectYourCoursesFragment.show(fragmentManager, "SplashActivity");

        } else if (v == buttonSignin) {
            new ViewManager().gotoSigninView(this);
        } else if (v == buttonLogin) {
            Util.storeToPrefrences(this, "SelectYourCategoryID", "0");
            new ViewManager().gotoLoginView(this);
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

            if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_HOME_FLASHIMAGES)) {

                OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

                if (outputResponseModel.isSuccess()) {

                    JSONObject jSONObject = new JSONObject(response);

                    String dataContent = jSONObject.getString("data");

                    splashResponseModelLstArray = new ArrayList<SplashResponseModel>();

                    jArray = parser.parse(dataContent).getAsJsonArray();
                    for (JsonElement obj : jArray) {
                        SplashResponseModel splashResponseModelObj = gson.fromJson(obj, SplashResponseModel.class);
                        splashResponseModelLstArray.add(splashResponseModelObj);
                    }

                    loadData();




                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(String error) {
        if (pd.isShowing()) {
            pd.cancel();
        }

        if (error.isEmpty()) {
            error = "Error in connection";
        }

        alertMessageManagement.alertDialogActivation(this, 1, "Alert!", error, "OK", "");

    }
}

package com.simplelecture.main.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.simplelecture.main.R;
import com.simplelecture.main.util.SessionManager;
import com.simplelecture.main.util.Util;
import com.simplelecture.main.viewManager.ViewManager;


public class SplashMainScreenActivity extends AppCompatActivity {

    private TextView textViewSplash;
    private TextView textViewComplexities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main_screen);

        textViewSplash = (TextView) findViewById(R.id.textViewSplash);
        textViewComplexities = (TextView) findViewById(R.id.textViewComplexities);

        Thread timer = new Thread() {
            public void run() {
                try {
                    Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fades);
                    Animation animations = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
                    textViewSplash.startAnimation(animation);
                    textViewComplexities.startAnimation(animations);
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {

                    SessionManager sessionManager = SessionManager.getInstance();
                    sessionManager.setLoginStatus(Util.getFromPrefrencesBoolean(SplashMainScreenActivity.this, "loginStatus"));
                    sessionManager.setLoginFBStatus(Util.getFromPrefrencesBoolean(SplashMainScreenActivity.this, "FBStatus"));
                    sessionManager.setLoginGmailStatus(Util.getFromPrefrencesBoolean(SplashMainScreenActivity.this, "GmailStatus"));
                    sessionManager.setLoginSLStatus(Util.getFromPrefrencesBoolean(SplashMainScreenActivity.this, "SLStatus"));
                    Log.i("sessionManager-->", sessionManager + " - " + sessionManager.isLoginStatus() + " - " + Util.getFromPrefrences(SplashMainScreenActivity.this, "SelectYourCategoryID"));

                    if (sessionManager == null || !sessionManager.isLoginStatus() || Util.getFromPrefrences(SplashMainScreenActivity.this, "SelectYourCategoryID").equals("")) {
                        new ViewManager().gotoSplashActivity(SplashMainScreenActivity.this);
                    } else {
                        new ViewManager().gotoHomeView(SplashMainScreenActivity.this);
                    }
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();

    }

}


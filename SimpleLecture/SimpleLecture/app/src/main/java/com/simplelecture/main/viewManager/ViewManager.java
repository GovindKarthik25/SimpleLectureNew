package com.simplelecture.main.viewManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.simplelecture.main.activities.CartActivity;
import com.simplelecture.main.activities.ChangePasswordActivity;
import com.simplelecture.main.activities.ComboCourseActivity;
import com.simplelecture.main.activities.CreateAccountActivity;
import com.simplelecture.main.activities.DashboardActivity;
import com.simplelecture.main.activities.ForgotPasswordActivity;
import com.simplelecture.main.activities.HomeActivity;
import com.simplelecture.main.activities.LoginActivity;
import com.simplelecture.main.activities.OTPcodeActivity;
import com.simplelecture.main.activities.OrderSummaryActivity;
import com.simplelecture.main.activities.SingleCourseActivity;
import com.simplelecture.main.activities.VideoPlayerActivity;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;

/**
 * Created by karthik.rao on 03-02-2016.
 */
public class ViewManager {

    private Intent intent;

    public ViewManager() {
    }

    /**
     * @param activity
     * @param intent
     */
    public void setDisplay(Activity activity, Intent intent) {
        activity.startActivity(intent);
    }


    /**
     * Description: go to Login View
     *
     * @return
     */
    public Intent gotoLoginView(Activity activity) {
        intent = new Intent(activity, LoginActivity.class);
        setDisplay(activity, intent);
        activity.finish();
        return intent;
    }

    /**
     * Description: go to Create Account View
     *
     * @return
     */
    public Intent gotoSigninView(Activity activity) {
        intent = new Intent(activity, CreateAccountActivity.class);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to OTP code View
     *
     * @return
     */
    public Intent gotoOTPcodeView(Activity activity) {
        intent = new Intent(activity, OTPcodeActivity.class);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to Forgot Password View
     *
     * @return
     */
    public Intent gotoForgotPasswordView(Activity activity) {
        intent = new Intent(activity, ForgotPasswordActivity.class);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to Change Password View
     *
     * @return
     */
    public Intent gotoChangePasswordView(Activity activity) {
        intent = new Intent(activity, ChangePasswordActivity.class);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to Home View
     *
     * @return
     */
    public Intent gotoHomeView(Activity activity) {
        intent = new Intent(activity, HomeActivity.class);
        setDisplay(activity, intent);
        return intent;
    }


    public Intent gotoDashboardView(Activity activity) {
        intent = new Intent(activity, DashboardActivity.class);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to Combo Course View
     *
     * @return
     */
    public Intent gotoComboCourseView(Activity activity, CourseDetailsResponseModel courseDetailsResponseModel) {
        intent = new Intent(activity, ComboCourseActivity.class);
        intent.putExtra("courseDetails", courseDetailsResponseModel);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to Single Course View
     *
     * @return
     */
    public Intent gotoSingleCourseView(Activity activity, CourseDetailsResponseModel courseDetailsResponseModelObj) {
        intent = new Intent(activity, SingleCourseActivity.class);
        intent.putExtra("courseDetails", courseDetailsResponseModelObj);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to Video Player View
     *
     * @return
     */
    public Intent gotoVideoPlayerView(Context mContext,  String displayView, int getCtId,  String videoURL) {
        Intent intent = new Intent(mContext, VideoPlayerActivity.class);
        intent.putExtra("ctId1", getCtId);
        intent.putExtra("DisplayView", displayView);
        intent.putExtra("videoURL", videoURL);
        mContext.startActivity(intent);
        return intent;
    }


    /**
     * Description: go to gotoCartActivity
     *
     * @return
     */
    public Intent gotoCartActivity(Context mContext) {

        intent = new Intent(mContext, CartActivity.class);
        mContext.startActivity(intent);
        return intent;
    }

    /**
     * Description: go to gotoOrderSummaryActivity
     *
     * @return
     */
    public Intent gotoOrderSummaryActivity(Context mContext) {

        intent = new Intent(mContext, OrderSummaryActivity.class);
        mContext.startActivity(intent);
        return intent;
    }


}

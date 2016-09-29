package com.simplelecture.main.viewManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.simplelecture.main.activities.BillingAddressActivity;
import com.simplelecture.main.activities.CartActivity;
import com.simplelecture.main.activities.ChangePasswordActivity;
import com.simplelecture.main.activities.ComboCourseActivity;
import com.simplelecture.main.activities.CreateAccountActivity;
import com.simplelecture.main.activities.DasboardTestPaperStartQuizActivity;
import com.simplelecture.main.activities.DashboardActivity;
import com.simplelecture.main.activities.DashboardTestPaperChapterActivity;
import com.simplelecture.main.activities.DashboardTestPaperQuesionAnswerActivity;
import com.simplelecture.main.activities.EbsPaymentWebViewActivity;
import com.simplelecture.main.activities.ForgotPasswordActivity;
import com.simplelecture.main.activities.ForumWebViewActivity;
import com.simplelecture.main.activities.HomeActivity;
import com.simplelecture.main.activities.LoginActivity;
import com.simplelecture.main.activities.OTPcodeActivity;
import com.simplelecture.main.activities.OrderSummaryActivity;
import com.simplelecture.main.activities.PolicyWebview;
import com.simplelecture.main.activities.ResultScreenActivity;
import com.simplelecture.main.activities.SingleCourseActivity;
import com.simplelecture.main.activities.VideoPlayerActivity;
import com.simplelecture.main.model.viewmodel.CourseDetailsResponseModel;
import com.simplelecture.main.model.viewmodel.DashboardQuizQuestionsResponseModel;
import com.simplelecture.main.model.viewmodel.DashboardQuizResult;
import com.simplelecture.main.model.viewmodel.PlaceOrderResponseModel;
import com.simplelecture.main.model.viewmodel.myCourses;

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


    public Intent gotoDashboardView(Activity activity, int tabSelect) {
        intent = new Intent(activity, DashboardActivity.class);
        intent.putExtra("tabSelect", tabSelect);
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

    /**
     * Description: go to Create Account View
     *
     * @return
     */
    public Intent gotoBillingAddressActivityView(Activity activity, boolean containsCourseMaterial) {
        intent = new Intent(activity, BillingAddressActivity.class);
        intent.putExtra("containsCourseMaterial", containsCourseMaterial);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to Policy Webview
     *
     * @return
     */
    public Intent gotoPolicyWebview(Activity activity, String urlLink, String namePage) {
        intent = new Intent(activity, PolicyWebview.class);
        intent.putExtra("urlLink", urlLink);
        intent.putExtra("namePage", namePage);
        setDisplay(activity, intent);
        return intent;
    }


    /**
     * Description: go to TestPaper Chapter Activity
     *
     * @return
     */
    public Intent gotoTestPaperChapterActivity(Activity activity, myCourses myCoursesObj) {
        intent = new Intent(activity, DashboardTestPaperChapterActivity.class);
        intent.putExtra("myCoursesObj", myCoursesObj);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to TestPaper Start Quiz
     *
     * @return
     */
    public Intent gotoTestPaperStartQuizActivity(Activity activity, DashboardQuizQuestionsResponseModel dashboardQuizQuestionsResponseModelObj) {
        intent = new Intent(activity, DasboardTestPaperStartQuizActivity.class);
        intent.putExtra("dashboardQuizQuestionsResponseModelObj", dashboardQuizQuestionsResponseModelObj);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to TestPaper Quesion Answer
     *
     * @return
     */
    public Intent gotoTestPaperQuesionAnswerActivity(Activity activity, DashboardQuizQuestionsResponseModel dashboardQuizQuestionsResponseModelObj) {
        intent = new Intent(activity, DashboardTestPaperQuesionAnswerActivity.class);
        intent.putExtra("dashboardQuizQuestionsResponseModelObj", dashboardQuizQuestionsResponseModelObj);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to TestPaper Quesion Answer
     *
     * @return
     */
    public Intent gotoTestPaperResultScreenActivity(Activity activity, DashboardQuizResult dashboardQuizResultObj) {
        intent = new Intent(activity, ResultScreenActivity.class);
        intent.putExtra("dashboardQuizResultObj", dashboardQuizResultObj);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to Forum WebView
     *
     * @return
     */
    public Intent gotoForumWebViewActivity(Activity activity, String pageUrlForumDetails, String namePage) {
        intent = new Intent(activity, ForumWebViewActivity.class);
        intent.putExtra("urlLink", pageUrlForumDetails);
        intent.putExtra("namePage", namePage);
        setDisplay(activity, intent);
        return intent;
    }

    /**
     * Description: go to Forum WebView
     *
     * @return
     */
    public Intent gotoEBSPaymentGatewayWebViewActivity(Activity activity, PlaceOrderResponseModel placeOrderResponseModelObj) {
        intent = new Intent(activity, EbsPaymentWebViewActivity.class);
        intent.putExtra("placeOrderResponseModel", placeOrderResponseModelObj);
        setDisplay(activity, intent);
        return intent;
    }
}

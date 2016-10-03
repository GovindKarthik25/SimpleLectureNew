package com.simplelecture.main.http;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.simplelecture.main.model.Answers;
import com.simplelecture.main.model.BillingAddressModel;
import com.simplelecture.main.model.CartModel;
import com.simplelecture.main.model.LoginModel;
import com.simplelecture.main.model.SignInModel;
import com.simplelecture.main.transactions.BillingAddressGetTransaction;
import com.simplelecture.main.transactions.BillingAddressTransaction;
import com.simplelecture.main.transactions.CartAddTransaction;
import com.simplelecture.main.transactions.CartDetailsTransaction;
import com.simplelecture.main.transactions.CartRemoveTransaction;
import com.simplelecture.main.transactions.ChangeMonthTransaction;
import com.simplelecture.main.transactions.ChangePasswordTransaction;
import com.simplelecture.main.transactions.ChaptersTransaction;
import com.simplelecture.main.transactions.CheckOrderStatusTransaction;
import com.simplelecture.main.transactions.CourseCategoriesTransaction;
import com.simplelecture.main.transactions.CoursePostReviewTransaction;
import com.simplelecture.main.transactions.CoursesDetailsTransaction;
import com.simplelecture.main.transactions.DashboardExerciseCourseChapterfileTransaction;
import com.simplelecture.main.transactions.DashboardExerciseTransaction;
import com.simplelecture.main.transactions.DashboardForumCourseGetTransaction;
import com.simplelecture.main.transactions.DashboardForumCourseTransaction;
import com.simplelecture.main.transactions.DashboardMyCourseTransaction;
import com.simplelecture.main.transactions.DashboardTestPaperChapterTransaction;
import com.simplelecture.main.transactions.DashboardTestPaperQuizQuestionsTransaction;
import com.simplelecture.main.transactions.DashboardTestPaperQuizResultTransaction;
import com.simplelecture.main.transactions.DashboardTransaction;
import com.simplelecture.main.transactions.DashboardUserQuizCoursesTransaction;
import com.simplelecture.main.transactions.DemoTutorialTransaction;
import com.simplelecture.main.transactions.ForgotPasswordTransaction;
import com.simplelecture.main.transactions.HomePageDataTransaction;
import com.simplelecture.main.transactions.LoginTransaction;
import com.simplelecture.main.transactions.MyCoursesTransaction;
import com.simplelecture.main.transactions.PlaceOrderTransaction;
import com.simplelecture.main.transactions.PromoCodeTransaction;
import com.simplelecture.main.transactions.ResendOTPTransaction;
import com.simplelecture.main.transactions.SelectMyCourseTransaction;
import com.simplelecture.main.transactions.SignInTransaction;
import com.simplelecture.main.transactions.SubmitQuizAnswerTransaction;
import com.simplelecture.main.transactions.SummaryDetailsTransaction;
import com.simplelecture.main.transactions.VerifyOTPTransaction;
import com.simplelecture.main.transactions.VimeoVideoTransaction;
import com.simplelecture.main.util.JsonFactory;
import com.simplelecture.main.util.SessionManager;
import com.simplelecture.main.util.Util;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by M1032185 on 2/16/2016.
 */
public class ApiService {

    public static ApiService apiService = new ApiService();

    private ApiService() {

    }

    public static String headerToken = "";

    public static ApiService getApiService() {
        return apiService;
    }

    public void doLogin(LoginModel loginModel, Context context) {
        try {
            JsonFactory jsonFactory = new JsonFactory();
            JSONObject jsonObject = jsonFactory.getLoginParams(loginModel.getUe(), loginModel.getUp());
            LoginTransaction loginTransaction = new LoginTransaction(jsonObject, context);
            TransactionProcessor transactionProcessor = new TransactionProcessor(context);
            transactionProcessor.execute(loginTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetMyCourses(Context mContext, String uId, Fragment fragmentContext) {

        try {
            //use this to get token stored in prefrences
            String token = Util.getFromPrefrences(mContext, "uToken");

            //String token = "alc3ZXpKOE1MSWl2aVlBV25tNHlpSHlRc3N3MkYvWGFLZGRsV3FLU0QzWT06REVFS1NIQU5BSURVMTlAR01BSUwuQ09NOjYzNTkxMjM1MjY4NzE0Njg0OQ==";

            MyCoursesTransaction myCoursesTransaction = new MyCoursesTransaction(null, mContext, uId, token);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(myCoursesTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetCourseDetails(Context mContext, Fragment fragmentContext, String uId) {

        try {
            CoursesDetailsTransaction coursesDetailsTransaction = new CoursesDetailsTransaction(null, mContext, uId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(coursesDetailsTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //HomeCoursesModel index
    public void doGetChapters(Context mContext, Fragment fragmentContext, String uId) {
        try {
            String token = Util.getFromPrefrences(mContext, "uToken");

            ChaptersTransaction chaptersTransaction = new ChaptersTransaction(null, mContext, uId, token);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(chaptersTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void doAddToCart(Context mContext, CartModel cartModel) {

        try {
            headerToken = Util.getFromPrefrences(mContext, "uToken");

            String userID = Util.getFromPrefrences(mContext, "uId");
            String courseID = cartModel.getCourseID();
            String months = cartModel.getMonths();
            String courseMaterial = cartModel.getCourseMaterials();

            JsonFactory jsonFactory = new JsonFactory();
            JSONObject jsonObject = jsonFactory.getCartAdd(userID, courseID, months, courseMaterial);
            CartAddTransaction cartAddTransaction = new CartAddTransaction(jsonObject, mContext);
            TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
            transactionProcessor.execute(cartAddTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetCartDetails(Context mContext) {

        String userId = Util.getFromPrefrences(mContext, "uId");
        String token = Util.getFromPrefrences(mContext, "uToken");

        CartDetailsTransaction cartDetailsTransaction = new CartDetailsTransaction(null, mContext, userId, token);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(cartDetailsTransaction);

    }

    public void doRemoveFromCart(Context mContext, String courseId) {

        String token = Util.getFromPrefrences(mContext, "uToken");
        String userId = Util.getFromPrefrences(mContext, "uId");

        CartRemoveTransaction cartRemoveTransaction = new CartRemoveTransaction(null, mContext, userId + "/" + courseId, token);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(cartRemoveTransaction);
    }

    public void doChangeMonthFromCart(Context mContext, String courseId, String month) {

        String token = Util.getFromPrefrences(mContext, "uToken");
        String userId = Util.getFromPrefrences(mContext, "uId");

        String courseID = courseId;
        String months = month;

        JsonFactory jsonFactory = new JsonFactory();
        JSONObject jsonObject = jsonFactory.getChangeMonth(userId, courseID, months);
        ChangeMonthTransaction changeMonthTransaction = new ChangeMonthTransaction(jsonObject, mContext, token);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(changeMonthTransaction);
    }

    //Vimeo Video
    public void doGetVimeoVideoURL(Context mContext, int videoId) {

        try {
            //use this to get token stored in prefrences
            String token = Util.getFromPrefrences(mContext, "uToken");

            VimeoVideoTransaction vimeoVideoTransaction = new VimeoVideoTransaction(null, mContext, videoId, token);
            TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
            transactionProcessor.execute(vimeoVideoTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetHomeScreenData(Context mContext, Fragment fragmentContext, String uId) {

        try {
            String userId = Util.getFromPrefrences(mContext, "uId");

            if (SessionManager.getInstance().isLoginStatus() && !userId.equals("")) {
                userId = "/" + userId;
            } else {
                userId = "";
            }

            HomePageDataTransaction homePageDataTransaction = new HomePageDataTransaction(null, mContext, uId, userId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(homePageDataTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetVideoSampleTutorial(Context mContext, Fragment fragmentContext) {

        try {

            DemoTutorialTransaction demoTutorialTransaction = new DemoTutorialTransaction(null, mContext);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(demoTutorialTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetSelectYourCourse(Context mContext, Fragment fragmentContext) {

        try {

            SelectMyCourseTransaction selectMyCourseTransaction = new SelectMyCourseTransaction(null, mContext);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(selectMyCourseTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetCourseCategories(Context mContext, Fragment fragmentContext, String CId) {

        try {

            CourseCategoriesTransaction courseCategoriesTransaction = new CourseCategoriesTransaction(null, mContext, CId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(courseCategoriesTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetForgotPassword(Context mContext, String email) {

        try {

            JsonFactory jsonFactory = new JsonFactory();
            JSONObject jsonObject = jsonFactory.getForgotPwdParams(email);
            ForgotPasswordTransaction forgotPasswordTransaction = new ForgotPasswordTransaction(jsonObject, mContext);
            TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
            transactionProcessor.execute(forgotPasswordTransaction);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetChangePassword(Context mContext, String oldPassword, String confirmPassword) {

        try {

            String token = Util.getFromPrefrences(mContext, "uToken");
            String uId = Util.getFromPrefrences(mContext, "uId");

            JsonFactory jsonFactory = new JsonFactory();
            JSONObject jsonObject = jsonFactory.getChangePwdParams(uId, oldPassword, confirmPassword);
            ChangePasswordTransaction changePasswordTransaction = new ChangePasswordTransaction(jsonObject, mContext, token);
            TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
            transactionProcessor.execute(changePasswordTransaction);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetSignIn(Context mContext, SignInModel signInModel) {

        try {

            JsonFactory jsonFactory = new JsonFactory();
            JSONObject jsonObject = jsonFactory.getSignInParams(signInModel);
            SignInTransaction signInTransaction = new SignInTransaction(jsonObject, mContext);
            TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
            transactionProcessor.execute(signInTransaction);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetCoursePostReview(Context mContext, Fragment fragmentContext, String cid, String reviewText) {

        try {

            String token = Util.getFromPrefrences(mContext, "uToken");
            String uId = Util.getFromPrefrences(mContext, "uId");
            Log.i("token---->", token);
            JsonFactory jsonFactory = new JsonFactory();
            JSONObject jsonObject = jsonFactory.getCoursePostReview(uId, cid, reviewText);
            CoursePostReviewTransaction coursePostReviewTransaction = new CoursePostReviewTransaction(jsonObject, mContext, token);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(coursePostReviewTransaction);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetSummaryDetails(Context mContext) {

        String userId = Util.getFromPrefrences(mContext, "uId");
        String token = Util.getFromPrefrences(mContext, "uToken");

        SummaryDetailsTransaction summaryDetailsTransaction = new SummaryDetailsTransaction(null, mContext, userId, token);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(summaryDetailsTransaction);

    }

    public void doGetPromoCode(Context mContext, String code) {

        String userId = Util.getFromPrefrences(mContext, "uId");
        JsonFactory jsonFactory = new JsonFactory();
        JSONObject jsonObject = jsonFactory.getPromoCode(userId, code);
        PromoCodeTransaction promoCodeTransaction = new PromoCodeTransaction(jsonObject, mContext);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(promoCodeTransaction);

    }

    public void doBillingAddress(Context context, BillingAddressModel billingAddressModel) {
        try {

            String userId = Util.getFromPrefrences(context, "uId");
            billingAddressModel.setUserID(userId);
            JsonFactory jsonFactory = new JsonFactory();
            JSONObject jsonObject = jsonFactory.getBillingAddress(billingAddressModel);
            BillingAddressTransaction billingAddressTransaction = new BillingAddressTransaction(jsonObject, context);
            TransactionProcessor transactionProcessor = new TransactionProcessor(context);
            transactionProcessor.execute(billingAddressTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetBillingAddress(Context mContext) {

        String userId = Util.getFromPrefrences(mContext, "uId");

        BillingAddressGetTransaction billingAddressGetTransaction = new BillingAddressGetTransaction(null, mContext, userId);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(billingAddressGetTransaction);

    }

    public void doGetVerify(Context mContext, String code) {

        String userId = Util.getFromPrefrences(mContext, "uId");
        VerifyOTPTransaction verifyOTPTransaction = new VerifyOTPTransaction(null, mContext, userId, code);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(verifyOTPTransaction);

    }

    public void doGetResendOTP(Context mContext, String mobile) {

        String userId = Util.getFromPrefrences(mContext, "uId");
        ResendOTPTransaction resendOTPTransaction = new ResendOTPTransaction(null, mContext, userId, mobile);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(resendOTPTransaction);

    }

    public void doGetDashboardDetails(Context mContext, Fragment fragmentContext) {

        try {
            String userId = Util.getFromPrefrences(mContext, "uId");


            DashboardTransaction dashboardTransaction = new DashboardTransaction(null, mContext, userId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(dashboardTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetDashboardMyCourse(Context mContext, Fragment fragmentContext) {

        try {
            String userId = Util.getFromPrefrences(mContext, "uId");


            DashboardMyCourseTransaction dashboardMyCourseTransaction = new DashboardMyCourseTransaction(null, mContext, userId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(dashboardMyCourseTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetDashboardExercise(Context mContext, Fragment fragmentContext) {

        try {
            String userId = Util.getFromPrefrences(mContext, "uId");

            DashboardExerciseTransaction dashboardExerciseTransaction = new DashboardExerciseTransaction(null, mContext, userId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(dashboardExerciseTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetDashboardExerciseCourseChapterfile(Context mContext, Fragment fragmentContext, String courseChapterId) {

        try {
            String userId = Util.getFromPrefrences(mContext, "uId");

            DashboardExerciseCourseChapterfileTransaction dashboardExerciseCourseChapterfileTransaction = new DashboardExerciseCourseChapterfileTransaction(null, mContext, userId, courseChapterId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(dashboardExerciseCourseChapterfileTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetDashboardUSER_Quiz_Courses(Context mContext, Fragment fragmentContext) {

        try {
            String userId = Util.getFromPrefrences(mContext, "uId");

            DashboardUserQuizCoursesTransaction dashboardUserQuizCoursesTransaction = new DashboardUserQuizCoursesTransaction(null, mContext, userId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(dashboardUserQuizCoursesTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetDashboardUser_Quiz_Chapters(Context mContext, String cId) {

        DashboardTestPaperChapterTransaction dashboardTestPaperChapterTransaction = new DashboardTestPaperChapterTransaction(null, mContext, cId);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(dashboardTestPaperChapterTransaction);

    }


    public void doGetDashboardUser_Quiz_Questions(Context mContext, String CourseChapterId) {

        try {
            String userId = Util.getFromPrefrences(mContext, "uId");

            DashboardTestPaperQuizQuestionsTransaction dashboardTestPaperQuizQuestionsTransaction = new DashboardTestPaperQuizQuestionsTransaction(null, mContext, userId, CourseChapterId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
            transactionProcessor.execute(dashboardTestPaperQuizQuestionsTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doGetDashboardUser_Quiz_Result(Context mContext, String testId) {

        try {

            DashboardTestPaperQuizResultTransaction dashboardTestPaperQuizResultTransaction = new DashboardTestPaperQuizResultTransaction(null, mContext, testId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
            transactionProcessor.execute(dashboardTestPaperQuizResultTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void doSubmitQuizAnswer(Context context, String testid, List<Answers> answerslst) {
        try {

            JsonFactory jsonFactory = new JsonFactory();
            JSONObject jsonObject = jsonFactory.getSubmitQuizAnswer(answerslst);
            SubmitQuizAnswerTransaction submitQuizAnswerTransaction = new SubmitQuizAnswerTransaction(jsonObject, context, testid);
            TransactionProcessor transactionProcessor = new TransactionProcessor(context);
            transactionProcessor.execute(submitQuizAnswerTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetDashboardForumCourse(Context mContext, Fragment fragmentContext) {

        try {
            String userId = Util.getFromPrefrences(mContext, "uId");


            DashboardForumCourseTransaction dashboardForumCourseTransaction = new DashboardForumCourseTransaction(null, mContext, userId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(dashboardForumCourseTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetDashboardForumCourseGet(Context mContext, Fragment fragmentContext, String courseId) {

        try {
            String userId = Util.getFromPrefrences(mContext, "uId");


            DashboardForumCourseGetTransaction dashboardForumCourseGetTransaction = new DashboardForumCourseGetTransaction(null, mContext, userId, courseId);
            TransactionProcessor transactionProcessor = new TransactionProcessor(fragmentContext);
            transactionProcessor.execute(dashboardForumCourseGetTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doGetPlaceOrder(Context mContext, String code) {

        String userId = Util.getFromPrefrences(mContext, "uId");
        String deviceType = "Android";
        JsonFactory jsonFactory = new JsonFactory();
        JSONObject jsonObject = jsonFactory.getPlaceOrder(userId, code, deviceType);
        PlaceOrderTransaction placeOrderTransaction = new PlaceOrderTransaction(jsonObject, mContext);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(placeOrderTransaction);

    }

    public void doGetCheckOrderStatus(Context mContext, int orderId) {

        CheckOrderStatusTransaction checkOrderStatusTransaction = new CheckOrderStatusTransaction(null, mContext, orderId);
        TransactionProcessor transactionProcessor = new TransactionProcessor(mContext);
        transactionProcessor.execute(checkOrderStatusTransaction);

    }


}

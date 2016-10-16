package com.simplelecture.main.constants;

/**
 * Created by karthik.rao on 03-02-2016.
 * Description: This class is used to declare the parameters.
 */
public class Constants {

    public static String VERSION = "1.0";
    public static String android = "Android";
    public static String loginTypeSL = "SL";
    public static String loginTypeFB = "FB";
    public static String loginTypeG = "G";
    //“SL” or “FB” or “G”


    /*Database Name*/
    public static String DATABASE_NAME = "SimpleLecture.db";

    /*Database Table */
    public static String USERDETAILS_TABLE_NAME = "UserDetails";
    public static String ADDTOCART_TABLE_NAME = "cart";


    /*Api Sevices*/
    public static String GET_LOGIN = "User/Validate";
    public static String GET_FORGOTPASSWORD = "User/ForgotPassword";
    public static String GET_CREATEACCOUNT = "User/RegisterUser";
    public static String GET_CHANGEPASSWORD = "User/ChangePassword";
    public static String GET_SAMPLEVIDEOS = "Home/SampleVideos";
    public static String GET_CATEGORY_ALL = "Category/All";
    public static String GET_HOME_PAGE = "Home/Page/";
    public static String GET_COURSECATEGORIES = "Course/Search/";
    public static String GET_COURSEDETAILS = "Course/Details/";
    public static String GET_COURSECHAPTERS = "Course/Chapters/";
    public static String GET_COURSEPOSTREVIEW = "Course/PostReview";
    public static String GET_EXERCISE_FILE = "Exercise/FILE";


    /*Cart*/
    public static String GET_CART_ALL = "Cart/All/";
    public static String GET_CART_ADD = "cart/Add";
    public static String GET_CART_REMOVE = "Cart/Remove/";
    public static String GET_CART_CHANGEMONTH = "Cart/ChangeMonth";

    /*Summary*/
    public static String GET_ORDER_SUMMARY = "Order/Summary/";
    public static String GET_PROMOCODE = "Order/CheckPromocode";


    /*Profile*/
    public static String GET_BILLINGADDRESSGET = "BillingAddress/Get/";
    public static String GET_BILLINGADDRESSSAVE = "BillingAddress/Save";

    /*OTP*/
    public static String GET_USER_RESENDOTP = "User/ResendOTP/";
    public static String GET_USER_VERIFYOTP = "User/VerifyOTP/";

    /* Dashboard*/
    public static String GET_USER_DASHBOARD = "User/Dashboard/";
    public static String GET_USER_MYCOURSES = "User/MyCourses/";
    public static String GET_USER_MYEXERCISES = "User/MyExercises/";
    public static String GET_USER_COURSE_CHAPTERFILE = "course/chapterfile/";

    public static String GET_USER_QUIZ_COURSES = "Quiz/Courses/";
    public static String GET_USER_QUIZ_CHAPTERS = "Quiz/Chapters/";
    public static String GET_USER_QUIZ_QUESTIONS = "Quiz/Questions/";
    public static String GET_USER_QUIZ_RESULT = "Quiz/Result/";
    public static String GET_USER_QUIZ_SUBMITANSWERS = "Quiz/SubmitAnswers/";
    public static String GET_USER_QUIZ_ANSWERS = "Quiz/Answers/";


    public static String GET_USER_FORUMCOURSES = "Forum/Courses/";
    public static String GET_USER_FORUMGET = "Forum/Get/";

    /*EbsPayment*/
    public static String GET_ORDER_PLACEORDER = "Order/PlaceOrder";
    public static String GET_ORDER_CHECKORDERSTATUS = "Order/CheckOrderStatus/";


}

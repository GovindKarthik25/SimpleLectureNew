package com.simplelecture.main.constants;

/**
 * Created by karthik.rao on 03-02-2016.
 * Description: This class is used to declare the parameters.
 */
public class Constants {

    public static String VERSION = "1.0";

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


    /*Cart*/
    public static String GET_CART_ALL = "Cart/All/";
    public static String GET_CART_ADD = "cart/Add";
    public static String GET_CART_REMOVE = "Cart/Remove/";
    public static String GET_CART_CHANGEMONTH = "Cart/ChangeMonth";

    /*Summary*/
    public static String GET_ORDER_SUMMARY = "Order/Summary/";

    /*Profile*/
    public static String GET_BILLINGADDRESSGET = "BillingAddress/Get/";
    public static String GET_BILLINGADDRESSSAVE  = "BillingAddress/Save";


}

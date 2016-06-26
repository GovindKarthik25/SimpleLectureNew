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

    /* Default Api Service*/
    public static String SERVICE_URL = "http://204.9.137.199/api/";

    /*Api Sevices*/
    public static String GET_LOGINSIGNIN = "User/Validate";
    public static String GET_FORGOTPASSWORD = "login/signIn";
    public static String GET_CREATEACCOUNT = "login/signIn";
    public static String GET_CHANGEPASSWORD = "login/signIn";
    public static String GET_DEMOTUTORIALS = "Home/DemoTutorials";
    public static String GET_CATEGORY_ALL = "Category/All";


}

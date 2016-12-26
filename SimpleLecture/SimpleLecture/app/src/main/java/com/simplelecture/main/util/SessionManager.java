package com.simplelecture.main.util;

/**
 * Created by karthik.rao on 03-02-2016.
 */
public class SessionManager {
    private static SessionManager instance;
    private static boolean loginStatus = false;  /// default Loginstatus
    private static boolean loginFBStatus = false; /// Facebook Loginstatus
    private static boolean loginGmailStatus = false;/// Gmail Loginstatus
    private static boolean loginSLStatus = false;/// SimpleLecture Loginstatus

    static {
        try {
            instance = new SessionManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionManager getInstance() {
        if (instance == null)
            instance = new SessionManager(); //throw new Exception("No instance of SessionManager has been created!");
        return instance;
    }

    public static void setInstance(SessionManager instance) {
        SessionManager.instance = instance;
    }


    public static boolean isLoginStatus() {
        return loginStatus;
    }

    public static void setLoginStatus(boolean loginStatus) {
        SessionManager.loginStatus = loginStatus;
    }

    public static boolean isLoginFBStatus() {
        return loginFBStatus;
    }

    public static void setLoginFBStatus(boolean loginFBStatus) {
        SessionManager.loginFBStatus = loginFBStatus;
    }

    public static boolean isLoginGmailStatus() {
        return loginGmailStatus;
    }

    public static void setLoginGmailStatus(boolean loginGmailStatus) {
        SessionManager.loginGmailStatus = loginGmailStatus;
    }

    public static boolean isLoginSLStatus() {
        return loginSLStatus;
    }

    public static void setLoginSLStatus(boolean loginSLStatus) {
        SessionManager.loginSLStatus = loginSLStatus;
    }

}

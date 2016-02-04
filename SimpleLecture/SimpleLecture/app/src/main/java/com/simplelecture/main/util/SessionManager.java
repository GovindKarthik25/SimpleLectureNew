package com.simplelecture.main.util;

/**
 * Created by karthik.rao on 03-02-2016.
 */
public class SessionManager {
    private static SessionManager instance;

    static {
        try {
            instance = new SessionManager();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SessionManager getInstance() {
        if(instance == null)
            instance = new SessionManager(); //throw new Exception("No instance of SessionManager has been created!");
        return instance;
    }

    public static void setInstance(SessionManager instance) {
        SessionManager.instance = instance;
    }
}

package com.simplelecture.main.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.simplelecture.main.R;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by karthik.rao on 03-02-2016.
 */
public class Util {


    /**
     * Description: To hideKeyboard when onTouch of view.
     */
    public static void hideKeyboard(Activity activity, View view) {
        InputMethodManager in = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Description: To hideKeyboard when onTouch of view.
     */
    public static void hideKeyboard(Activity activity){
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /**
     * format float value into two decimal places
     * @param inValue
     * @return
     */
    public static String decFormat(float inValue){
        String shortString = "";
        DecimalFormat twoDec = new DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US));
        shortString = (twoDec.format(inValue));
        return shortString;
    }
    /**
     * Description : This method formats the String data empty, null into Hyphen char.
     * @param data
     * @return string data with formated.
     */
    public static String formatN2H(String data) {
        if(data == null || data.trim().equals("") || data.equals("null")){
            data = "";
        }
        return data;
    }

    /**
     * Description : Converts text password to "SHA256" Format.
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hashingSHA256(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();

        byte[] byteData = digest.digest(input.getBytes("UTF-8"));
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < byteData.length; i++){
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    /**
     * Formatting the date
     * @param value
     * @return
     */
    public static String appendZero(int value) {
        String val = value + "";
        if (val.length() == 1) {
            val = "0" + val;
        }
        return val;
    }

    /**
     * Split the url
     * @param urlHost
     * @return
     */
    public static String[] stringSeparator(String urlHost) {
        String[] separated = urlHost.split("/");
        String myFile = separated[separated.length - 1];
        String[] splitMe = myFile.split("\\.");

        return splitMe;
    }

    /**
     * @param rootView
     * @return
     */
    public static boolean isKeyboardShown(View rootView) {
		/* 128dp = 32dp * 4, minimum button height 32dp and generic 4 rows soft keyboard */
        final int SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128;

        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
		/* heightDiff = rootView height - status bar height (r.top) - visible frame height (r.bottom - r.top) */
        int heightDiff = rootView.getBottom() - r.bottom;
		/* Threshold size: dp to pixels, multiply with display density */
        boolean isKeyboardShown = heightDiff > SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density;

        //Log.d("TAG", "isKeyboardShown ? " + isKeyboardShown + ", heightDiff:" + heightDiff + ", density:" + dm.density
        //	+ "root view height:" + rootView.getHeight() + ", rect:" + r);

        return isKeyboardShown;
    }

    /**
     * @param inputFormat
     * @param outputFormat
     * @param inputDate
     * @return
     */
    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate){

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (java.text.ParseException e) {
            Log.e("TAG", "ParseException - dateFormat");
        }

        return outputDate;

    }

    public static String firstLetterToUpperCase(String convertText) {

        StringBuilder rackingSystemSb = new StringBuilder(convertText.toLowerCase());
        rackingSystemSb.setCharAt(0, Character.toUpperCase(rackingSystemSb.charAt(0)));
        convertText = rackingSystemSb.toString();

        return convertText;

    }

    /**
     * Description : waiting Message Custom.
     * @param activity
     * @param title
     * @param message
     */
    public ProgressDialogCustom waitingMessageCustom(Activity activity, String title, String message){

        ProgressDialogCustom pd = new ProgressDialogCustom(activity, R.drawable.abc_spinner_mtrl_am_alpha);
        pd.setCancelable(false);
        pd.show();
        return pd;
    }

    /**
     * Description : Waiting message.
     * @param activity
     * @param title
     * @param message
     */
    public ProgressDialog waitingMessage(Activity activity, String title, String message){
        ProgressDialog pd = new ProgressDialog(activity);
        pd.setTitle(title);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.show();
        return pd;
    }

}

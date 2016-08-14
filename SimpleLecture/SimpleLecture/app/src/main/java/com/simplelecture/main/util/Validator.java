package com.simplelecture.main.util;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import java.util.ArrayList;

public class Validator {
    public static Boolean isStringValid(String input) {
        return input != null && input.trim().length() > 0;
    }

    public static Boolean validateLength(int maxlength, String value) {
        if (isStringValid(value)) {
            int length = 0;
            try {
                length = value.length();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return (maxlength >= length) ? true : false;
        }
        return false;
    }

    public static String[] validateStringArray(String[] array) {
        ArrayList<String> list = new ArrayList<String>();
        for (String str : array)
            if (str != null && !str.equals(""))
                list.add(str);
        return list.toArray(new String[list.size()]);
    }

    public static boolean validateName(Activity activity, EditText inputName, TextInputLayout inputLayoutName, String errorMsg) {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(errorMsg);
            requestFocus(activity, inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    public static boolean validateEmail(Activity activity, EditText inputEmail, TextInputLayout inputLayoutEmail, String errorMsg) {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(errorMsg);
            requestFocus(activity, inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    public static boolean validatePassword(Activity activity, EditText inputPassword, TextInputLayout inputLayoutPassword, String errorMsg) {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(errorMsg);
            requestFocus(activity, inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    public static String validateForNull(String ChkNull) {
        if (ChkNull == null || ChkNull.equals("null")) {
            ChkNull = "";
        }
        return ChkNull;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static void requestFocus(Activity activity, View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}

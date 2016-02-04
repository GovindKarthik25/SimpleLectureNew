package com.simplelecture.main.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;

/**
 * Created by karthik.rao on 03-02-2016.
 */
public class AlertMessageManagement {

    private CustomAlertDialogListener customAlertDialogListner;
    private  Context context;
    public AlertDialog alert;
    private int alertTagOne = 1;
    private int alertTagTwo = 2;
    private int alertTagThree = 3;
    private int alertTagFour = 4;

    public AlertMessageManagement(){

    }

    public AlertMessageManagement(Context contxt){
        this.context = contxt;
    }

    public AlertMessageManagement(Context context, CustomAlertDialogListener customAlertDialogListener){
        this.context = context;
        this.customAlertDialogListner = customAlertDialogListener;
    }


    /**
     * Description : To show alert dialog
     * @param alertTag
     * @param alertTitle
     * @param message
     * @param negativeButon
     * @param positiveButon
     * @param activity
     */
    public void alertDialogActivation(final Activity activity, int alertTag, String alertTitle, String message, final String negativeButon, final String positiveButon) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setTitle(alertTitle);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setMessage(Html.fromHtml(message));
        dialogBuilder.setIcon(null);

        if(alertTag == alertTagOne) {
            dialogBuilder.setNegativeButton(negativeButon, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {  // whichButton == 2
                    if(customAlertDialogListner != null)
                    customAlertDialogListner.alertResult(dialog, whichButton);
                }
            });
        } else if(alertTag == alertTagTwo) { // 2
            dialogBuilder.setNegativeButton(negativeButon, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {   // whichButton == 2
                    if(customAlertDialogListner != null)
                        customAlertDialogListner.alertResult(dialog, whichButton);
                }
            });

            dialogBuilder.setPositiveButton(positiveButon, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {  // whichButton == 1
                    if(customAlertDialogListner != null)
                        customAlertDialogListner.alertResult(dialog, whichButton);
                }
            });
        }

        dialogBuilder.create();

        alert = dialogBuilder.show();
    }


    public interface CustomAlertDialogListener {
        void alertResult(DialogInterface dialog, int whichButton);

    }
}

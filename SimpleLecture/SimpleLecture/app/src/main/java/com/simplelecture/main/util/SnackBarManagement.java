package com.simplelecture.main.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.simplelecture.main.R;

/**
 * Created by karthik.rao on 03-02-2016.
 */
public class SnackBarManagement {

    private CustomSnackbarListener customSnackbarListner;
    private Context context;
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;


    public SnackBarManagement(){

    }

    public SnackBarManagement(Context contxt){
        this.context = contxt;
    }

    public SnackBarManagement(Context context, CustomSnackbarListener customSnackbarListener){
        this.context = context;
        this.customSnackbarListner = customSnackbarListener;
    }

    public void snackBarNotification(final Activity activity, int alertTag, String message, final String actionMsg) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.coordinatelayout, null);

        coordinatorLayout = (CoordinatorLayout) convertView.findViewById(R.id.coordinatorLayout);

        snackbar = Snackbar
                .make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction(actionMsg, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(customSnackbarListner != null) {
                            customSnackbarListner.alertResult(view);
                        } else {
                            snackbar.dismiss();
                        }
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);

        snackbar.show();
    }

    public interface CustomSnackbarListener {
        void alertResult(View view);

    }
}


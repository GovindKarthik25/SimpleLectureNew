package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.simplelecture.main.R;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.http.ApiService;
import com.simplelecture.main.http.NetworkLayer;
import com.simplelecture.main.model.viewmodel.CheckOrderStatusResponseModel;
import com.simplelecture.main.model.viewmodel.OutputResponseModel;
import com.simplelecture.main.model.viewmodel.PlaceOrderResponseModel;
import com.simplelecture.main.util.AlertMessageManagement;
import com.simplelecture.main.util.ConnectionDetector;
import com.simplelecture.main.util.SnackBarManagement;
import com.simplelecture.main.util.Util;

import org.json.JSONObject;

public class EbsPaymentWebViewActivity extends AppCompatActivity implements NetworkLayer {

    private Toolbar toolbar;
    private WebView webView;
    private ProgressDialog pd;
    private PlaceOrderResponseModel placeOrderResponseModel;
    private TextView textView_progress;
    private String param_get_ServiceCallResult = "";
    private SnackBarManagement snack;
    private AlertMessageManagement alertMessageManagement;
    private CheckOrderStatusResponseModel checkOrderStatusResponseModel;
    private Button buttonDashboard;
    private boolean buttonclick = false;
    private Intent intent;
    private final Handler handler = new Handler();

    @Override
    public void onBackPressed() {

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebs_payment);

        Bundle bundle = getIntent().getExtras();
        placeOrderResponseModel = (PlaceOrderResponseModel) bundle.get("placeOrderResponseModel");

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Util.setActionBarText("Payment"));

        snack = new SnackBarManagement(this);
        alertMessageManagement = new AlertMessageManagement(EbsPaymentWebViewActivity.this, new AlertDialogClick());


        webView = (WebView) findViewById(R.id.webView_EBSPayment);
        buttonDashboard = (Button) findViewById(R.id.buttonDashboard);
        textView_progress = (TextView) findViewById(R.id.textView_progress);
        textView_progress.setVisibility(View.GONE);
        pd = new ProgressDialog(this);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new myWebClient());
        webView.loadUrl(placeOrderResponseModel.getPaymentUrl());

        final Thread thread = new Thread() {
            public void run() {
                EbsPaymentWebViewActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        loadGetCheckOrderStatusUpdate();

                    }
                });
            }
        };

        thread.start();

        buttonDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonclick = true;
                loadGetCheckOrderStatus();
            }
        });
    }

    /**
     * Description: To disable default back button.
     */
    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            pd.setMessage(getResources().getString(R.string.loading));
            pd.setIndeterminate(true);
            pd.setCanceledOnTouchOutside(false);
            pd.setCancelable(true);
            if (!isFinishing()) {
                pd.show();
            }
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        // Here the page loading will stop so dismiss the ProgressDialog
        public void onPageFinished(WebView view, String url) {
            // this is what we should do
            pd.dismiss();
        }
    }

    private void loadGetCheckOrderStatus() {
        try {
            if (new ConnectionDetector(EbsPaymentWebViewActivity.this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_ORDER_CHECKORDERSTATUS;
                pd = new Util().waitingMessage(EbsPaymentWebViewActivity.this, "", getResources().getString(R.string.loading));

                ApiService.getApiService().doGetCheckOrderStatus(EbsPaymentWebViewActivity.this, Integer.valueOf(placeOrderResponseModel.getOrderId()));
            } else {
                alertMessageManagement.alertDialogActivation(EbsPaymentWebViewActivity.this, 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadGetCheckOrderStatusUpdate() {
        try {
            if (new ConnectionDetector(EbsPaymentWebViewActivity.this).isConnectingToInternet()) {
                param_get_ServiceCallResult = Constants.GET_ORDER_CHECKORDERSTATUS;

                ApiService.getApiService().doGetCheckOrderStatus(EbsPaymentWebViewActivity.this, Integer.valueOf(placeOrderResponseModel.getOrderId()));
            } else {
                alertMessageManagement.alertDialogActivation(EbsPaymentWebViewActivity.this, 1, "Alert!", getResources().getString(R.string.noInternetConnection), "OK", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void parseResponse(String response) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }

            Gson gson = new Gson();
            OutputResponseModel outputResponseModel = gson.fromJson(response, OutputResponseModel.class);

            if (outputResponseModel.isSuccess()) {

                JSONObject jSONObject1 = new JSONObject(response);
                String dataContent = jSONObject1.getString("data");

                if (param_get_ServiceCallResult.equalsIgnoreCase(Constants.GET_ORDER_CHECKORDERSTATUS)) {
                    checkOrderStatusResponseModel = gson.fromJson(dataContent, CheckOrderStatusResponseModel.class);

                    textView_progress.setVisibility(View.VISIBLE);

                    String orderStatus = "Payment Status: " + "<font color='black'><b>" + checkOrderStatusResponseModel.getOrderStatus() + "</b></font>"+ ". ";

                    Spanned result;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        result = Html.fromHtml(orderStatus, Html.FROM_HTML_MODE_LEGACY);
                    } else {
                        result = Html.fromHtml(orderStatus);
                    }
                    Log.i("orderStatus", response);
                    textView_progress.setText(result);
                    String orderStatusDialog = result + " To continue payment click 'Cancel' or to Cancel payment Click 'Dashboard'. ";

                    if(buttonclick) {
                        alertMessageManagement.alertDialogActivation(EbsPaymentWebViewActivity.this, 2, getResources().getString(R.string.alert), orderStatusDialog, getResources().getString(R.string.cancel), getResources().getString(R.string.dashboard));
                    }
                }
            } else {
                Toast.makeText(this, outputResponseModel.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showError(String error) {
        try {
            if (pd.isShowing()) {
                pd.cancel();
            }
            if (error.isEmpty()) {
                error = "Error in connection";
            }

            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AlertDialogClick implements AlertMessageManagement.onCustomAlertDialogListener {
        @Override
        public void onClickResult(DialogInterface dialog, int whichButton) {
            if (whichButton == -2) { // negative Button 2
                buttonclick = false;
                dialog.cancel();


            } else if (whichButton == -1) { //Postive -1
                buttonclick = false;
                finish();
                //new ViewManager().gotoDashboardView(EbsPaymentWebViewActivity.this, 0);
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                intent.putExtra("isDashboard",true);
                startActivity(intent);

            }
        }
    }


}

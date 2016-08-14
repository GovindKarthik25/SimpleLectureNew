package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.simplelecture.main.R;

/**
 * Created by Raos on 8/14/2016.
 */
public class PolicyWebview extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog pd;

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        String url = "https://www.google.co.in/";
        webView = (WebView) findViewById(R.id.webView);
        pd = new ProgressDialog(this);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.setWebViewClient(new myWebClient());
        webView.loadUrl(url);


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

}

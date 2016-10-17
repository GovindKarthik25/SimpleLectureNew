package com.simplelecture.main.activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.simplelecture.main.R;
import com.simplelecture.main.util.Util;

/**
 * Created by Raos on 8/14/2016.
 */
public class PolicyWebview extends AppCompatActivity {

    private WebView webView;
    private ProgressDialog pd;
    private String Url;
    private Toolbar toolbar;
    private String namePage;

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        Bundle bundle = getIntent().getExtras();
        Url = (String) bundle.get("urlLink");
        namePage = (String) bundle.get("namePage");

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(Util.setActionBarText(namePage));


        webView = (WebView) findViewById(R.id.webView);
        pd = new ProgressDialog(this);

        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setSupportZoom(false);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new myWebClient());
        webView.loadUrl(Url);


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

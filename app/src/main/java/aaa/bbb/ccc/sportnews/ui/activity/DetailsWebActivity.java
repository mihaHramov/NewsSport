package aaa.bbb.ccc.sportnews.ui.activity;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wang.avi.AVLoadingIndicatorView;

import aaa.bbb.ccc.sportnews.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsWebActivity extends AppCompatActivity {
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.progress)
    AVLoadingIndicatorView progress;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_web);
        ButterKnife.bind(this);
        Uri data = getIntent().getData();
        webView.loadUrl(data.toString());
        final WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.hide();
            }

        };
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
    }
}

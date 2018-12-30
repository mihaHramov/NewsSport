package aaa.bbb.ccc.sportnews.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import aaa.bbb.ccc.sportnews.R;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.Article;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsWebActivity extends AppCompatActivity {
    private static final String KEY = "KEY_OF_ARTICLE_DetailsWebActivity";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_view)
    WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_web);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        Article article = (Article) getIntent().getSerializableExtra(KEY);
        toolbar.setTitle(article.getTitle());
        webView.loadUrl(article.getUrl());
        final WebViewClient client = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        };
        webView.setWebViewClient(client);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
    }
    public static Intent getInstance(Context context, Article article){
        Intent intent = new Intent(context,DetailsWebActivity.class);
        intent.putExtra(KEY,article);
        return intent;
    }
}

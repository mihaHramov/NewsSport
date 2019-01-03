package aaa.bbb.ccc.sportnews.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.squareup.picasso.Picasso;

import aaa.bbb.ccc.sportnews.NewsApp;
import aaa.bbb.ccc.sportnews.R;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.Article;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterDetailsNews;
import aaa.bbb.ccc.sportnews.mvp.view.ViewDetailsNews;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends MvpAppCompatActivity implements ViewDetailsNews {
    @BindView(R.id.content)
    TextView content;

    @OnClick(R.id.read_in_the_original_source)
    void readInSource(){
        presenterDetailsNews.clickShowNewsInSource();
    }
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.image)
    KenBurnsView image;

    @InjectPresenter
    PresenterDetailsNews presenterDetailsNews;

    @ProvidePresenter
    PresenterDetailsNews providePresenterDetailsNews() {
        presenterDetailsNews = NewsApp.getNewsDetailsComponent().getPresenter();
        presenterDetailsNews.init((Article) (getIntent().getSerializableExtra(KEY)));
        return presenterDetailsNews;
    }

    private static final String KEY = DetailsActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

    }

    public static void showDetailsNews(Context context, Article article) {
        Intent i = new Intent(context, DetailsActivity.class);
        i.putExtra(KEY, article);
        context.startActivity(i);
    }

    @Override
    public void initView(Article article) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(article.getTitle());
        }
        content.setText(article.getContent());
        Picasso.get().load(article.getUrlToImage()).into(image);
    }

    @Override
    public void showInSource(Article article) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
        startActivity(intent);
    }
}

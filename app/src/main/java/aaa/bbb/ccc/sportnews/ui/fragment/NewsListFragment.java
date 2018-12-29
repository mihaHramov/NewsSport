package aaa.bbb.ccc.sportnews.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import aaa.bbb.ccc.sportnews.NewsApp;
import aaa.bbb.ccc.sportnews.R;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsListFragment;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsListFragment;
import aaa.bbb.ccc.sportnews.pojo.Article;
import aaa.bbb.ccc.sportnews.ui.activity.DetailsWebActivity;
import aaa.bbb.ccc.sportnews.ui.adapter.ArticleAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsListFragment extends BaseNewsListFragment implements ViewNewsListFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    AVLoadingIndicatorView progressBar;
    @BindView(R.id.empty_list)
    TextView emptyList;
    private ArticleAdapter adapter;

    @InjectPresenter
    PresenterNewsListFragment presenterNewsListFragment;

    @ProvidePresenter
    PresenterNewsListFragment providePresenter() {
        return NewsApp.getNewsListComponent().getPresenter();
    }

    @Override
    public void displayNews(String tag) {
        presenterNewsListFragment.init(tag);
    }

    public NewsListFragment() {
    }

    @Override
    public void showNews(List<Article> news) {
        adapter.setList(news);
    }

    @Override
    public void loading(Boolean b) {
        if (b)
            progressBar.show();
        else
            progressBar.hide();
    }

    @Override
    public void showError(Boolean isShow) {
        emptyList.setVisibility(isShow?View.VISIBLE:View.GONE);
        recyclerView.setVisibility(isShow?View.GONE:View.VISIBLE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);
        ButterKnife.bind(this, v);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        NewsApp.getNewsListComponent().inject(this);
        adapter = new ArticleAdapter(article -> startActivity(DetailsWebActivity.getInstance(getActivity(), article)));
        recyclerView.setAdapter(adapter);
        return v;
    }
}

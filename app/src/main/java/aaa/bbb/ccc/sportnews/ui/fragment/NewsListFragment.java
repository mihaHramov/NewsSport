package aaa.bbb.ccc.sportnews.ui.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.squareup.otto.Subscribe;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import javax.inject.Inject;

import aaa.bbb.ccc.sportnews.NewsApp;
import aaa.bbb.ccc.sportnews.R;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.Article;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.NewsSource;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsListFragment;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsListFragment;
import aaa.bbb.ccc.sportnews.ui.activity.DetailsActivity;
import aaa.bbb.ccc.sportnews.ui.adapter.ArticleAdapter;
import aaa.bbb.ccc.sportnews.ui.util.BusProvider;
import aaa.bbb.ccc.sportnews.ui.util.ChangeNetworkConnect;
import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsListFragment extends MvpAppCompatFragment implements ViewNewsListFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    AVLoadingIndicatorView progressBar;
    @BindView(R.id.empty_list)
    TextView emptyList;
    @Inject
    ArticleAdapter adapter;

    private static final String KEY = NewsListFragment.class.getName();
    @InjectPresenter
    PresenterNewsListFragment presenterNewsListFragment;

    @ProvidePresenter
    PresenterNewsListFragment providePresenter() {
        presenterNewsListFragment = NewsApp.getNewsListComponent().getPresenter();
        presenterNewsListFragment.init((NewsSource) getArguments().getSerializable(KEY));
        return presenterNewsListFragment;
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
        emptyList.setVisibility(isShow ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    public static NewsListFragment newInstance(NewsSource source) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY, source);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);
        ButterKnife.bind(this, v);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        NewsApp.getNewsListComponent().inject(this);
        adapter.setOnItemClick(article -> DetailsActivity.showDetailsNews(getActivity(), article));
        recyclerView.setAdapter(adapter);
        BusProvider.getInstance().register(this);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void changeNetworkConnect (ChangeNetworkConnect connect){
        presenterNewsListFragment.changeNetwork(connect.getConnect());
    }


}
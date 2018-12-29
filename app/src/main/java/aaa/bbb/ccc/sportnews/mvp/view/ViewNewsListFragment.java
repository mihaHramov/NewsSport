package aaa.bbb.ccc.sportnews.mvp.view;

import com.arellomobile.mvp.MvpView;

import java.util.List;

import aaa.bbb.ccc.sportnews.pojo.Article;

public interface ViewNewsListFragment extends MvpView {
    void showNews(List<Article> news);
    void loading(Boolean b);
    void showError(Boolean isShow);
}

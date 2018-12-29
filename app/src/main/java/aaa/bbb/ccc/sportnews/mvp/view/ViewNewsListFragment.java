package aaa.bbb.ccc.sportnews.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import aaa.bbb.ccc.sportnews.pojo.Article;
@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface ViewNewsListFragment extends MvpView {
    void showNews(List<Article> news);
    void loading(Boolean b);
    void showError(Boolean isShow);
}

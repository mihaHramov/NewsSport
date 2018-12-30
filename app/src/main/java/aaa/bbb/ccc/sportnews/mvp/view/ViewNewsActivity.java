package aaa.bbb.ccc.sportnews.mvp.view;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.pojo.NewsSource;


public interface ViewNewsActivity extends MvpView {
    void showMenu(List<NewsSource> globalSources);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void selectItemMenu(Integer id);

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showNews(NewsSource string);

    void showError(Boolean bool);
}

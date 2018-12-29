package aaa.bbb.ccc.sportnews.mvp.view;


import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.Source;


public interface ViewNewsActivity extends MvpView {
    void showMenu(List<Source> globalSources);

    @StateStrategyType(value = AddToEndSingleStrategy.class)
    void selectItemMenu(Integer id);

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showNews(Source string);

    void showError(Boolean bool);
}

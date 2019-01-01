package aaa.bbb.ccc.sportnews.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import aaa.bbb.ccc.sportnews.mvp.model.pojo.Article;

public interface ViewDetailsNews extends MvpView {
    void initView(Article article);
    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void showInSource(Article article);
}

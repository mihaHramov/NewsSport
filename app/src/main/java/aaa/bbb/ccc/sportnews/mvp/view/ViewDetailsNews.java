package aaa.bbb.ccc.sportnews.mvp.view;

import com.arellomobile.mvp.MvpView;

import aaa.bbb.ccc.sportnews.mvp.model.pojo.Article;

public interface ViewDetailsNews extends MvpView {
    void initView(Article article);
}

package aaa.bbb.ccc.sportnews.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import aaa.bbb.ccc.sportnews.mvp.model.pojo.Article;
import aaa.bbb.ccc.sportnews.mvp.view.ViewDetailsNews;

@InjectViewState
public class PresenterDetailsNews extends MvpPresenter<ViewDetailsNews> {
    private Article article;
    public void init(Article article){
        this.article = article;
        getViewState().initView(article);
    }
    public void clickShowNewsInSource(){
        getViewState().showInSource(article);
    }
}

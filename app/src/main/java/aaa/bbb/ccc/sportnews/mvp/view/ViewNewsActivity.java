package aaa.bbb.ccc.sportnews.mvp.view;


import com.arellomobile.mvp.MvpView;

import java.util.List;

import aaa.bbb.ccc.sportnews.pojo.Article;
import aaa.bbb.ccc.sportnews.pojo.GlobalSource;


public interface ViewNewsActivity  extends MvpView{
    void showNews(List<Article> news);
    void loading(Boolean b);
    void showErrorEmptyList();
    void clearError();
    void showMenu(List<GlobalSource> globalSources);
    void selectItemMenu(Integer id);
}

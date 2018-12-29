package aaa.bbb.ccc.sportnews.mvp.view;


import com.arellomobile.mvp.MvpView;

import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.Source;


public interface ViewNewsActivity  extends MvpView{
    void showMenu(List<Source> globalSources);
    void selectItemMenu(Integer id);
    void showNews(Source string);
}

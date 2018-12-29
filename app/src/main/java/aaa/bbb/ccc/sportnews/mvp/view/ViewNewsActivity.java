package aaa.bbb.ccc.sportnews.mvp.view;


import com.arellomobile.mvp.MvpView;

import java.util.List;

import aaa.bbb.ccc.sportnews.pojo.GlobalSource;


public interface ViewNewsActivity  extends MvpView{
    void showMenu(List<GlobalSource> globalSources);
    void selectItemMenu(Integer id);
    void showNews(String string);
}

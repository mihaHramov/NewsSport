package aaa.bbb.ccc.sportnews.ui.fragment;

import com.arellomobile.mvp.MvpAppCompatFragment;

import aaa.bbb.ccc.sportnews.mvp.model.pojo.NewsSource;

public abstract class BaseNewsListFragment extends MvpAppCompatFragment {
    abstract public void displayNews(NewsSource tag);
}

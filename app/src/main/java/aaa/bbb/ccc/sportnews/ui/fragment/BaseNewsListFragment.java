package aaa.bbb.ccc.sportnews.ui.fragment;

import com.arellomobile.mvp.MvpAppCompatFragment;

import aaa.bbb.ccc.sportnews.mvp.model.Source;

public abstract class BaseNewsListFragment extends MvpAppCompatFragment {
    abstract public void displayNews(Source tag);
}

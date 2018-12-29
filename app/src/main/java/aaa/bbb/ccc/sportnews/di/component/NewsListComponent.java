package aaa.bbb.ccc.sportnews.di.component;

import aaa.bbb.ccc.sportnews.di.module.NewsListModule;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsListFragment;
import aaa.bbb.ccc.sportnews.ui.fragment.NewsListFragment;
import dagger.Subcomponent;

@Subcomponent(modules = NewsListModule.class)
public interface NewsListComponent {
    PresenterNewsListFragment getPresenter();
    void inject(NewsListFragment fragment);
}

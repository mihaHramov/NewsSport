package aaa.bbb.ccc.sportnews.di.component;

import aaa.bbb.ccc.sportnews.di.module.NewsListModule;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsActivity;
import aaa.bbb.ccc.sportnews.ui.activity.NewsActivity;
import dagger.Subcomponent;

@Subcomponent(modules = NewsListModule.class)
public interface NewsListComponent {
    PresenterNewsActivity getPresenter();
    void inject(NewsActivity activity);
}

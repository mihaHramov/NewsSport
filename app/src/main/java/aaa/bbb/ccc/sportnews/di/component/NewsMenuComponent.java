package aaa.bbb.ccc.sportnews.di.component;

import aaa.bbb.ccc.sportnews.di.module.NewsMenuModule;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsActivity;
import aaa.bbb.ccc.sportnews.ui.activity.NewsActivity;
import dagger.Subcomponent;

@Subcomponent(modules = NewsMenuModule.class)
public interface NewsMenuComponent {
    PresenterNewsActivity getPresenter();
    void inject(NewsActivity activity);
}

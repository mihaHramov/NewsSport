package aaa.bbb.ccc.sportnews.di.component;

import aaa.bbb.ccc.sportnews.di.module.DetailsNewsModule;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterDetailsNews;
import dagger.Subcomponent;

@Subcomponent(modules = DetailsNewsModule.class)
public interface NewsDetailsComponent {
    PresenterDetailsNews getPresenter();
}

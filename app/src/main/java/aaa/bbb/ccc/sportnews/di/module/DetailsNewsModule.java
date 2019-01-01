package aaa.bbb.ccc.sportnews.di.module;

import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterDetailsNews;
import dagger.Module;
import dagger.Provides;

@Module
public class DetailsNewsModule {
    @Provides
    PresenterDetailsNews presenterDetailsNews() {
        return new PresenterDetailsNews();
    }
}

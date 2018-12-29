package aaa.bbb.ccc.sportnews.di.module;

import javax.inject.Named;

import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsActivity;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class NewsMenuModule {
    @Provides
    PresenterNewsActivity getPresenter(@Named("uiScheduler") Scheduler ui,
                                       @Named("backgroundScheduler") Scheduler background,
                                       IRepositoryOfNews repositoryOfNews) {
        return new PresenterNewsActivity.Builder()
                .setRepository(repositoryOfNews)
                .setThreadUI(ui)
                .setThreadBackground(background)
                .build();
    }
}

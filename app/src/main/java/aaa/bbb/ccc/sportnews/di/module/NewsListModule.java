package aaa.bbb.ccc.sportnews.di.module;

import javax.inject.Named;

import aaa.bbb.ccc.sportnews.mvp.model.ILocalStorage;
import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsListFragment;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class NewsListModule {

    @Provides
    PresenterNewsListFragment getPresenter(@Named("uiScheduler") Scheduler ui,
                                           @Named("backgroundScheduler") Scheduler background,
                                           IRepositoryOfNews repositoryOfNews,
                                           ILocalStorage localStarage) {
        return new PresenterNewsListFragment.Builder()
                .setRepository(repositoryOfNews)
                .setLocalStorage(localStarage)
                .setThreadUI(ui)
                .setThreadBackground(background)
                .build();
    }

}

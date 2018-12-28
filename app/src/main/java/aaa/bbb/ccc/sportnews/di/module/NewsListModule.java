package aaa.bbb.ccc.sportnews.di.module;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Named;

import aaa.bbb.ccc.sportnews.mvp.model.ILocalStarage;
import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsActivity;
import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

@Module
public class NewsListModule {

    @Provides
    PresenterNewsActivity getPresenter(@Named("uiScheduler") Scheduler ui,
                                       @Named("backgroundScheduler") Scheduler background,
                                       IRepositoryOfNews repositoryOfNews,
                                       ILocalStarage localStarage) {
        return new PresenterNewsActivity.Builder()
                .setRepository(repositoryOfNews)
                .setLocalStorage(localStarage)
                .setThreadUI(ui)
                .setThreadBackground(background)
                .build();
    }

}

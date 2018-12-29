package aaa.bbb.ccc.sportnews.di.module;

import android.content.Context;

import javax.inject.Singleton;

import aaa.bbb.ccc.sportnews.api.NyNewsApi;
import aaa.bbb.ccc.sportnews.mvp.model.ILocalStorage;
import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.model.RepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.model.StorageDB;
import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    @Singleton
    IRepositoryOfNews provideRepositoryOfNews(NyNewsApi api,ILocalStorage localStorage) {
        return new RepositoryOfNews(api);
    }

    @Provides
    @Singleton
    ILocalStorage provideLocalRepository(Context context) {
        return new StorageDB(context);
    }
}

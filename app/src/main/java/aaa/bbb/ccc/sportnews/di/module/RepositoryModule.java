package aaa.bbb.ccc.sportnews.di.module;

import android.content.Context;

import aaa.bbb.ccc.sportnews.mvp.model.ILocalStarage;
import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.model.RepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.model.StorageDB;
import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {
    @Provides
    IRepositoryOfNews provideRepositoryOfNews() {
        return new RepositoryOfNews();
    }

    @Provides
    ILocalStarage provideLocalRepository(Context context) {
        return new StorageDB(context);
    }
}

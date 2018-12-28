package aaa.bbb.ccc.sportnews.di.module;

import android.content.Context;

import aaa.bbb.ccc.sportnews.NewsApp;
import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final NewsApp application;

    public ApplicationModule(NewsApp app) {
        application = app;
    }

    @Provides
    Context provideContext() {
        return application.getApplicationContext();
    }

}

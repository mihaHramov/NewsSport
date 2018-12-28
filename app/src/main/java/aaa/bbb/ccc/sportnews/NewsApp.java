package aaa.bbb.ccc.sportnews;

import android.app.Application;


import aaa.bbb.ccc.sportnews.di.component.DaggerNewsAppComponent;
import aaa.bbb.ccc.sportnews.di.component.NewsAppComponent;
import aaa.bbb.ccc.sportnews.di.component.NewsListComponent;
import aaa.bbb.ccc.sportnews.di.module.ApplicationModule;

public class NewsApp extends Application {

    static NewsAppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerNewsAppComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public static NewsListComponent getNewsListComponent() {
        return component.getNewsListComponent();
    }
}

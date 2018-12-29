package aaa.bbb.ccc.sportnews.di.component;

import javax.inject.Singleton;

import aaa.bbb.ccc.sportnews.di.module.ApplicationModule;
import aaa.bbb.ccc.sportnews.di.module.NetworkModule;
import aaa.bbb.ccc.sportnews.di.module.RepositoryModule;
import aaa.bbb.ccc.sportnews.di.module.SchedulerModule;
import dagger.Component;

@Component(modules = {ApplicationModule.class,RepositoryModule.class,SchedulerModule.class,NetworkModule.class})
@Singleton
public interface NewsAppComponent {
    NewsListComponent getNewsListComponent();
    NewsMenuComponent getNewsMenuComponent();
}

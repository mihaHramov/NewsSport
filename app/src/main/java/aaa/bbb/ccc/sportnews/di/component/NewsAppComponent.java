package aaa.bbb.ccc.sportnews.di.component;

import aaa.bbb.ccc.sportnews.di.module.ApplicationModule;
import aaa.bbb.ccc.sportnews.di.module.NetworkModule;
import aaa.bbb.ccc.sportnews.di.module.RepositoryModule;
import aaa.bbb.ccc.sportnews.di.module.SchedulerModule;
import dagger.Component;

@Component(modules = {ApplicationModule.class,RepositoryModule.class,SchedulerModule.class,NetworkModule.class})
public interface NewsAppComponent {
    NewsListComponent getNewsListComponent();
}

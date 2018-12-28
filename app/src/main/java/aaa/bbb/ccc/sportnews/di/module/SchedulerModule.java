package aaa.bbb.ccc.sportnews.di.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class SchedulerModule {

    @Named("uiScheduler")
    @Provides
    Scheduler getUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Named("backgroundScheduler")
    @Provides
    Scheduler getBackgroundScheduler() {
        return Schedulers.newThread();
    }
}

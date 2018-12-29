package aaa.bbb.ccc.sportnews.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.model.Source;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsActivity;
import rx.Scheduler;


@InjectViewState
public class PresenterNewsActivity extends MvpPresenter<ViewNewsActivity> {
    private IRepositoryOfNews repository;
    private List<Source> localGlobalSource;
    private Scheduler uiThread;
    private Scheduler newThread;

    private PresenterNewsActivity(IRepositoryOfNews repositoryOfNews, Scheduler thread, Scheduler newThread) {
        this.uiThread = thread;
        this.newThread = newThread;
        this.repository = repositoryOfNews;
        this.init();
    }

    private void init() {
        repository.getAllSource()
                .subscribeOn(newThread)
                .observeOn(uiThread)
                .doOnNext(globalSources -> localGlobalSource = globalSources)
                .doOnNext(globalSources -> getViewState().showMenu(globalSources))
                .subscribe(globalSources -> showNews(0));
    }

    public void showNews(Integer id) {
        Source string = localGlobalSource.get(id);
        getViewState().selectItemMenu(id);
        getViewState().showNews(string);
    }

    public static class Builder {
        private Scheduler threadUI;
        private Scheduler threadBackground;
        private IRepositoryOfNews repository;

        public Builder setThreadUI(Scheduler threadUI) {
            this.threadUI = threadUI;
            return this;
        }

        public Builder setThreadBackground(Scheduler threadBackground) {
            this.threadBackground = threadBackground;
            return this;
        }

        public Builder setRepository(IRepositoryOfNews repository) {
            this.repository = repository;
            return this;
        }

        public PresenterNewsActivity build() {
            return new PresenterNewsActivity(repository, threadUI, threadBackground);
        }
    }
}
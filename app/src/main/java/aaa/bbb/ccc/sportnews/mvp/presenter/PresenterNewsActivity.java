package aaa.bbb.ccc.sportnews.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.ILocalStarage;
import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsActivity;
import aaa.bbb.ccc.sportnews.pojo.GlobalSource;
import aaa.bbb.ccc.sportnews.pojo.ResponceOfSource;
import rx.Observable;
import rx.Scheduler;


@InjectViewState
public class PresenterNewsActivity extends MvpPresenter<ViewNewsActivity> {
    private IRepositoryOfNews repository;
    private List<GlobalSource> localGlobalSource;
    private ILocalStarage localStorage;
    private Scheduler uiThread;
    private Scheduler newThread;

    private PresenterNewsActivity(IRepositoryOfNews repositoryOfNews, ILocalStarage localStorage, Scheduler thread, Scheduler newThread) {
        this.uiThread = thread;
        this.newThread = newThread;
        this.repository = repositoryOfNews;
        this.localStorage = localStorage;
    }

    public void init() {
        repository.getAllSource()
                .map(ResponceOfSource::getSources)
                .subscribeOn(newThread)
                .observeOn(uiThread)
                .doOnNext(globalSources -> localGlobalSource = globalSources)
                .subscribe(globalSources -> {
                    getViewState().showMenu(globalSources);
                    showNews(0);
                });
    }

    public void showNews(Integer id) {
        String string = localGlobalSource.get(id).getId();
        getViewState().selectItemMenu(id);
        repository.getNews(string)
                .doOnNext(news -> localStorage.addNews(news, string))
                .onErrorResumeNext(throwable -> Observable.fromCallable(() -> localStorage.getNewsBySource(string)))
                .subscribeOn(newThread)
                .observeOn(uiThread)
                .doOnSubscribe(() -> getViewState().loading(true))
                .doOnError(throwable -> getViewState().loading(false))
                .doOnCompleted(() -> getViewState().loading(false))
                .flatMap(news -> Observable.from(news.getArticles()))
                .filter(article -> !article.getUrl().contains("facebook"))
                .toList()
                .subscribe(news -> {
                    if (news.size() == 0) {
                        getViewState().showErrorEmptyList();
                    } else {
                        getViewState().clearError();
                        getViewState().showNews(news);
                    }
                });
    }

    public static class Builder {
        private Scheduler threadUI;
        private Scheduler threadBackground;
        private IRepositoryOfNews repository;
        private ILocalStarage localStorage;

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

        public Builder setLocalStorage(ILocalStarage localStorage) {
            this.localStorage = localStorage;
            return this;
        }


        public PresenterNewsActivity build() {
            return new PresenterNewsActivity(repository, localStorage, threadUI, threadBackground);
        }
    }
}
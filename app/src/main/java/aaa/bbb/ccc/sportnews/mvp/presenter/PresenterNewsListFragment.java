package aaa.bbb.ccc.sportnews.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import aaa.bbb.ccc.sportnews.mvp.model.ILocalStorage;
import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsListFragment;
import rx.Observable;
import rx.Scheduler;

@InjectViewState
public class PresenterNewsListFragment extends MvpPresenter<ViewNewsListFragment> {
    private IRepositoryOfNews repository;
    private Scheduler uiThread;
    private Scheduler newThread;

    private PresenterNewsListFragment(IRepositoryOfNews repositoryOfNews,
                                      Scheduler thread, Scheduler newThread) {
        this.uiThread = thread;
        this.newThread = newThread;
        this.repository = repositoryOfNews;
    }

    public void init(String string) {
        repository.getNews(string)
                .flatMap(news -> Observable.from(news.getArticles()))
                .filter(article -> !article.getUrl().contains("facebook"))
                .toList()
                .subscribeOn(newThread)
                .observeOn(uiThread)
                .doOnSubscribe(() -> getViewState().loading(true))
                .doOnNext(articles -> getViewState().showError(articles.size() == 0))
                .subscribe(news -> getViewState().showNews(news), throwable -> getViewState().loading(false), () -> getViewState().loading(false));
    }

    public static class Builder {
        private Scheduler threadUI;
        private Scheduler threadBackground;
        private IRepositoryOfNews repository;
        private ILocalStorage localStorage;

        public PresenterNewsListFragment.Builder setThreadUI(Scheduler threadUI) {
            this.threadUI = threadUI;
            return this;
        }

        public PresenterNewsListFragment.Builder setThreadBackground(Scheduler threadBackground) {
            this.threadBackground = threadBackground;
            return this;
        }

        public PresenterNewsListFragment.Builder setRepository(IRepositoryOfNews repository) {
            this.repository = repository;
            return this;
        }

        public PresenterNewsListFragment.Builder setLocalStorage(ILocalStorage localStorage) {
            this.localStorage = localStorage;
            return this;
        }


        public PresenterNewsListFragment build() {
            return new PresenterNewsListFragment(repository, threadUI, threadBackground);
        }
    }
}
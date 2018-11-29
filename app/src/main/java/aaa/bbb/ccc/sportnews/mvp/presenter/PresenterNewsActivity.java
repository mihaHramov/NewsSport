package aaa.bbb.ccc.sportnews.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;


import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.ILocalStarage;
import aaa.bbb.ccc.sportnews.mvp.model.RepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsActivity;


import aaa.bbb.ccc.sportnews.pojo.GlobalSource;
import aaa.bbb.ccc.sportnews.pojo.ResponceOfSource;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


@InjectViewState
public class PresenterNewsActivity extends MvpPresenter<ViewNewsActivity> {
    private RepositoryOfNews repository;
    private List<GlobalSource> localGlobalSource;
    private ILocalStarage localStorage;

    public PresenterNewsActivity(RepositoryOfNews repositoryOfNews, ILocalStarage localStorage) {
        repository = repositoryOfNews;
        this.localStorage = localStorage;
    }

    public void init() {
        repository.getAllSource()
                .map(ResponceOfSource::getSources)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
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
}
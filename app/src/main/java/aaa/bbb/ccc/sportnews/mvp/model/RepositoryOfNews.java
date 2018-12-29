package aaa.bbb.ccc.sportnews.mvp.model;

import java.util.List;

import aaa.bbb.ccc.sportnews.api.NyNewsApi;
import aaa.bbb.ccc.sportnews.pojo.News;
import aaa.bbb.ccc.sportnews.pojo.ResponceOfSource;
import rx.Observable;

public class RepositoryOfNews implements IRepositoryOfNews{
    private final ILocalStorage storage;
    private NyNewsApi api;

    public RepositoryOfNews(NyNewsApi api,ILocalStorage storage) {
        this.api = api;
        this.storage = storage;
    }

    public Observable<News> getNews(Source string){
       return api.getAllNews(string.getUrl())
               .doOnNext(news -> storage.addNews(news, string))
               .onErrorResumeNext(throwable -> Observable.fromCallable(() -> storage.getNewsBySource(string)));
    }

    public Observable<List<Source>> getAllSource() {
        return api.getAllSource()
                .map(ResponceOfSource::getSources)
                .flatMap(Observable::from)
                .map(storage::addNewsSource)
                .toList()
                .onErrorReturn(throwable -> storage.getAllSources());
    }
}
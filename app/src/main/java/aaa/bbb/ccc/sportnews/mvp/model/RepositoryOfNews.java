package aaa.bbb.ccc.sportnews.mvp.model;

import aaa.bbb.ccc.sportnews.api.NyNewsApi;
import aaa.bbb.ccc.sportnews.pojo.News;
import aaa.bbb.ccc.sportnews.pojo.ResponceOfSource;
import rx.Observable;

public class RepositoryOfNews implements IRepositoryOfNews{
    private NyNewsApi api;

    public RepositoryOfNews(NyNewsApi api) {
        this.api = api;
    }

    public Observable<News> getNews(String string){
       return api.getAllNews(string);
    }

    public Observable<ResponceOfSource> getAllSource() {
        return api.getAllSource();
    }
}

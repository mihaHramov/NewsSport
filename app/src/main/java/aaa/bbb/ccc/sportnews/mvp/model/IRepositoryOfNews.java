package aaa.bbb.ccc.sportnews.mvp.model;

import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.pojo.News;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.NewsSource;
import rx.Observable;

public interface IRepositoryOfNews {
    Observable<List<NewsSource>> getAllSource();
    Observable<News> getNews(NewsSource string);
}

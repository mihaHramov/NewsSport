package aaa.bbb.ccc.sportnews.mvp.model;

import java.util.List;

import aaa.bbb.ccc.sportnews.pojo.News;
import rx.Observable;

public interface IRepositoryOfNews {
    Observable<List<Source>> getAllSource();
    Observable<News> getNews(Source string);
}

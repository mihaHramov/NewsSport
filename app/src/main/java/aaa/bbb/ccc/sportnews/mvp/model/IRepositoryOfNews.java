package aaa.bbb.ccc.sportnews.mvp.model;

import aaa.bbb.ccc.sportnews.pojo.News;
import aaa.bbb.ccc.sportnews.pojo.ResponceOfSource;
import rx.Observable;

public interface IRepositoryOfNews {
    Observable<ResponceOfSource> getAllSource();
    Observable<News> getNews(String string);
}

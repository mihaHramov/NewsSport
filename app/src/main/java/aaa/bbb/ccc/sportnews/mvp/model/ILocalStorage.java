package aaa.bbb.ccc.sportnews.mvp.model;

import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.pojo.GlobalSource;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.News;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.NewsSource;


public interface ILocalStorage {
    NewsSource addNewsSource(GlobalSource source);
    List<NewsSource> getAllSources();
    void addNews(News news, NewsSource source);
    News getNewsBySource(NewsSource string);
}

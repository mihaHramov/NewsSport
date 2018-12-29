package aaa.bbb.ccc.sportnews.mvp.model;

import aaa.bbb.ccc.sportnews.pojo.News;


public interface ILocalStorage {
    void addNews(News news, String source);
    News getNewsBySource(String string);
}

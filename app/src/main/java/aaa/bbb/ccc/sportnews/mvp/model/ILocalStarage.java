package aaa.bbb.ccc.sportnews.mvp.model;

import aaa.bbb.ccc.sportnews.pojo.News;


public interface ILocalStarage {
    void addNews(News news, String source);
    News getNewsBySource(String string);
}

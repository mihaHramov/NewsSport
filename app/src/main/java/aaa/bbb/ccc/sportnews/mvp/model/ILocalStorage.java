package aaa.bbb.ccc.sportnews.mvp.model;

import java.util.List;

import aaa.bbb.ccc.sportnews.pojo.GlobalSource;
import aaa.bbb.ccc.sportnews.pojo.News;


public interface ILocalStorage {
    Source addNewsSource(GlobalSource source);
    List<Source> getAllSources();
    void addNews(News news, Source source);
    News getNewsBySource(Source string);
}

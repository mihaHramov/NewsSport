package aaa.bbb.ccc.sportnews;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import aaa.bbb.ccc.sportnews.api.NyNewsApi;
import aaa.bbb.ccc.sportnews.mvp.model.ILocalStorage;
import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.model.RepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.Article;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.GlobalSource;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.News;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.NewsSource;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.ResponceOfSource;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.Source;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class RepositoryOfNewsTest {
    private IRepositoryOfNews repositoryOfNews;
    private NyNewsApi api;
    private ILocalStorage storage;

    @Before
    public void before() {
        api = mock(NyNewsApi.class);
        storage = mock(ILocalStorage.class);
        repositoryOfNews = new RepositoryOfNews(api, storage);
    }


    @Test
    public void test_save_news_source_in_local_bd() {
        ResponceOfSource responceOfSource = getResponceOfSource();
        NewsSource source = getAnyNewsSource();
        when(api.getAllSource()).then((Answer<Observable<ResponceOfSource>>) invocation -> Observable.just(responceOfSource));
        when(storage.addNewsSource(any(GlobalSource.class))).then((Answer<NewsSource>) invocation -> source);
        repositoryOfNews.getAllSource().subscribe(new TestSubscriber<>());
        verify(storage, times(responceOfSource.getSources().size())).addNewsSource(any(GlobalSource.class));
    }

    @Test
    public void test_get_all_news_source_when_error() {
        NewsSource source = getAnyNewsSource();
        List<NewsSource> newsSourceList = new ArrayList<>();
        for (int i=0;i<10;i++){
            newsSourceList.add(source);
        }
        when(api.getAllSource()).thenReturn(Observable.error(new Throwable()));
        when(storage.getAllSources()).then((Answer<List<NewsSource>>) invocation -> newsSourceList);

        when(storage.addNewsSource(any(GlobalSource.class))).then((Answer<NewsSource>) invocation -> source);
        repositoryOfNews.getAllSource().subscribe(new TestSubscriber<>());

        verify(storage).getAllSources();
    }

    private ResponceOfSource getResponceOfSource() {
        GlobalSource globalSource = getGlobalSource();
        List<GlobalSource> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(globalSource);
        }
        ResponceOfSource responceOfSource = new ResponceOfSource();
        responceOfSource.setStatus("status");
        responceOfSource.setSources(list);
        return responceOfSource;
    }

    private GlobalSource getGlobalSource() {
        GlobalSource globalSource = new GlobalSource();
        globalSource.setUrl("url");
        globalSource.setName("name");
        globalSource.setDescription("setDescription");
        globalSource.setId("id");
        return globalSource;
    }

    private News getNews() {
        News news = new News();
        news.setTotalResults(10);
        news.setStatus("name");
        Article article = new Article();
        List<Article> articles = new ArrayList<>();
        article.setAuthor("author");
        article.setDescription("Description");
        article.setPublishedAt("data");
        Source source = new Source();
        source.setId("id");
        source.setName("name");
        article.setSource(source);
        article.setAuthor("author");
        article.setTitle("title");
        article.setUrl("url");
        article.setUrlToImage("image");
        articles.add(article);
        articles.add(article);
        articles.add(article);
        articles.add(article);
        news.setArticles(articles);
        return news;
    }


    private NewsSource getAnyNewsSource() {
        NewsSource source = new NewsSource();
        source.setId(1);
        source.setName("name");
        source.setUrl("url");
        return source;
    }

}
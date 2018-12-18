package aaa.bbb.ccc.sportnews;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.ILocalStarage;
import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsActivity;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsActivity$$State;
import aaa.bbb.ccc.sportnews.pojo.Article;
import aaa.bbb.ccc.sportnews.pojo.GlobalSource;
import aaa.bbb.ccc.sportnews.pojo.News;
import aaa.bbb.ccc.sportnews.pojo.ResponceOfSource;
import aaa.bbb.ccc.sportnews.pojo.Source;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PresenterNewsActivityTest {
    private IRepositoryOfNews repositoryOfNews;
    private ILocalStarage localStorage;
    private ViewNewsActivity$$State state;
    private PresenterNewsActivity presenterNewsActivity;

    @Before
    public void before() {
        repositoryOfNews = mock(IRepositoryOfNews.class);
        localStorage = mock(ILocalStarage.class);
        state = mock(ViewNewsActivity$$State.class);
        presenterNewsActivity = getPresenter(localStorage, repositoryOfNews);
        presenterNewsActivity.setViewState(state);
    }

    @Test
    public void menu_is_show() {
        ResponceOfSource responceOfSource = getResponceOfSource();
        when(repositoryOfNews.getAllSource())
                .then((Answer<Observable<ResponceOfSource>>) invocation -> Observable.just(responceOfSource));
        when(repositoryOfNews.getNews(anyString()))
                .then((Answer<Observable<News>>) invocation -> Observable.just(getNews()));
        presenterNewsActivity.init();
        verify(state).showMenu(responceOfSource.getSources());
        verify(state, never()).showErrorEmptyList();
    }
    @Test
    public void test_filter() {
        News news = getNews();
        List<Article> articles = new ArrayList<>();
        for (int i =0;i<news.getArticles().size();i++) {
            Article article  =news.getArticles().get(i);
            article.setUrl("facebook");
            articles.add(article);
        }
        news.setArticles(articles);

        ResponceOfSource responceOfSource = getResponceOfSource();
        when(repositoryOfNews.getAllSource())
                .then((Answer<Observable<ResponceOfSource>>) invocation -> Observable.just(responceOfSource));
        when(repositoryOfNews.getNews(anyString()))
                .then((Answer<Observable<News>>) invocation -> Observable.just(news));
        presenterNewsActivity.init();
        verify(state).showErrorEmptyList();
    }

    @Test
    public void news_is_show() {
        News news  = getNews();
        ResponceOfSource responceOfSource = getResponceOfSource();
        when(repositoryOfNews.getAllSource())
                .then((Answer<Observable<ResponceOfSource>>) invocation -> Observable.just(responceOfSource));
        when(repositoryOfNews.getNews(anyString()))
                .then((Answer<Observable<News>>) invocation -> Observable.just(news));
        presenterNewsActivity.init();
        verify(state).showNews(news.getArticles());
    }

    @Test
    public void loading_is_active() {
        ResponceOfSource responceOfSource = getResponceOfSource();
        when(repositoryOfNews.getAllSource())
                .then((Answer<Observable<ResponceOfSource>>) invocation -> Observable.just(responceOfSource));
        when(repositoryOfNews.getNews(anyString()))
                .then((Answer<Observable<News>>) invocation -> Observable.just(getNews()));
        presenterNewsActivity.init();
        verify(state,times(1)).loading(true);
        verify(state,times(1)).loading(false);
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

    private PresenterNewsActivity getPresenter(ILocalStarage localStorage, IRepositoryOfNews repositoryOfNews) {
        return new PresenterNewsActivity.Builder()
                .setLocalStorage(localStorage)
                .setRepository(repositoryOfNews)
                .setThreadUI(Schedulers.immediate())
                .setThreadBackground(Schedulers.immediate())
                .build();
    }
}
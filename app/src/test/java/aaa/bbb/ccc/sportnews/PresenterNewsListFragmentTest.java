package aaa.bbb.ccc.sportnews;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.Article;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.News;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.NewsSource;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.Source;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsListFragment;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsListFragment$$State;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PresenterNewsListFragmentTest {
    private IRepositoryOfNews repositoryOfNews;
    private ViewNewsListFragment$$State state;
    private PresenterNewsListFragment presenterNewsFragment;

    @Before
    public void before() {
        repositoryOfNews = mock(IRepositoryOfNews.class);
        state = mock(ViewNewsListFragment$$State.class);
        presenterNewsFragment = getPresenter(repositoryOfNews);
        presenterNewsFragment.setViewState(state);
    }


    @Test
    public void test_loading_after_error() {
        NewsSource source = getAnyNewsSource();
        when(repositoryOfNews.getNews(source)).then((Answer<Observable<News>>) invocation -> Observable.error(new Throwable()));
        presenterNewsFragment.init(source);
        verify(state,times(1)).loading(true);
        verify(state,times(1)).loading(false);
    }

    @Test
    public void test_filter_and_one_result(){
        News news = getNews();
        NewsSource source = getAnyNewsSource();
        List<Article> articles = new ArrayList<>();
        for (int i =0;i<news.getArticles().size();i++) {
            Article article  =news.getArticles().get(i);
            article.setUrl("facebook");
            articles.add(article);
        }
        Article article = new Article();
        article.setUrl("testUrl");
        articles.add(article);
        news.setArticles(articles);
        List<Article> articlesTest = new ArrayList<>();
        articlesTest.add(article);
        when(repositoryOfNews.getNews(source)).then((Answer<Observable<News>>) invocation -> Observable.just(news));
        presenterNewsFragment.init(source);
        verify(state).showNews(articlesTest);
        verify(state).showError(false);
    }

    @Test
    public void test_filter() {
        News news = getNews();
        NewsSource source = getAnyNewsSource();
        List<Article> articles = new ArrayList<>();
        for (int i =0;i<news.getArticles().size();i++) {
            Article article  =news.getArticles().get(i);
            article.setUrl("facebook");
            articles.add(article);
        }
        news.setArticles(articles);
        when(repositoryOfNews.getNews(source)).then((Answer<Observable<News>>) invocation -> Observable.just(news));
        presenterNewsFragment.init(source);
        verify(state).showError(true);
    }

    @Test
    public void news_is_show() {
        NewsSource source = getAnyNewsSource();
        News news = getNews();
        when(repositoryOfNews.getNews(source)).then((Answer<Observable<News>>) invocation -> Observable.just(news));
        presenterNewsFragment.init(source);
        verify(state).showNews(news.getArticles());
    }

    @Test
    public void loading_is_active() {
        NewsSource source = getAnyNewsSource();
        News news = getNews();
        when(repositoryOfNews.getNews(source)).then((Answer<Observable<News>>) invocation -> Observable.just(news));
        presenterNewsFragment.init(source);
        verify(state,times(1)).loading(true);
        verify(state,times(1)).loading(false);
    }

    private NewsSource getAnyNewsSource(){
        NewsSource source = new NewsSource();
        source.setId(1);
        source.setName("name");
        source.setUrl("url");
        return source;
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

    private PresenterNewsListFragment getPresenter(IRepositoryOfNews repositoryOfNews) {
        return new PresenterNewsListFragment.Builder()
                .setRepository(repositoryOfNews)
                .setThreadUI(Schedulers.immediate())
                .setThreadBackground(Schedulers.immediate())
                .build();
    }
}
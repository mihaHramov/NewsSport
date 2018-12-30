package aaa.bbb.ccc.sportnews;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.IRepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.NewsSource;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsActivity;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsActivity$$State;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PresenterNewsActivityTest {
    private IRepositoryOfNews repositoryOfNews;
    private ViewNewsActivity$$State state;
    private PresenterNewsActivity presenterNewsActivity;

    @Before
    public void before() {
        repositoryOfNews = mock(IRepositoryOfNews.class);
        state = mock(ViewNewsActivity$$State.class);
        presenterNewsActivity = new PresenterNewsActivity.Builder()
                .setRepository(repositoryOfNews)
                .setThreadUI(Schedulers.immediate())
                .setThreadBackground(Schedulers.immediate())
                .build();
        presenterNewsActivity.setViewState(state);
    }

    @Test
    public void menu_is_show() {
        List<NewsSource> sourceList = new ArrayList<>();
        NewsSource source = new NewsSource();
        source.setId(1);
        source.setName("name");
        source.setUrl("url");
        for (int i = 0; i < 10; i++) {
            sourceList.add(source);
        }
        when(repositoryOfNews.getAllSource())
                .then((Answer<Observable<List<NewsSource>>>) invocation -> Observable.just(sourceList));
        presenterNewsActivity.init();
        verify(state).showMenu(sourceList);
        verify(state,never()).showError(true);
    }

    @Test
    public void menu_item_is_zero(){
        when(repositoryOfNews.getAllSource()).then((Answer<Observable<List<NewsSource>>>) invocation ->{
            List<NewsSource> newsSources = new ArrayList<>();
            return Observable.just(newsSources);
        });
        presenterNewsActivity.init();
        verify(state).showError(true);
    }
}
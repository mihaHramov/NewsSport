package aaa.bbb.ccc.sportnews.di.module;

import aaa.bbb.ccc.sportnews.mvp.model.pojo.Article;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterDetailsNews;
import dagger.Module;
import dagger.Provides;

@Module
public class DetailsNewsModule {
    private Article article;

    public DetailsNewsModule(Article article) {
        this.article = article;
    }

    @Provides
    PresenterDetailsNews presenterDetailsNews() {
        return new PresenterDetailsNews(article);
    }
}

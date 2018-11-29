package aaa.bbb.ccc.sportnews.api;

import aaa.bbb.ccc.sportnews.pojo.News;
import aaa.bbb.ccc.sportnews.pojo.ResponceOfSource;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NyNewsApi {
    String BASE_URL= "https://newsapi.org/";

    @GET("v1/sources?category=sports&language=en")
    Observable<ResponceOfSource> getAllSource();

    @GET("v2/top-headlines?apiKey=a6d1e3a8b85049378ddeb95d5d49531e")
    Observable<News> getAllNews(@Query("sources") String source);
}
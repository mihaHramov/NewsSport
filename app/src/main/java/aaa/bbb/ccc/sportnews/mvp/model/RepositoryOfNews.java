package aaa.bbb.ccc.sportnews.mvp.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import aaa.bbb.ccc.sportnews.api.NyNewsApi;
import aaa.bbb.ccc.sportnews.pojo.News;
import aaa.bbb.ccc.sportnews.pojo.ResponceOfSource;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class RepositoryOfNews {
    private NyNewsApi api;

    public RepositoryOfNews() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(NyNewsApi.BASE_URL)
                .client(client).build();//базовый url
        api = retrofit.create(NyNewsApi.class);
    }

    public Observable<News> getNews(String string){
       return api.getAllNews(string);
    }

    public Observable<ResponceOfSource> getAllSource() {
        return api.getAllSource();
    }
}

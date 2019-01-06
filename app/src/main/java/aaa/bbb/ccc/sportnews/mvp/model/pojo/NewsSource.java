package aaa.bbb.ccc.sportnews.mvp.model.pojo;

import java.io.Serializable;

public class NewsSource implements Serializable {
    private String name;
    private String url;
    private Integer id;
    private News news;
    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNews(News news) {
        this.news = news;
    }

    public News getNews() {
        return this.news;
    }
}

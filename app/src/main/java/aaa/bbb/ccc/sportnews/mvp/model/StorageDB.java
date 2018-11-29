package aaa.bbb.ccc.sportnews.mvp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import aaa.bbb.ccc.sportnews.pojo.Article;
import aaa.bbb.ccc.sportnews.pojo.News;


public class StorageDB extends SQLiteOpenHelper implements ILocalStarage {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "newsManager";
    private static final String TABLE_ARTICLES = "article";

    private static final String KEY_ID = "id";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL = "url";
    private static final String KEY_URL_TO_IMAGE = "image_url";
    private static final String KEY_PUBLISHED_AT = "published_at";
    private static final String KEY_SOURCE = "source";
    private static final String KEY_TITLE = "title";


    public StorageDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public News getNewsBySource(String source) {
        News news = new News();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_ARTICLES + " WHERE " + KEY_SOURCE + "='" + source+"'";
        Cursor c = db.rawQuery(selectQuery, null);
        List<Article> articleList = new ArrayList<>();
        if (c.moveToFirst()) {
            int authorColIndex = c.getColumnIndex(KEY_AUTHOR);
            int descriptionColIndex = c.getColumnIndex(KEY_DESCRIPTION);
            int publishedColIndex = c.getColumnIndex(KEY_PUBLISHED_AT);
            int urlColIndex = c.getColumnIndex(KEY_URL);
            int urlImageColIndex = c.getColumnIndex(KEY_URL_TO_IMAGE);
            int titleImageColIndex = c.getColumnIndex(KEY_TITLE);
            while (c.moveToNext()) {
                Article article = new Article();
                article.setAuthor(c.getString(authorColIndex));
                article.setDescription(c.getString(descriptionColIndex));
                article.setTitle(c.getString(titleImageColIndex));
                article.setUrl(c.getString(urlColIndex));
                article.setUrlToImage(c.getString(urlImageColIndex));
                article.setPublishedAt(c.getString(publishedColIndex));
                articleList.add(article);
            }
        }
        news.setArticles(articleList);
        c.close();
        db.close();
        return news;
    }

    @Override
    public void addNews(News news,String souce) {
        if(news==null) return;
        SQLiteDatabase db = this.getWritableDatabase();
        for (Article ar : news.getArticles()) {
            String selectQuery = "SELECT  * FROM " + TABLE_ARTICLES + " WHERE " + KEY_URL + "='" + ar.getUrl()+"'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (!cursor.moveToFirst()) {
                //create
               ContentValues cv = new ContentValues();
                cv.put(KEY_AUTHOR, ar.getAuthor());
                cv.put(KEY_TITLE, ar.getTitle());
                cv.put(KEY_DESCRIPTION, ar.getDescription());
                cv.put(KEY_PUBLISHED_AT, ar.getPublishedAt());
                cv.put(KEY_URL, ar.getUrl());
                cv.put(KEY_URL_TO_IMAGE, ar.getUrlToImage());
                cv.put(KEY_SOURCE, souce);
                db.insert(TABLE_ARTICLES, null, cv);
            }
            cursor.close();
        }
        db.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ARTICLE_TABLE = "CREATE TABLE " + TABLE_ARTICLES + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_AUTHOR + " TEXT,"
                + KEY_DESCRIPTION + " TEXT,"
                + KEY_URL + " TEXT,"
                + KEY_URL_TO_IMAGE + " TEXT,"
                + KEY_TITLE + " TEXT,"
                + KEY_PUBLISHED_AT + " TEXT,"
                + KEY_SOURCE + " TEXT" + ")";
        db.execSQL(CREATE_ARTICLE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        onCreate(db);
    }
}

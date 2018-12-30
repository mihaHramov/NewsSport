package aaa.bbb.ccc.sportnews.mvp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import aaa.bbb.ccc.sportnews.mvp.model.pojo.Article;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.GlobalSource;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.News;


public class StorageDB extends SQLiteOpenHelper implements ILocalStorage {
    private static final int DATABASE_VERSION = 7;
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


    private static final String KEY_NAME_OF_SOURCE = "name";
    private static final String KEY_URL_OF_SOURCE = "url";
    private static final String KEY_ID_SOURCE = "id";
    private static final String TABLE_SOURCE = "source";


    public StorageDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public List<NewsSource> getAllSources() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_SOURCE;
        Cursor c = db.rawQuery(selectQuery, null);
        List<NewsSource> sourcesList = new ArrayList<>();

        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(KEY_ID_SOURCE);
            int urlColIndex = c.getColumnIndex(KEY_URL_OF_SOURCE);
            int nameColIndex = c.getColumnIndex(KEY_NAME_OF_SOURCE);

            do{
                NewsSource source = new NewsSource();
                source.setId(c.getInt(idColIndex));
                source.setName(c.getString(nameColIndex));
                source.setUrl(c.getString(urlColIndex));
                sourcesList.add(source);
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        return sourcesList;
    }

    @Override
    public NewsSource addNewsSource(GlobalSource sourceName) {
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_SOURCE + " WHERE " + KEY_URL_OF_SOURCE + "='" + sourceName.getId() + "'";

        Cursor c = db.rawQuery(selectQuery, null);
        NewsSource source = new NewsSource();
        if(!c.moveToFirst()){
            ContentValues cv = new ContentValues();
            cv.put(KEY_NAME_OF_SOURCE, sourceName.getName());
            cv.put(KEY_URL_OF_SOURCE,sourceName.getId());

            source.setId((int) db.insert(TABLE_SOURCE, null, cv));
            source.setName(sourceName.getName());
            source.setUrl(sourceName.getUrl());
        }else {
            int idColIndex = c.getColumnIndex(KEY_ID_SOURCE);
            int urlColIndex = c.getColumnIndex(KEY_URL_OF_SOURCE);
            int nameColIndex = c.getColumnIndex(KEY_NAME_OF_SOURCE);
            source.setId(c.getInt(idColIndex));
            source.setUrl(c.getString(urlColIndex));
            source.setName(c.getString(nameColIndex));
        }
        c.close();
        db.close();
        return source;
    }

    @Override
    public News getNewsBySource(NewsSource source) {
        News news = new News();
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_ARTICLES + " WHERE " + KEY_URL_OF_SOURCE + "='" + source.getId() + "'";
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
    public void addNews(News news, NewsSource souce) {
        if (news == null) return;
        SQLiteDatabase db = this.getWritableDatabase();
        for (Article ar : news.getArticles()) {
            String selectQuery = "SELECT  * FROM " + TABLE_ARTICLES + " WHERE " + KEY_URL + "='" + ar.getUrl() + "'";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (!cursor.moveToFirst()) {
                ContentValues cv = new ContentValues();
                cv.put(KEY_AUTHOR, ar.getAuthor());
                cv.put(KEY_TITLE, ar.getTitle());
                cv.put(KEY_DESCRIPTION, ar.getDescription());
                cv.put(KEY_PUBLISHED_AT, ar.getPublishedAt());
                cv.put(KEY_URL, ar.getUrl());
                cv.put(KEY_URL_TO_IMAGE, ar.getUrlToImage());
                cv.put(KEY_SOURCE, souce.getId());
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
                + KEY_SOURCE + " INTEGER" + ")";
        db.execSQL(CREATE_ARTICLE_TABLE);

        String CREATE_SOURCE_TABLE = "CREATE TABLE " + TABLE_SOURCE + "("
                + KEY_ID_SOURCE + " INTEGER PRIMARY KEY,"
                + KEY_NAME_OF_SOURCE + " TEXT,"
                + KEY_URL_OF_SOURCE +" TEXT"+
                ")";
        db.execSQL(CREATE_SOURCE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ARTICLES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOURCE);
        onCreate(db);
    }
}

package aaa.bbb.ccc.sportnews.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import aaa.bbb.ccc.sportnews.R;
import aaa.bbb.ccc.sportnews.mvp.model.RepositoryOfNews;
import aaa.bbb.ccc.sportnews.mvp.model.StorageDB;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsActivity;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsActivity;
import aaa.bbb.ccc.sportnews.pojo.Article;
import aaa.bbb.ccc.sportnews.pojo.GlobalSource;
import aaa.bbb.ccc.sportnews.ui.adapter.ArticleAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class NewsActivity extends MvpAppCompatActivity implements ViewNewsActivity,
        NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    AVLoadingIndicatorView progressBar;
    @BindView(R.id.empty_list)
    TextView emptyList;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private ArticleAdapter adapter;


    @InjectPresenter
    PresenterNewsActivity presenterNewsActivity;

    @ProvidePresenter
    PresenterNewsActivity providePresenter() {
        presenterNewsActivity = new PresenterNewsActivity.Builder()
                .setRepository(new RepositoryOfNews())
                .setLocalStorage(new StorageDB(this))
                .setThreadUI(AndroidSchedulers.mainThread())
                .setThreadBackground(Schedulers.newThread()).build();
        presenterNewsActivity.init();
        return presenterNewsActivity;
    }

    @Override
    public void showErrorEmptyList() {
        emptyList.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void clearError() {
        recyclerView.setVisibility(View.VISIBLE);
        emptyList.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        adapter = new ArticleAdapter(article -> startActivity(DetailsWebActivity.getIstance(this, article)));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this,
                getResources().getIdentifier("layout_animation_fall_down", "anim", getPackageName()));
        recyclerView.setLayoutAnimation(animation);
        recyclerView.scheduleLayoutAnimation();
        recyclerView.setAdapter(adapter);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void loading(Boolean b) {
        if (b)
            progressBar.show();
        else
            progressBar.hide();
    }


    @Override
    public void showMenu(List<GlobalSource> globalSources) {
        for (int i = 0; i < globalSources.size(); i++) {
            GlobalSource item = globalSources.get(i);
            navigationView.getMenu()
                    .add(Menu.NONE, i, i, item.getName())
                    .setOnMenuItemClickListener(menuItem -> {
                        presenterNewsActivity.showNews(menuItem.getItemId());
                        return false;
                    });
        }
    }


    @Override
    public void selectItemMenu(Integer id) {
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(id == i);
        }
    }

    @Override
    public void showNews(List<Article> news) {
        adapter.setList(news);
    }
}
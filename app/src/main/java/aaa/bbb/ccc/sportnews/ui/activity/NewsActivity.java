package aaa.bbb.ccc.sportnews.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;

import java.util.List;

import aaa.bbb.ccc.sportnews.NewsApp;
import aaa.bbb.ccc.sportnews.R;
import aaa.bbb.ccc.sportnews.mvp.model.Source;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsActivity;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsActivity;
import aaa.bbb.ccc.sportnews.ui.fragment.BaseNewsListFragment;
import aaa.bbb.ccc.sportnews.ui.fragment.NewsListFragment;
import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsActivity extends MvpAppCompatActivity implements ViewNewsActivity,
        NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    private BaseNewsListFragment container;

    @InjectPresenter
    PresenterNewsActivity presenterNewsActivity;

    @ProvidePresenter
    PresenterNewsActivity providePresenter() {
        return NewsApp.getMenuComponent().getPresenter();
    }

    @Override
    public void showNews(Source string) {
        container.displayNews(string);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        NewsApp.getMenuComponent().inject(this);
        if (savedInstanceState == null) {
            container = new NewsListFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, container)
                    .commit();
        }

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
    public void showMenu(List<Source> globalSources) {
        for (int i = 0; i < globalSources.size(); i++) {
            Source item = globalSources.get(i);
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
}
package aaa.bbb.ccc.sportnews.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.infideap.drawerbehavior.Advance3DDrawerLayout;
import com.squareup.otto.Subscribe;

import java.util.List;

import aaa.bbb.ccc.sportnews.NewsApp;
import aaa.bbb.ccc.sportnews.R;
import aaa.bbb.ccc.sportnews.mvp.model.pojo.NewsSource;
import aaa.bbb.ccc.sportnews.mvp.presenter.PresenterNewsActivity;
import aaa.bbb.ccc.sportnews.mvp.view.ViewNewsActivity;
import aaa.bbb.ccc.sportnews.ui.fragment.NewsListFragment;
import aaa.bbb.ccc.sportnews.ui.util.BusProvider;
import aaa.bbb.ccc.sportnews.ui.util.ChangeNetworkConnect;
import aaa.bbb.ccc.sportnews.ui.util.NetworkUtil;
import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsActivity extends MvpAppCompatActivity implements ViewNewsActivity,
        NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.error)
    TextView error;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    Advance3DDrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @InjectPresenter
    PresenterNewsActivity presenterNewsActivity;

    @ProvidePresenter
    PresenterNewsActivity providePresenter() {
        presenterNewsActivity = NewsApp.getMenuComponent().getPresenter();
        presenterNewsActivity.init();
        return presenterNewsActivity;
    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = NetworkUtil.getConnectivityStatus(context);
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                BusProvider.getInstance().post(new ChangeNetworkConnect(status != NetworkUtil.TYPE_NOT_CONNECTED));
            }
        }
    };

    @Subscribe
    public void changeNetworkConnect (ChangeNetworkConnect connect){
        presenterNewsActivity.changeNetwork(connect.getConnect());
    }

    @Override
    public void showNews(NewsSource string) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, NewsListFragment.newInstance(string))
                .commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        NewsApp.getMenuComponent().inject(this);
        initDrawerLayout();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BusProvider.getInstance().unregister(this);
    }

    private void initDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.setViewRotation(Gravity.START, 15);
        drawer.setViewScale(Gravity.START, 0.9f); //set height scale for main view (0f to 1f)
        drawer.setViewElevation(Gravity.START, 20);//set main view elevation when drawer open (dimension)
        drawer.setViewScrimColor(Gravity.START, Color.TRANSPARENT);//set drawer overlay coloe (color)
        drawer.setDrawerElevation(Gravity.START, 20);//set drawer elevation (dimension)
        drawer.setRadius(Gravity.START, 25);//set end container's corner radius (dimension)
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void showError(Boolean bool) {
        error.setVisibility(bool ? View.VISIBLE : View.GONE);
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
    public void showMenu(List<NewsSource> globalSources) {
        for (int i = 0; i < globalSources.size(); i++) {
            NewsSource item = globalSources.get(i);
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
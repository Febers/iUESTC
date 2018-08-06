/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.service.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.net.WebViewConfigure;
import com.febers.iuestc.util.DestroyWebViewUtil;
import com.febers.iuestc.util.PToUrlUtil;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

public class ServiceActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "ServiceActivity";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private WebView webView;
    private int position;
    private Toolbar mToolbar;
    private ProgressBar progressBar;

    @Override
    protected int setView() {
        SlidrConfig config = new SlidrConfig.Builder()
                .edge(true)
                .build();
        Slidr.attach(this, config);
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        return R.layout.activity_service;
    }

    @Override
    protected void initView() {
        mToolbar = findViewById(R.id.tb_service);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        progressBar = findViewById(R.id.progressbar_service);
        drawerLayout = findViewById(R.id.dl_service);
        navigationView = findViewById(R.id.nv_service);
        navigationView.setCheckedItem(position);
        navigationView.setNavigationItemSelectedListener(this);
        webView = findViewById(R.id.web_service);
        initWebView();
    }

    private void initWebView() {
        new WebViewConfigure.Builder(this, webView)
                .setOpenUrlOut(false)
                .setSupportWindow(true)
                .setSupportLoadingBar(true, progressBar)
                .builder();
        dateRequest(true);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        webView.loadUrl(PToUrlUtil.getUrl(position));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (drawerLayout.isDrawerOpen(Gravity.END)) {
            drawerLayout.closeDrawer(Gravity.END);
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d(TAG, "onNavigationItemSelected: ");
        switch (item.getItemId()) {
            case R.id.item_nav_query_classroom:
                webView.clearHistory();
                webView.loadUrl(PToUrlUtil.getUrl(0));
                navigationView.setCheckedItem(R.id.item_nav_query_classroom);
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_today_class:
                webView.clearHistory();
                webView.loadUrl(PToUrlUtil.getUrl(1));
                navigationView.setCheckedItem(R.id.item_nav_query_today_class);
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_all_class:
                webView.clearHistory();
                webView.loadUrl(PToUrlUtil.getUrl(2));
                navigationView.setCheckedItem(R.id.item_nav_query_all_class);
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_teacher:
                webView.clearHistory();
                webView.loadUrl(PToUrlUtil.getUrl(3));
                navigationView.setCheckedItem(R.id.item_nav_query_teacher);
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_lxfs:
                webView.clearHistory();
                webView.loadUrl(PToUrlUtil.getUrl(9));
                navigationView.setCheckedItem(R.id.item_nav_lxfs);
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_exit:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 防止内存泄漏
     */
    @Override
    protected void onDestroy() {
        DestroyWebViewUtil.destroy(webView);
        super.onDestroy();
    }
}

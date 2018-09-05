/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-17 下午2:22
 *
 */

package com.febers.iuestc.module.service.view;

import android.content.Intent;
import android.net.Uri;
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
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.net.WebViewConfigure;
import com.febers.iuestc.util.DestroyWebViewUtil;
import com.febers.iuestc.util.PToUrlUtil;

public class ServiceActivity extends BaseSwipeActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "ServiceActivity";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private WebView webView;
    private int position;
    private Toolbar mToolbar;
    private ProgressBar progressBar;
    private String mUrl = PToUrlUtil.getUrl(0);

    @Override
    protected int setView() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        return R.layout.activity_service;
    }

    @Override
    protected int setMenu() {
        return R.menu.service_menu;
    }

    @Override
    protected void findViewById() {
        mToolbar = findViewById(R.id.tb_service);
        progressBar = findViewById(R.id.progressbar_service);
        drawerLayout = findViewById(R.id.dl_service);
        navigationView = findViewById(R.id.nv_service);
        webView = findViewById(R.id.web_service);
    }

    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setCheckedItem(position);
        navigationView.setNavigationItemSelectedListener(this);
        initWebView();
    }

    private void initWebView() {
        new WebViewConfigure.Builder(this, webView)
                .enableJS(true)
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
        switch (item.getItemId()) {
            case R.id.item_nav_query_classroom:
                webView.clearHistory();
                mUrl = PToUrlUtil.getUrl(0);
                navigationView.setCheckedItem(R.id.item_nav_query_classroom);
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_today_class:
                webView.clearHistory();
                mUrl = PToUrlUtil.getUrl(1);
                navigationView.setCheckedItem(R.id.item_nav_query_today_class);
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_all_class:
                webView.clearHistory();
                mUrl = PToUrlUtil.getUrl(2);
                navigationView.setCheckedItem(R.id.item_nav_query_all_class);
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_query_teacher:
                webView.clearHistory();
                mUrl = PToUrlUtil.getUrl(3);
                navigationView.setCheckedItem(R.id.item_nav_query_teacher);
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_lxfs:
                webView.clearHistory();
                mUrl = PToUrlUtil.getUrl(9);
                navigationView.setCheckedItem(R.id.item_nav_lxfs);
                drawerLayout.closeDrawer(Gravity.END);
                break;
            case R.id.item_nav_exit:
                finish();
                break;
            default:
                break;
        }
        webView.loadUrl(mUrl);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_service_source) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mUrl));
            startActivity(Intent.createChooser(intent, "请选择浏览器"));
        }
        return super.onOptionsItemSelected(item);
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

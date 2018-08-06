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
import android.net.http.SslError;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.net.WebViewConfigure;
import com.febers.iuestc.util.DestroyWebViewUtil;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

public class BusActivity extends BaseActivity {

    private static final String TAG = "BusActivity";
    private WebView webView;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    @Override
    protected int setView() {
        return R.layout.activity_bus;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.tb_bus);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        progressBar = findViewById(R.id.progressbar_bus);
        webView = findViewById(R.id.web_bus);

        new WebViewConfigure.Builder(this, webView)
                .setSupportLoadingBar(true, progressBar)
                .builder();
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
//        String cacheDirPath = getFilesDir().getAbsolutePath() + "_cache";
//        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setDatabaseEnabled(true);
//
//        webSettings.setUseWideViewPort(true);  //将图片调整到适当的大小
//
        dateRequest(true);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        webView.loadUrl("https://uestc.ga/api/extra/traffic");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calender_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_calender_refresh:
                webView.loadUrl("https://uestc.ga/api/extra/traffic");
                break;
            case R.id.item_calender_open_web:
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://bbs.uestc.edu.cn/forum.php?mod=viewthread&tid=1430861"));
                startActivity(Intent.createChooser(intent, "请选择浏览器"));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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

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
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        String cacheDirPath = getFilesDir().getAbsolutePath() + "_cache";
        webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        webSettings.setUseWideViewPort(true);  //将图片调整到适当的大小
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持打开窗口
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  //接受所有网站证书
            }
        });
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
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }

            webView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
            webView.clearView();
            webView.removeAllViews();
            webView.destroy();
            super.onDestroy();
        }
    }
}

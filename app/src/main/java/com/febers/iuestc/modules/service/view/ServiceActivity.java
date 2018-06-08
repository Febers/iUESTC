/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-6-6 下午9:37
 *
 */

package com.febers.iuestc.modules.service.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.febers.iuestc.utils.PToUrlUtil;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

public class ServiceActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private static final String TAG = "ServiceActivity";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private WebView webView;
    private WebSettings webSettings;
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
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheEnabled(true);//开启 Application Caches 功能
        String cacheDirPath = getFilesDir().getAbsolutePath() + "_cache";
        webSettings.setAppCachePath(cacheDirPath); //设置Application Caches 缓存目录
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        webSettings.setUseWideViewPort(true);  //将图片调整到适当的大小
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持打开窗口
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

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

        webView.setDownloadListener( (String url, String userAgent, String contentDisposition,
                                      String mimetype, long contentLength) -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();  //忽略SSL证书错误，加载页面
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (!webSettings.getLoadsImagesAutomatically()) {
                    webSettings.setLoadsImagesAutomatically(true);
                }
            }

        });
        //延迟加载图片,对于4.4直接加载
        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 防止内存泄漏
     */
    @Override
    protected void onDestroy() {
        if (webView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码
            // 需要先onDetachedFromWindow()，再destory()
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

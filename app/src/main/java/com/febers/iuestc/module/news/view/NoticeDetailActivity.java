/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-8 下午8:41
 *
 */

package com.febers.iuestc.module.news.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.net.WebViewConfigure;
import com.febers.iuestc.util.WebViewUtil;

public class NoticeDetailActivity extends BaseSwipeActivity {

    private WebView webView;

    @Override
    protected int setView() {
        return R.layout.activity_notice_detail;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        Toolbar toolbar = findViewById(R.id.tb_notice_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ProgressBar progressBar = findViewById(R.id.progressbar_notice);
        webView = findViewById(R.id.webview_notice);
        new WebViewConfigure.Builder(this, webView)
                .enableJS(true)
                .setOpenUrlOut(false)
//                .setSupportLoadingBar(true, progressBar)
                .builder();
        webView.loadUrl(url);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebViewUtil.destroyWebView(webView);
    }
}

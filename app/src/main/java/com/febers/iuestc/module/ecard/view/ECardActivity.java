/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-30 下午1:42
 *
 */

package com.febers.iuestc.module.ecard.view;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.net.CustomWebViewClient;
import com.febers.iuestc.net.HtmlJSInterface;

public class ECardActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected int setView() {
        return R.layout.activity_ecard;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.web_ecard);
        Toolbar toolbar = findViewById(R.id.tb_ecard);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        HtmlJSInterface htmlJSInterface = new HtmlJSInterface();
        webView.addJavascriptInterface(htmlJSInterface, "HTMLOUT");
        webView.setWebViewClient(new CustomWebViewClient());
        webView.loadUrl("http://ecard.uestc.edu.cn/");
        webView.loadUrl("http://ecard.uestc.edu.cn/web/guest/personal");
    }
}

/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-30 下午1:41
 *
 */

package com.febers.iuestc.module.user.view;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanUser;
import com.febers.iuestc.module.user.model.UserJSInterface;
import com.febers.iuestc.module.user.presenter.UserContract;
import com.febers.iuestc.net.CustomWebViewClient;

public class UserActivity extends BaseActivity implements UserContract.View {

    private WebView webView;

    @Override
    protected int setView() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_user);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        webView = findViewById(R.id.web_user);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        UserJSInterface userJSInterface = new UserJSInterface(this);
        webView.addJavascriptInterface(userJSInterface, "HTMLOUT");
        webView.setWebViewClient(new CustomWebViewClient());
        webView.loadUrl("http://eams.uestc.edu.cn/eams/stdDetail.action");
    }

    @Override
    public void showUserDetail(BaseEvent<BeanUser> event) {

    }

    @Override
    public void statusToSuccess() {

    }

    @Override
    public void statusToFail() {

    }
}

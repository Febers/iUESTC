/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-30 下午1:41
 *
 */

package com.febers.iuestc.module.user.view;

import android.webkit.WebView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.net.CustomWebViewClient;

public class UserActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected int setView() {
        return R.layout.activity_user;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.web_user);
        webView.setWebViewClient(new CustomWebViewClient());
        webView.loadUrl("http://eams.uestc.edu.cn/eams/stdDetail.action");
    }
}

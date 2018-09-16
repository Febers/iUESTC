/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-8 下午9:33
 *
 */

package com.febers.iuestc.module.library.view;

import android.webkit.WebView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.net.WebViewConfigure;

public class LibHistoryActivity extends BaseSwipeActivity {

    @Override
    protected int setView() {
        return R.layout.activity_lib_history;
    }

    @Override
    protected void initView() {
        WebView webView = findViewById(R.id.webview_lib_lend);
        new WebViewConfigure.Builder(this, webView)
                .enableJS(true)
                .setOpenUrlOut(false)
                .builder();
        //webView.loadUrl("http://mc.m.5read.com/user/uc/showUserCenter.jspx");
        webView.loadUrl("http://222.197.165.97:8080/sms/opac/search/showSearch.action?xc=6");
    }
}

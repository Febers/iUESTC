/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-8-2 下午8:10
 *
 */

package com.febers.iuestc.module.library.view;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.entity.BeanBook;
import com.febers.iuestc.module.library.presenter.LibraryContract;
import com.febers.iuestc.module.library.presenter.LibraryPresenterImp;

import java.util.List;

public class LibDetailActivity extends BaseActivity implements LibraryContract.View {

    private WebView webView;

    @Override
    protected int setView() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_lib_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView = findViewById(R.id.webview_lib_detail);
        sendQuest(url);
    }

    private void sendQuest(String url) {
        showProgressDialog();
        LibraryContract.Presenter presenter = new LibraryPresenterImp(this);
        presenter.bookDetailRequest(url);
    }

    @Override
    public void showQuery(BaseEvent<List<BeanBook>> event) {
    }

    @Override
    public void showBookDetail(BaseEvent<String> event) {
        dismissProgressDialog();
        runOnUiThread(()-> {
            webView.loadDataWithBaseURL(null, event.getDate(),
                    "text/html","UTF-8", null);
        });
    }
}

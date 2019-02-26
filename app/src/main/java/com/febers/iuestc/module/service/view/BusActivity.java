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
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseSwipeActivity;
import com.febers.iuestc.net.WebViewConfigure;
import com.febers.iuestc.util.WebViewUtil;

public class BusActivity extends BaseSwipeActivity {

    private static final String TAG = "BusActivity";
    private WebView webView;
    private Toolbar toolbar;
    private ProgressBar progressBar;

    @Override
    protected int setView() {
        return R.layout.activity_bus;
    }

    @Override
    protected int setMenu() {
        return R.menu.calender_menu;
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

        dataRequest(true);
    }

    @Override
    public void dataRequest(Boolean isRefresh) {
        webView.loadUrl("http://bbs.uestc.edu.cn/forum.php?mod=viewthread&tid=1430861");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_calender_refresh:
                webView.loadUrl("http://bbs.uestc.edu.cn/forum.php?mod=viewthread&tid=1430861");
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
        WebViewUtil.destroyWebView(webView);
        super.onDestroy();
    }
}

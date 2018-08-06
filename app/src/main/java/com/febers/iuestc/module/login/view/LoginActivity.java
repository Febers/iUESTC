/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-28 下午5:55
 *
 */

package com.febers.iuestc.module.login.view;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseActivity;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.login.presenter.LoginJSInterface;
import com.febers.iuestc.module.login.presenter.LoginContract;
import com.febers.iuestc.net.WebViewConfigure;
import com.febers.iuestc.util.CustomSharedPreferences;

public class LoginActivity extends BaseActivity implements LoginContract.View{

    private static final String TAG = "LoginActivity";
    private WebView webView;
    private LoginJSInterface loginJSInterface;

    @Override
    protected int setView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.tb_login);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        webView = findViewById(R.id.web_login);
        dateRequest(true);
    }

    @Override
    public void dateRequest(Boolean isRefresh) {
        loginJSInterface = new LoginJSInterface(this);
        new WebViewConfigure.Builder(this, webView)
                .setOpenUrlOut(false)
                .setProcessHtml(true, loginJSInterface, "HTMLOUT")
                .builder();
        webView.loadUrl("http://portal.uestc.edu.cn");
    }

    @Override
    public void loginResult(BaseEvent event) {
        Intent intent = new Intent();
        if (event.getCode() == BaseCode.UPDATE) {
            CustomSharedPreferences.getInstance().put(getString(R.string.sp_is_login), true);
            intent.putExtra("status", true);
        } else {
            intent.putExtra("status", false);
        }
        this.setResult(BaseCode.STATUS, intent);
        finish();
    }

    @Override
    public void statusToFail() {
    }

}

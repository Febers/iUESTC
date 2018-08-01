/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-31 下午4:57
 *
 */

package com.febers.iuestc.module.login.presenter;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseJSInterface;
import com.febers.iuestc.module.login.presenter.LoginContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * JS接口，用来解析登录之后浏览器返回的HTML源码
 */
public class LoginJSInterface extends LoginContract.Presenter implements BaseJSInterface {

    private static final String TAG = "LoginJSInterface";

    public LoginJSInterface(LoginContract.View view) {
        super(view);
    }

    @Override
    public void processHTML(String html) {

    }

    @Override
    public void loginResult(BaseEvent event) {
        mView.loginSuccess(new BaseEvent(0, ""));
    }
}

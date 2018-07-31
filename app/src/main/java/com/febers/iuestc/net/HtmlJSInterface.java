/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-30 下午2:19
 *
 */

package com.febers.iuestc.net;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.login.presenter.LoginContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * JS接口，用来解析登录之后浏览器返回的HTML源码
 */
public class HtmlJSInterface {

    private static final String TAG = "HtmlJSInterface";
    private LoginContract.View loginView;

    public HtmlJSInterface(){}

    public HtmlJSInterface(LoginContract.View view) {
        loginView = view;
    }

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        checkLoginResult(html);
    }

    private void checkLoginResult(String html) {
        Log.i(TAG, "checkLoginResult: " + html);
        try {
            if (loginView == null) {
                return;
            }
            Document document = Jsoup.parse(html);
            Elements elements = document.select("li");
            if (elements.size() == 0) {
                Log.i(TAG, "checkLoginResult: 登录无效");
                return;
            }
            for(Element e:elements) {
                if (e.text().contains("个人") || e.text().contains("教务")){
                    Log.i(TAG, "checkLoginResult: 登录成功");
                    loginView.loginSuccess(new BaseEvent(0, "success"));
                }
            }

        } catch (Exception e) {
            Log.i(TAG, "checkLoginResult: 登录出错");
        }
    }


}

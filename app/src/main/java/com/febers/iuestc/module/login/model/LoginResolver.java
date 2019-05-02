/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-7-31 下午5:14
 *
 */

package com.febers.iuestc.module.login.model;

import android.util.Log;

import com.febers.iuestc.MyApp;
import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseCode;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.login.presenter.LoginContract;
import com.febers.iuestc.util.SPUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 该类用来解析webView登录的结果
 */
public class LoginResolver implements LoginContract.Resolver {

    private static final String TAG = "LoginResolver";
    private LoginContract.Presenter loginPresenter;

    public LoginResolver(LoginContract.Presenter presenter) {
        loginPresenter = presenter;
    }

    /**
     * 该方法提供一个解析webView源网页代码的功能
     * 源码由JS交互接口提供
     *
     * @param html 网页源码
     */
    @Override
    public void resolve(String html) {
        try {
            if (html.contains("mobileUsername")) {
                loginPresenter.sendIdAndPwFunc(new BaseEvent<>(BaseCode.UPDATE, idAndPwFunc()));
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
                    loginPresenter.loginResult(new BaseEvent<>(BaseCode.UPDATE, "成功"));
                }
            }

        } catch (Exception e) {
            Log.i(TAG, "checkLoginResult: 登录出错");
        }
    }

    /**
     * 向login界面的用户名输入框注入id
     *
     * @return js函数
     */
    private String  idAndPwFunc() {
        String userId = SPUtil.getInstance().get(MyApp.getContext()
                .getString(R.string.sp_user_id), "");
        return "javascript:function fun(){document.getElementById('mobileUsername').value='" + userId + "';" +
                "document.getElementById('mobilePassword').value='" + "" + "';}";
    }
}

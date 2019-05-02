package com.febers.iuestc.module.login.presenter;

import android.webkit.JavascriptInterface;

import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.module.login.model.LoginResolver;

/**
 * JS接口，用来解析登录之后浏览器返回的HTML源码
 */
public class LoginJSInterface extends LoginContract.Presenter {

    private static final String TAG = "LoginJSInterface";

    public LoginJSInterface(LoginContract.View view) {
        super(view);
    }

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        LoginContract.Resolver loginResolver = new LoginResolver(this);
        loginResolver.resolve(html);
    }

    @Override
    public void loginResult(BaseEvent<String> event) {
        if (eduView !=null) {
            eduView.loginResult(event);
        }
    }

    @Override
    public void sendIdAndPwFunc(BaseEvent<String> event) {
        if (eduView != null) {
            eduView.loadIdAndPwFunc(event);
        }
    }
}

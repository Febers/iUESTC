/*
 * Created by Febers 2018.
 * Copyright (c). All rights reserved.
 *
 * Last Modified 18-9-15 上午11:05
 *
 */

package com.febers.iuestc.module.login.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.febers.iuestc.R;
import com.febers.iuestc.base.BaseEvent;
import com.febers.iuestc.base.BaseFragment;
import com.febers.iuestc.entity.BeanLoginStatus;
import com.febers.iuestc.module.login.presenter.LoginContract;
import com.febers.iuestc.module.login.presenter.LoginJSInterface;
import com.febers.iuestc.net.WebViewConfigure;
import com.febers.iuestc.util.WebViewUtil;

import org.greenrobot.eventbus.EventBus;

public class LoginViewWeb extends BaseFragment implements LoginContract.View {

    private WebView webView;

    @Override
    protected int setContentView() {
        return R.layout.fragment_login_web;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.web_login);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        dataRequest(true);
    }

    @Override
    public void loadIdAndPwFunc(BaseEvent<String> event) {
        getActivity().runOnUiThread( () -> {
            webView.loadUrl(event.getDate());
            webView.loadUrl("javascript:fun();");
        });
    }

    @Override
    public void loginResult(BaseEvent<String> event) {
        EventBus.getDefault().post(new BaseEvent<>(event.getCode(), new BeanLoginStatus(false)));
    }

    @Override
    public boolean onBackPressedSupport() {
        getActivity().finish();
        return true;
    }

    @Override
    public void dataRequest(Boolean isRefresh) {
        LoginJSInterface loginJSInterface = new LoginJSInterface(this);
        new WebViewConfigure.Builder(getContext(), webView)
                .setOpenUrlOut(false)
                .setProcessHtml(true, loginJSInterface, "HTMLOUT")
                .builder();
        webView.loadUrl("http://portal.uestc.edu.cn");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        WebViewUtil.destroyWebView(webView);
    }
}
